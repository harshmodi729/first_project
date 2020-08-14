package com.ss_eduhub.edupi.ui.activity

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.ParametersBuilder
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.DefaultTrackNameProvider
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Assertions
import com.google.android.exoplayer2.util.Util
import com.google.gson.Gson
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.adapter.VideoQualityAdapter
import com.ss_eduhub.edupi.base.BaseActivity
import com.ss_eduhub.edupi.common.Constants
import com.ss_eduhub.edupi.extension.makeToast
import com.ss_eduhub.edupi.model.VideoQualityItem
import com.ss_eduhub.edupi.model.VideosItem
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.lay_dialog_video_quality.view.*
import kotlinx.android.synthetic.main.lay_video_playback_control_view.*
import java.util.*
import kotlin.collections.ArrayList

class VideoActivity : BaseActivity() {

    private var playbackPosition = 0L
    private var currentWindow = 0
    private var playWhenReady = true
    private lateinit var trackSelector: DefaultTrackSelector
    private lateinit var trackSelectorParameters: DefaultTrackSelector.Parameters
    private var exoPlayer: ExoPlayer? = null
    private var alVideoQualityItem = ArrayList<VideoQualityItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        if (intent.extras != null) {
            val item = intent.getSerializableExtra(Constants.VIDEO_ITEM) as VideosItem
            tvLessonName.text = item.videoTitle
            tvLessonDescription.text = item.videoIntro
            initializePlayer(item.video)
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }
        btnMore.setOnClickListener {
            Log.d(
                "VideoViewTag",
                "log tracks clicked"
            )
            val mappedTrackInfo =
                Assertions.checkNotNull(trackSelector.currentMappedTrackInfo)
            val parameters: DefaultTrackSelector.Parameters = trackSelector.parameters

            alVideoQualityItem = ArrayList<VideoQualityItem>()
            for (rendererIndex in 0 until mappedTrackInfo.rendererCount) {
                if (showTabForRenderer(mappedTrackInfo, rendererIndex)) {
                    val trackType = mappedTrackInfo.getRendererType(rendererIndex)
                    val trackGroupArray =
                        mappedTrackInfo.getTrackGroups(rendererIndex)
                    val isRendererDisabled =
                        parameters.getRendererDisabled(rendererIndex)
                    val selectionOverride =
                        parameters.getSelectionOverride(rendererIndex, trackGroupArray)
                    Log.d(
                        "VideoViewTag",
                        "------------------------------------------------------Track item $rendererIndex------------------------------------------------------"
                    )
                    Log.d(
                        "VideoViewTag",
                        "track type: " + trackTypeToName(trackType)
                    )
                    Log.d(
                        "VideoViewTag",
                        "track group array: " + Gson().toJson(trackGroupArray)
                    )
                    for (groupIndex in 0 until trackGroupArray.length) {
                        for (trackIndex in 0 until trackGroupArray[groupIndex].length) {
                            val trackName =
                                DefaultTrackNameProvider(resources).getTrackName(
                                    trackGroupArray[groupIndex].getFormat(trackIndex)
                                )
                            val isTrackSupported = mappedTrackInfo.getTrackSupport(
                                rendererIndex,
                                groupIndex,
                                trackIndex
                            ) == RendererCapabilities.FORMAT_HANDLED
                            if (trackType == C.TRACK_TYPE_VIDEO) {
                                val videoQualityItem = VideoQualityItem()
                                videoQualityItem.trackName = trackName
                                var list = mutableListOf<DefaultTrackSelector.SelectionOverride>()
                                if (selectionOverride == null)
                                    list = Collections.emptyList()
                                else
                                    list[0] = selectionOverride
                                videoQualityItem.selectionOverride = list
                                alVideoQualityItem.add(videoQualityItem)
                            }
                            Log.d(
                                "VideoViewTag",
                                "track item $groupIndex: trackName: $trackName, isTrackSupported: $isTrackSupported"
                            )
                        }
                    }
                    Log.d(
                        "VideoViewTag",
                        "isRendererDisabled: $isRendererDisabled"
                    )
                    Log.d(
                        "VideoViewTag",
                        "selectionOverride: " + Gson().toJson(selectionOverride)
                    )
                }
            }

//            getVideoQualityDialog()
        }
        btnFullScreen.setOnCheckedChangeListener { _, isFullScreen ->
            exoPlayer?.let {
                playPausePlayer(false)
                videoView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                val layParams = videoView.layoutParams as ConstraintLayout.LayoutParams
                btnBack.visibility = if (isFullScreen) View.GONE else View.VISIBLE
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
                playPausePlayer(true)
            }
        }
    }

    private fun initializePlayer(videoUrl: String) {
        trackSelectorParameters = ParametersBuilder().build()
        trackSelector =
            DefaultTrackSelector(AdaptiveTrackSelection.Factory(DefaultBandwidthMeter()))
        trackSelector.parameters = trackSelectorParameters
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(this)

        exoPlayer = ExoPlayerFactory.newSimpleInstance(
            renderersFactory, DefaultTrackSelector(), loadControl
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
                    Player.STATE_READY -> videoView.showController()
                    Player.STATE_ENDED -> {
                        exoPlayer!!.seekTo(0)
                        playPausePlayer(false)
                    }
                }
                willHaveContent(trackSelector)
            }
        })
        videoView.player = exoPlayer

        val mediaSource =
            buildMediaSource(Uri.parse(videoUrl))
        exoPlayer!!.prepare(mediaSource, true, false)
        videoView.showController()
//        willHaveContent(trackSelector)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val userAgent = Util.getUserAgent(this, getString(R.string.app_name))
        return ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
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

    /**
     * Returns whether a track selection dialog will have content to display if initialized with the
     * specified [DefaultTrackSelector] in its current state.
     */
    private fun willHaveContent(trackSelector: DefaultTrackSelector): Boolean {
        val mappedTrackInfo = trackSelector.currentMappedTrackInfo
        return mappedTrackInfo != null && willHaveContent(mappedTrackInfo)
    }

    /**
     * Returns whether a track selection dialog will have content to display if initialized with the
     * specified [MappedTrackInfo].
     */
    private fun willHaveContent(mappedTrackInfo: MappedTrackInfo): Boolean {
        for (i in 0 until mappedTrackInfo.rendererCount) {
            if (showTabForRenderer(mappedTrackInfo, i)) {
                return true
            }
        }
        return false
    }

    private fun showTabForRenderer(
        mappedTrackInfo: MappedTrackInfo,
        rendererIndex: Int
    ): Boolean {
        val trackGroupArray = mappedTrackInfo.getTrackGroups(rendererIndex)
        if (trackGroupArray.length == 0) {
            return false
        }
        val trackType = mappedTrackInfo.getRendererType(rendererIndex)
        return isSupportedTrackType(
            trackType
        )
    }

    private fun isSupportedTrackType(trackType: Int): Boolean {
        return when (trackType) {
            C.TRACK_TYPE_VIDEO, C.TRACK_TYPE_AUDIO, C.TRACK_TYPE_TEXT -> true
            else -> false
        }
    }

    private fun trackTypeToName(trackType: Int): String? {
        return when (trackType) {
            C.TRACK_TYPE_VIDEO -> "TRACK_TYPE_VIDEO"
            C.TRACK_TYPE_AUDIO -> "TRACK_TYPE_AUDIO"
            C.TRACK_TYPE_TEXT -> "TRACK_TYPE_TEXT"
            else -> "Invalid track type"
        }
    }

    private fun getVideoQualityDialog() {
        val dialog = AlertDialog.Builder(this, R.style.DialogStyle).create()
        val view = View.inflate(this, R.layout.lay_dialog_video_quality, null)
        val adapter = VideoQualityAdapter(this)
        view.rvVideoQuality.adapter = adapter
        adapter.addData(alVideoQualityItem)
        adapter.onVideoQualityChecked = { selectorOverride ->
            makeToast("Done")
        }
        view.btnOkay.setOnClickListener {
            dialog.dismiss()
        }
        view.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setView(view)
        dialog.setCancelable(false)
        dialog.show()
    }
}