package com.hmatter.first_project.model

import com.google.gson.annotations.SerializedName

data class VideoCategoryItem(
    @SerializedName("category_name")
    val categoryName: String = "",
    @SerializedName("category_id")
    val categoryId: Int = 0
)