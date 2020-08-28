package com.ss_eduhub.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DummyApiResponse<T> {
    @Expose
    @SerializedName("result", alternate = ["RESULT"])
    val success: Int = 0

    @Expose
    @SerializedName("data", alternate = ["DATA"])
    val data: T? = null

    @Expose
    @SerializedName("message", alternate = ["MESSAGE"])
    val message: String = ""

    fun isSuccess(): Boolean {
        return success > 0
    }
}