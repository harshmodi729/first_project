package com.sseduhub.edupi.model

import com.google.gson.annotations.SerializedName

data class PopularTagCategoryItem (
    @SerializedName("category")
    val categoryName: String = "",
    @SerializedName("cat_id")
    val categoryId: Int = 0
)