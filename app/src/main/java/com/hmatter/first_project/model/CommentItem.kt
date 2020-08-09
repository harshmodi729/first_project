package com.hmatter.first_project.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CommentItem(
    @SerializedName("classes_id")
    val classesId: Int = 0,
    @SerializedName("comments")
    val comments: String = "",
    @SerializedName("rating_id")
    val ratingId: Int = 0,
    @SerializedName("user_id")
    val userId: Int = 0,
    @SerializedName("created")
    val created: String = "",
    @SerializedName("courses_id")
    val coursesId: Int = 0,
    @SerializedName("rating")
    val rating: Int = 0,
    @SerializedName("modified")
    val modified: String = "",
    @SerializedName("status")
    val status: Int = 0
) : Serializable