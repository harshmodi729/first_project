package com.hmatter.first_project.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse<T> {
    @Expose
    @SerializedName("success", alternate = ["SUCCESS"])
    val success: Boolean = true

    @Expose
    @SerializedName("user_data", alternate = ["USER_DATA"])
    val data: T? = null

    @Expose
    @SerializedName("message", alternate = ["MESSAGE"])
    val message: String = ""
}