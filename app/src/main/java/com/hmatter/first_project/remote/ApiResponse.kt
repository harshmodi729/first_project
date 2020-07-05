package com.hmatter.first_project.remote

import com.google.gson.annotations.SerializedName

open class ApiResponse<T> {
    @SerializedName("data")
    val data: T? = null

    @SerializedName("result")
    val result: Int = 0

    @SerializedName("message")
    val message: String = ""

    fun isSuccess(): Boolean {
        return result > 0
    }
}