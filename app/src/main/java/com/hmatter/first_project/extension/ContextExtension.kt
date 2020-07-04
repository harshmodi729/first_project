package com.hmatter.first_project.extension

import android.content.Context
import android.widget.Toast

fun Context.makeToast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}