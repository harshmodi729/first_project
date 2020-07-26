package com.hmatter.first_project.base

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.extension.getPreferenceBoolean
import com.hmatter.first_project.extension.setPreferenceBoolean
import com.hmatter.first_project.remote.ApiManager
import com.hmatter.first_project.remote.ApiServices
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun getApiServiceManager(): ApiServices {
        return ApiManager.getApiServices()
    }

    fun getPreferenceManager(context: Context): SharedPreferences {
        return BaseSharedPreference.getPreferenceManager(context)
    }

    fun setBoolean(
        context: Context,
        key: String,
        value: Boolean
    ) {
        viewModelScope.launch {
            val preference = getPreferenceManager(context)
            preference.setPreferenceBoolean(key, value)
        }
    }

    fun getBoolean(
        context: Context,
        key: String,
        defaultValue: Boolean = false
    ): Boolean {
        val preference = getPreferenceManager(context)
        return preference.getPreferenceBoolean(key, defaultValue)
    }
}