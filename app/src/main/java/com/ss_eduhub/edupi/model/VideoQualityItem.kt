package com.ss_eduhub.edupi.model

import com.google.android.exoplayer2.trackselection.DefaultTrackSelector

class VideoQualityItem {
    var trackName = ""
    var selectionOverride: List<DefaultTrackSelector.SelectionOverride>? = null
}