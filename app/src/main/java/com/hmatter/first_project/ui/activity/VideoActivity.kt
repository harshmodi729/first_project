package com.hmatter.first_project.ui.activity

import android.net.Uri
import android.os.Bundle
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity
import com.hmatter.first_project.common.Constants
import com.hmatter.first_project.extension.makeToast
import com.hmatter.first_project.model.VideosItem
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.lay_video_playback_control_view.*

class VideoActivity : BaseActivity() {

    private var exoPlayer: ExoPlayer? = null
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
    }

    private fun initializePlayer(videoUrl: String) {
        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(this)

        exoPlayer = ExoPlayerFactory.newSimpleInstance(
            renderersFactory, trackSelector, loadControl
        )
        videoView.player = exoPlayer
        exoPlayer!!.playWhenReady = true

        val mediaSource =
            buildMediaSource(Uri.parse(videoUrl))
        exoPlayer!!.prepare(mediaSource, true, false)
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

            }
        })
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
            exoPlayer!!.playWhenReady = false
            exoPlayer!!.stop()
            exoPlayer!!.release()
            exoPlayer = null
        }
    }
}