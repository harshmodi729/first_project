package com.ss_eduhub.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CourseTestItem(
    @SerializedName("test_id")
    val testId: Int,
    @SerializedName("test_name")
    val testName: String
) : Serializable