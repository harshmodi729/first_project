package com.hmatter.first_project.model

import com.google.gson.annotations.SerializedName

data class PopularTagCategoryItem (
    @SerializedName("category")
    val categoryName: String = "",
    @SerializedName("cat_id")
    val categoryId: Int = 0
)