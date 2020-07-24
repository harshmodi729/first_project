package com.hmatter.first_project.extension

import androidx.appcompat.widget.AppCompatEditText

fun AppCompatEditText.isBlankOrEmpty(): Boolean {
    return this.text.toString().isBlank() || this.text.toString().isEmpty()
}