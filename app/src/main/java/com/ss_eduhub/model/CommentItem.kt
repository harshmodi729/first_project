package com.ss_eduhub.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CommentItem : Serializable {
    @SerializedName("classes_id")
    val classesId: Int = 0

    @SerializedName("comments")
    var comments: String = ""

    @SerializedName("rating_id")
    val ratingId: Int = 0

    @SerializedName("user_id")
    val userId: Int = 0

    @SerializedName("user_name")
    val userName: String = ""

    @SerializedName("profile")
    var profile: String = ""

    @SerializedName("created")
    var created: String = ""

    @SerializedName("courses_id")
    val coursesId: Int = 0

    @SerializedName("rating")
    var rating: Double = 0.0

    @SerializedName("modified")
    val modified: String = ""

    @SerializedName("status")
    val status: Int = 0

    var userComment = ""
    var userRating = 0.0
}