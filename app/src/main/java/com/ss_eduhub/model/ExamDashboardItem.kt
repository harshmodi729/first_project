package com.ss_eduhub.model


import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName

data class ExamDashboardItem(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: Drawable
)