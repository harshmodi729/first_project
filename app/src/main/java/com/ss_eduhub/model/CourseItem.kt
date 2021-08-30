package com.ss_eduhub.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CourseItem(
    @SerializedName("about_course")
    val aboutCourse: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("cat_id")
    val catId: Int,
    @SerializedName("course_id")
    val courseId: Int,
    @SerializedName("created")
    val created: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("popular")
    val popular: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("short_intro")
    val shortIntro: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String
) : Serializable