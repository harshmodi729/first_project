package com.ss_eduhub.edupi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PopularClassItem(
    @Expose
    @SerializedName("tutor_name")
    val tutorName: String = "",

    @Expose
    @SerializedName("class_price")
    val classPrice: String = "",

    @Expose
    @SerializedName("class_category")
    val classCategory: ArrayList<CategoryItem> = ArrayList(),

    @Expose
    @SerializedName("class_rating")
    val classRating: Float = 0F,

    @Expose
    @SerializedName("class_detail")
    val classDetail: String = "",

    @Expose
    @SerializedName("total_videos")
    val totalVideos: Int = 0
)

data class CategoryItem(
    @Expose
    @SerializedName("category_name")
    val categoryName: String = ""
)


