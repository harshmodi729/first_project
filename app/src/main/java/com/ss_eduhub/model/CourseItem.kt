package com.ss_eduhub.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CourseItem(
    @SerializedName("course_id")
    val courseId: Int,
    @SerializedName("course_name")
    val courseName: String
) : Serializable