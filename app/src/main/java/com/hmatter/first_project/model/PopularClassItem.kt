package com.hmatter.first_project.model

import com.google.gson.annotations.SerializedName

class PopularClassItem {
    @SerializedName("tutor_name")
    val tutorName: String = ""

    @SerializedName("class_price")
    val classPrice: String = ""

    @SerializedName("class_category")
    val classCategory: ArrayList<CategoryItem> = ArrayList()

    @SerializedName("class_rating")
    val classRating: Double = 0.0

    @SerializedName("class_detail")
    val classDetail: String = ""

    @SerializedName("total_videos")
    val totalVideos: Int = 0
}

class CategoryItem {
    @SerializedName("category_name")
    val categoryName: String = ""
}


