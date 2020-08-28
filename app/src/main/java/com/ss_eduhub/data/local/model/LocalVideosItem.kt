package com.ss_eduhub.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
class LocalVideosItem {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var lessonId = 0
    var videoId = 0
    var watchTime = 0L
}