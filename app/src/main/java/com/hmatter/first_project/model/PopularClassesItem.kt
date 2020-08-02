package com.hmatter.first_project.model

import com.google.gson.annotations.SerializedName

data class PopularClassesItem(@SerializedName("thumbnail")
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
                              val videos: List<VideosItem>?,
                              @SerializedName("title")
                              val title: String = "",
                              @SerializedName("pdf")
                              val pdf: List<PdfItem>?,
                              @SerializedName("ratings")
                              val ratings: Int = 0,
                              @SerializedName("cat_id")
                              val catId: Int = 0,
                              @SerializedName("pdfCount")
                              val pdfCount: Int = 0,
                              @SerializedName("about_course")
                              val aboutCourse: String = "")