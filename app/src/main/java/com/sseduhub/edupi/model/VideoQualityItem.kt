package com.sseduhub.edupi.model

import com.google.android.exoplayer2.trackselection.DefaultTrackSelector

class VideoQualityItem {
    var trackName = ""
    var selectionOverride: List<DefaultTrackSelector.SelectionOverride>? = null
}