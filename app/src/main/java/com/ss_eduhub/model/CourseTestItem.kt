package com.ss_eduhub.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CourseTestItem(
    @SerializedName("course")
    val course: Int,
    @SerializedName("created")
    val created: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("paper")
    val paper: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("time")
    val time: Int
) : Serializable