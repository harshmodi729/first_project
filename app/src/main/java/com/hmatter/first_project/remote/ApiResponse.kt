package com.hmatter.first_project.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse<T> {
    @Expose
    @SerializedName("result")
    val result: Int = 0

    @Expose
    @SerializedName("data")
    val data: T? = null

    @Expose
    @SerializedName("message")
    val message: String = ""

    fun isSuccess(): Boolean {
        return result > 0
    }
}