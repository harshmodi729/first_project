package com.ss_eduhub.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class QuestionItem(
    @SerializedName("ans")
    val ans: String,
//    @SerializedName("data")
//    val dataX: DataX,
    @SerializedName("options")
    val options: List<String>,
    @SerializedName("question")
    val question: String
) : Serializable {
    var isAnswered = false
    var answers = ArrayList<Answers>()
}

data class DataX(
    @SerializedName("ans")
    val ans: String,
    @SerializedName("created")
    val created: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("option1")
    val option1: String,
    @SerializedName("option2")
    val option2: String,
    @SerializedName("option3")
    val option3: String,
    @SerializedName("option4")
    val option4: String,
    @SerializedName("paper")
    val paper: Int,
    @SerializedName("question")
    val question: String,
    @SerializedName("status")
    val status: Int
) : Serializable

data class Answers(
    val answer: String,
    var isChecked: Boolean = false,
    var isCorrect: Boolean = false,
) : Serializable {
    var isAnswered = false
}