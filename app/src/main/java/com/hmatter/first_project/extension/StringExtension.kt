package com.hmatter.first_project.extension

import android.text.TextUtils

/**
 * Method will check whether given [String] is blank or empty.
 * Return true if [String] is blank or empty, false otherwise
 */
fun String.isBlankOrEmpty(): Boolean {
    return this.isBlank() || this.isEmpty()
}

/**
 * Method will check whether given [String] is valid for Email address or not.
 */
fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}