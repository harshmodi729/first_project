package com.ss_eduhub.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TestListItem(
    @SerializedName("test_id")
    val testId: Int,
    @SerializedName("test_name")
    val testName: String,
    @SerializedName("attempted")
    val attempted: Boolean
) : Serializable