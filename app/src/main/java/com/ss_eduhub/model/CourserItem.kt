package com.ss_eduhub.model


import com.google.gson.annotations.SerializedName

data class CourserItem(
    @SerializedName("course_id")
    val courseId: Int,
    @SerializedName("course_name")
    val courseName: String
)