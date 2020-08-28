package com.ss_eduhub.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse<T> {
    @Expose
    @SerializedName("success", alternate = ["response"])
    val success: Boolean = true

    @Expose
    @SerializedName("user_data", alternate = ["data"])
    val data: T? = null

    @Expose
    @SerializedName("message", alternate = ["msg"])
    val message: String = ""
}