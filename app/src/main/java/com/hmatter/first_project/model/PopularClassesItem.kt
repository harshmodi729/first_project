package com.hmatter.first_project.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PopularClassesItem(
    @SerializedName("thumbnail")
    val thumbnail: String = "",
    @SerializedName("cat_name")
    val catName: String = "",
    @SerializedName("short_intro")
    val shortIntro: String = "",
    @SerializedName("author")
    val author: String = "",
    @SerializedName("videosCount")
    val videosCount: Int = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("videos")
    val videos: ArrayList<VideosItem>,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("pdf")
    val pdf: ArrayList<PdfItem>,
    @SerializedName("ratings")
    val ratings: Int = 0,
    @SerializedName("cat_id")
    val catId: Int = 0,
    @SerializedName("pdfCount")
    val pdfCount: Int = 0,
    @SerializedName("about_course")
    val aboutCourse: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("class_id")
    val classId: Int = 0,
    @SerializedName("wishlist")
    val wishList: Int = 0
) : Serializable