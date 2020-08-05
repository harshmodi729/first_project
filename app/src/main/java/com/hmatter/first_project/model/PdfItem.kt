package com.hmatter.first_project.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PdfItem(@SerializedName("classes_id")
                   val classesId: Int = 0,
                   @SerializedName("pdf_file")
                   val pdfFile: String = "",
                   @SerializedName("created")
                   val created: String = "",
                   @SerializedName("pdf_id")
                   val pdfId: Int = 0,
                   @SerializedName("courses_id")
                   val coursesId: Int = 0,
                   @SerializedName("pdf_title")
                   val pdfTitle: String = "",
                   @SerializedName("modified")
                   val modified: String = "",
                   @SerializedName("pef_description")
                   val pefDescription: String = "",
                   @SerializedName("status")
                   val status: Int = 0
) : Serializable