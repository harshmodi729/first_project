package com.ss_eduhub.model

import com.google.android.exoplayer2.trackselection.DefaultTrackSelector

class VideoQualityItem {
    var trackName = ""
    var selectionOverride: List<DefaultTrackSelector.SelectionOverride>? = null
    var parameters: DefaultTrackSelector.Parameters? = null
}