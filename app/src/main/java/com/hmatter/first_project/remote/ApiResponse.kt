package com.hmatter.first_project.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse<T> {
    @Expose
    @SerializedName("success", alternate = ["RESULT"])
    val success: String = ""

    @Expose
    @SerializedName("data", alternate = ["DATA"])
    val data: T? = null

    @Expose
    @SerializedName("message", alternate = ["MESSAGE"])
    val message: String = ""

    fun isSuccess(): Boolean {
        return success > "0" || success.equals("success", true)
    }
}