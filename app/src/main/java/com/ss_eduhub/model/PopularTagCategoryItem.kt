package com.ss_eduhub.model

import com.google.gson.annotations.SerializedName

data class PopularTagCategoryItem (
    @SerializedName("category")
    val categoryName: String = "",
    @SerializedName("cat_id")
    val categoryId: Int = 0
)