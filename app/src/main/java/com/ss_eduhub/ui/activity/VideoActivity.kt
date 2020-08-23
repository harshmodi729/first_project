package com.ss_eduhub.ui.activity

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.ss_eduhub.common.Constants
import com.ss_eduhub.extension.makeToast
import com.ss_eduhub.model.VideosItem
import com.ss_eduhub.widget.SSEduhubTrackSelectionView
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.lay_dialog_track_selection.view.*
import kotlinx.android.synthetic.main.lay_video_playback_control_view.*

class VideoActivity : BaseActivity(), SSEduhubTrackSelectionView.TrackSelectionListener {

    private var playbackPosition = 0L
    private var currentWindow = 0
    private var playWhenReady = true
    private lateinit var trackSelector: DefaultTrackSelector
    private lateinit var trackSelectorParameters: DefaultTrackSelector.Parameters
    private var exoPlayer: SimpleExoPlayer? = null
    private lateinit var overrides: List<SelectionOverride>
    private var isDisabled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        if (intent.extras != null) {
            val item = intent.getSerializableExtra(Constants.VIDEO_ITEM) as VideosItem
            tvLessonName.text = item.videoTitle
            tvLessonDescription.text = item.videoIntro
            initializePlayer(item.video)
            btnMore.setImageResource(R.drawable.ic_more_disable)
            btnMore.isEnabled = false
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }
        btnMore.setOnClickListener {
            getVideoQualityDialog()
        }
        btnFullScreen.setOnCheckedChangeListener { _, isFullScreen ->
            exoPlayer?.let {
                videoView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                val layParams = videoView.layoutParams as ConstraintLayout.LayoutParams
                if (isFullScreen) {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    layParams.matchConstraintPercentHeight = 1F
                    layParams.topMargin = 0
                } else {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    layParams.matchConstraintPercentHeight = 0.3F
                    layParams.topMargin = resources.getDimension(R.dimen.large_margin).toInt()
                }
                videoView.layoutParams = layParams
            }
        }
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
                    }
                    Player.STATE_ENDED -> {
                        exoPlayer!!.seekTo(0)
                        playPausePlayer(false)
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
        if (uri.lastPathSegment!!.contains("m3u8"))
            return HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        else
            return ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
    }

    override fun onDestroy() {
        releasePlayer()
        super.onDestroy()
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

    private fun playPausePlayer(isPlay: Boolean) {
        exoPlayer!!.playWhenReady = isPlay
        exoPlayer!!.playbackState
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
}