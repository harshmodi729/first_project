package com.ss_eduhub.edupi.model

import com.google.gson.annotations.SerializedName

data class SignInItem(
    @SerializedName("social_id")
    val socialId: String = "",
    @SerializedName("profile")
    var profile: String = "",
    @SerializedName("social_flag")
    val socialFlag: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("mobile")
    var mobile: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("email")
    var email: String = "",
    var password: String = ""
)