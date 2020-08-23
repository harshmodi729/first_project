package com.ss_eduhub.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideosItem(
    @SerializedName("classes_id")
    val classesId: Int = 0,
    @SerializedName("video_title")
    val videoTitle: String = "",
    @SerializedName("created")
    val created: String = "",
    @SerializedName("courses_id")
    val coursesId: Int = 0,
    @SerializedName("modified")
    val modified: String = "",
    @SerializedName("video")
    val video: String = "",
    @SerializedName("video_id")
    val videoId: Int = 0,
    @SerializedName("video_intro")
    val videoIntro: String = "",
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("thumbnail")
    val thumbnail: String? = null
) : Serializable