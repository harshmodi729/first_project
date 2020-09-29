package com.ss_eduhub.ui.activity

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.ParametersBuilder
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.ss_eduhub.R
import com.ss_eduhub.base.BaseActivity
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.common.Constants
import com.ss_eduhub.common.PreferenceConstants
import com.ss_eduhub.data.local.model.LocalVideosItem
import com.ss_eduhub.extension.isWiFiConnected
import com.ss_eduhub.extension.makeToast
import com.ss_eduhub.extension.makeToastForServerError
import com.ss_eduhub.extension.permissionDeniedDialog
import com.ss_eduhub.model.VideosItem
import com.ss_eduhub.viewmodel.VideoViewModel
import com.ss_eduhub.widget.SSEduhubTrackSelectionView
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.lay_dialog_track_selection.view.*
import kotlinx.android.synthetic.main.lay_dialog_turn_on_wifi.view.*
import kotlinx.android.synthetic.main.lay_video_playback_control_view.*
import java.io.File

class VideoActivity : BaseActivity(), SSEduhubTrackSelectionView.TrackSelectionListener {

    private lateinit var item: VideosItem
    private var playbackPosition = 0L
    private var currentWindow = 0
    private var playWhenReady = true
    private lateinit var trackSelector: DefaultTrackSelector
    private lateinit var trackSelectorParameters: DefaultTrackSelector.Parameters
    private var exoPlayer: SimpleExoPlayer? = null
    private lateinit var overrides: List<SelectionOverride>
    private var isDisabled: Boolean = false
    private lateinit var videoViewModel: VideoViewModel
    private lateinit var downloadManager: DownloadManager
    private var filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
    private var downloadReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == -1L) return

            if (Constants.downloadingItem.containsKey(id)) {
                val item = Constants.downloadingItem[id]
                val cursor: Cursor =
                    downloadManager.query(DownloadManager.Query().setFilterById(id))
                if (cursor.moveToFirst()) {
                    val status: Int =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        Constants.downloadingItem.remove(id)
                        videoViewModel.addDownloadVideo(item!!.videoId, 1)
                    }
                }
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST = 1003
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        videoViewModel = ViewModelProviders.of(this)[VideoViewModel::class.java]
        videoViewModel.videoWatchTimeLiveData.observe(this, {
            when (it) {
                is BaseResult.Success -> {
                    it.item.forEach { localVideosItem ->
                        if (localVideosItem.videoId == 123) {
                            exoPlayer!!.seekTo(localVideosItem.watchTime)
                        }
                    }
                }
                is BaseResult.Error -> {
                    makeToastForServerError(it)
                }
            }
        })
        videoViewModel.addDownloadVideoLiveData.observe(this, {
            when (it) {
                is BaseResult.Success -> {
                    Log.d("DownloadVideo", it.item)
                }
                is BaseResult.Error -> {
                    Log.d("DownloadVideo", "${it.errorMessage}")
                }
            }
        })
        if (intent.extras != null) {
            item = intent.getSerializableExtra(Constants.VIDEO_ITEM) as VideosItem
            tvLessonName.text = item.videoTitle
            tvLessonDescription.text = item.videoIntro
            initializePlayer(item.video)
            btnMore.setImageResource(R.drawable.ic_more_disable)
            btnMore.isEnabled = false
            btnDownload.visibility = if (Constants.isPurchased) View.VISIBLE else View.GONE
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }
        btnMore.setOnClickListener {
            getVideoQualityDialog()
        }
        btnDownload.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkPermissions()) {
                    if (!videoViewModel.getBoolean(this, PreferenceConstants.IS_CELLULAR_DATA_ON)) {
                        if (!isWiFiConnected()) showTurnOnWiFiDialog()
                        else downloadVideo(item.video)
                    } else {
                        makeToast("Video downloading over cellular network.")
                        downloadVideo(item.video)
                    }
                } else {
                    requestPermission()
                }
            } else {
                if (!videoViewModel.getBoolean(this, PreferenceConstants.IS_CELLULAR_DATA_ON)) {
                    if (!isWiFiConnected()) showTurnOnWiFiDialog()
                    else downloadVideo(item.video)
                } else {
                    makeToast("Video downloading over cellular network.")
                    downloadVideo(item.video)
                }
            }
        }
        btnFullScreen.setOnCheckedChangeListener { _, isFullScreen ->
            exoPlayer?.let {
                videoView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                val layParams = videoView.layoutParams as ConstraintLayout.LayoutParams
                if (isFullScreen) {
                    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_FULLSCREEN)
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    layParams.matchConstraintPercentHeight = 1F
                } else {
                    window.decorView.systemUiVisibility = (
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            )
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    layParams.matchConstraintPercentHeight = 0.3F
                }
                videoView.layoutParams = layParams
            }
        }

        registerReceiver(downloadReceiver, filter)
    }

    override fun onBackPressed() {
        if (btnFullScreen.isChecked) {
            btnFullScreen.isChecked = false
        } else super.onBackPressed()
    }

    private fun showTurnOnWiFiDialog() {
        val turnOnWiFiDialog = AlertDialog.Builder(this, R.style.DialogStyle).create()
        val view = View.inflate(this, R.layout.lay_dialog_turn_on_wifi, null)
        turnOnWiFiDialog.setView(view)
        view.btnTurnOnWiFiOkay.setOnClickListener {
            turnOnWiFiDialog.dismiss()
        }
        turnOnWiFiDialog.show()
    }

    private fun downloadVideo(videoUrl: String) {
        Constants.downloadingItem.values.map {
            if (it.videoId == item.videoId)
                return
        }
        val uri = Uri.parse(videoUrl)
        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(uri)
        request.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_MOBILE or
                    DownloadManager.Request.NETWORK_WIFI
        )
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setTitle(item.videoTitle)
        val file =
            File(
                Environment.getExternalStorageDirectory().toString() + File.separator
                        + getString(R.string.app_name) + File.separator + "Video"
            )
        if (!file.mkdirs()) {
            file.mkdirs()
        }
        request.setDestinationInExternalPublicDir(file.absolutePath, uri.lastPathSegment)
        request.setVisibleInDownloadsUi(true)

        val downloadId = downloadManager.enqueue(request)
        Constants.downloadingItem[downloadId] = item
    }

    private fun initializePlayer(videoUrl: String) {
        trackSelectorParameters = ParametersBuilder().build()
        trackSelector =
            DefaultTrackSelector(AdaptiveTrackSelection.Factory())
        trackSelector.parameters = trackSelectorParameters
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(this)

        exoPlayer = ExoPlayerFactory.newSimpleInstance(
            this, renderersFactory, trackSelector, loadControl
        )
        exoPlayer!!.addListener(object : Player.EventListener {
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {

            }

            override fun onSeekProcessed() {

            }

            override fun onTracksChanged(
                trackGroups: TrackGroupArray?,
                trackSelections: TrackSelectionArray?
            ) {

            }

            override fun onPlayerError(error: ExoPlaybackException) {
                error.printStackTrace()
                makeToast("Oops something went wrong. Can not play video.")
            }

            override fun onLoadingChanged(isLoading: Boolean) {

            }

            override fun onPositionDiscontinuity(reason: Int) {

            }

            override fun onRepeatModeChanged(repeatMode: Int) {

            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

            }

            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {

            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> videoView.hideController()
                    Player.STATE_READY -> {
                        videoView.showController()
                        btnMore.setImageResource(R.drawable.ic_more)
                        btnMore.isEnabled = true
//                        videoViewModel.getVideoWatchTime(this@VideoActivity, 123)
                    }
                    Player.STATE_ENDED -> {
                        exoPlayer!!.seekTo(0)
                        exoPlayer!!.playWhenReady = false
                        exoPlayer!!.playbackState
                    }
                }
            }
        })
        videoView.player = exoPlayer

        val mediaSource =
            buildMediaSource(Uri.parse(videoUrl))
        exoPlayer!!.prepare(mediaSource, true, false)
        videoView.showController()
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val userAgent = Util.getUserAgent(this, getString(R.string.app_name))
        return if (uri.lastPathSegment!!.contains("m3u8"))
            HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        else
            ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
    }

    override fun onDestroy() {
        val localVideosItem = LocalVideosItem()
        localVideosItem.lessonId = 123
        localVideosItem.videoId = 345
        localVideosItem.watchTime = exoPlayer!!.currentPosition
//        videoViewModel.saveWatchTime(this, localVideosItem)
        releasePlayer()
        unregisterReceiver(downloadReceiver)
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.let {
            it.playWhenReady = false
            it.playbackState
        }
    }

    private fun releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer!!.currentPosition
            currentWindow = exoPlayer!!.currentWindowIndex
            playWhenReady = exoPlayer!!.playWhenReady
            exoPlayer!!.release()
            exoPlayer = null
        }
    }

    private fun getVideoQualityDialog() {
        val dialogBuilder = AlertDialog.Builder(this, R.style.DialogStyle).create()
        val dialogView = View.inflate(this, R.layout.lay_dialog_track_selection, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        val selectionView: SSEduhubTrackSelectionView = dialogView.trackSelectionView

        val mappedTrackInfo = trackSelector.currentMappedTrackInfo
        trackSelectorParameters = trackSelector.parameters
        if (mappedTrackInfo != null) {
            val trackGroupArray: TrackGroupArray = mappedTrackInfo.getTrackGroups(0)
            val initialOverride = trackSelectorParameters.getSelectionOverride(0, trackGroupArray)
            selectionView.init(
                mappedTrackInfo,
                0,
                trackSelectorParameters.getRendererDisabled(0),
                if (initialOverride == null) emptyList() else listOf(initialOverride),
                this
            )
            dialogView.btnOkay.setOnClickListener {
                val builder: ParametersBuilder = trackSelectorParameters.buildUpon()
                for (rendererIndex in 0..mappedTrackInfo.rendererCount) {
                    builder.clearSelectionOverrides(rendererIndex)
                        .setRendererDisabled(rendererIndex, isDisabled)
                    val overrides: List<SelectionOverride> = overrides
                    if (overrides.isNotEmpty()) {
                        builder.setSelectionOverride(
                            0,
                            mappedTrackInfo.getTrackGroups(0),
                            overrides[0]
                        )
                    }
                }
                trackSelector.setParameters(builder)
                dialogBuilder.dismiss()
            }
            dialogView.btnCancel.setOnClickListener {
                dialogBuilder.dismiss()
            }
            dialogBuilder.show()
        }
    }

    override fun onTrackSelectionChanged(
        isDisabled: Boolean,
        overrides: MutableList<SelectionOverride>?
    ) {
        this.isDisabled = isDisabled
        this.overrides = overrides!!
    }

    private fun checkPermissions(): Boolean {
        val writeStorage = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return writeStorage == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            PERMISSION_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST && grantResults.isNotEmpty()) {
            val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (storageAccepted) {
                if (!videoViewModel.getBoolean(this, PreferenceConstants.IS_CELLULAR_DATA_ON)) {
                    if (!isWiFiConnected()) showTurnOnWiFiDialog()
                    else downloadVideo(item.video)
                } else {
                    makeToast("Video downloading over cellular network.")
                    downloadVideo(item.video)
                }
                return
            }
            if (!storageAccepted) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    permissionDeniedDialog(
                        "Permission Denied",
                        "From application settings in permissions block set Storage permission ON. Press \"Settings\" and allow the permission.",
                        "Settings",
                        "Cancel"
                    )
                } else {
                    permissionDeniedDialog(
                        "Permission Denied",
                        "To download the video please allow the permission. Press \"Retry\" and allow the permission.",
                        "Retry",
                        "Cancel"
                    )
                }
            }
        }
    }
}