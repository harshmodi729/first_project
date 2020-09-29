package com.ss_eduhub.model

import com.google.gson.annotations.SerializedName

class SliderItem {
    @SerializedName("first_text")
    var detail: String = ""

    @SerializedName("created")
    val created: String = ""

    @SerializedName("banner")
    val imagePath: String = ""

    @SerializedName("modified")
    val modified: String = ""

    @SerializedName("id")
    val id: Int = 0

    @SerializedName("title")
    var title: String = ""

    @SerializedName("status")
    val status: Int = 0
}