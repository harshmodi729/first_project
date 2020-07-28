package com.hmatter.first_project.extension

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.StyleSpan

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

/**
 * Method will bold given [String] word/sentence in whole [String]
 */
fun String.bold(word: String): SpannableStringBuilder {
    val spannableString = SpannableStringBuilder(this)
    spannableString.setSpan(
        StyleSpan(Typeface.BOLD), this.indexOf(this),
        (this.indexOf(word) + word.length), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString
}