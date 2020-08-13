package com.ss_eduhub.edupi.base

import android.content.Context
import android.content.SharedPreferences
import com.ss_eduhub.edupi.BuildConfig

class BaseSharedPreference {

    companion object {
        private const val PREFERENCE_NAME: String = BuildConfig.APPLICATION_ID
        private var preference: SharedPreferences? = null
        private lateinit var editor: SharedPreferences.Editor

        fun getPreferenceManager(context: Context): SharedPreferences {
            if (preference == null) {
                preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            }
            editor = preference?.edit()!!
            return preference!!
        }
    }
}