package com.hmatter.first_project.extension

import android.content.SharedPreferences

fun SharedPreferences.erase() {
    this.edit().clear().commit()
}

/**
 * setPreferenceString will set string value into given key
 * @param key is set as an unique id in sharedPreference class
 * @param value string will set on given key
 */
fun SharedPreferences.setPreferenceString(key: String, value: String) {
    this.edit().putString(key, value).apply()
}

/**
 * getPreferenceString will get string value into given key
 * @param key to get an value, which is set on given key
 */
fun SharedPreferences.getPreferenceString(key: String, defaultValue: String = ""): String {
    return this.getString(key, defaultValue)!!
}

/**
 * setPreferenceInt will set string value into given key
 * @param key is set as an unique id in sharedPreference class
 * @param value int will set on given key
 */
fun SharedPreferences.setPreferenceInt(key: String, value: Int) {
    this.edit().putInt(key, value).apply()
}

/**
 * getPreferenceInt will get string value into given key
 * @param key to get an value, which is set on given key
 */
fun SharedPreferences.getPreferenceInt(key: String, defaultValue: Int = 0): Int {
    return this.getInt(key, defaultValue)
}

/**
 * setPreferenceBoolean will set boolean value into given key
 * @param key is set as an unique id in sharedPreference class
 * @param value boolean will set on given key
 */
fun SharedPreferences.setPreferenceBoolean(key: String, value: Boolean) {
    this.edit().putBoolean(key, value).apply()
}

/**
 * getPreferenceBoolean will get boolean value into given key
 * @param key to get an value, which is set on given key
 */
fun SharedPreferences.getPreferenceBoolean(key: String, defaultValue: Boolean = false): Boolean {
    return this.getBoolean(key, defaultValue)
}