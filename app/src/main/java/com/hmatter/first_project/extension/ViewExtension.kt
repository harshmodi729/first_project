package com.hmatter.first_project.extension

import androidx.appcompat.widget.AppCompatEditText

/**
 * Method will check whether given [AppCompatEditText] text is blank or empty.
 * Return true if text is blank or empty, false otherwise
 */
fun AppCompatEditText.isBlankOrEmpty(): Boolean {
    return this.text.toString().isBlank() || this.text.toString().isEmpty()
}