package com.sseduhub.edupi.base

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sseduhub.edupi.extension.getPreferenceBoolean
import com.sseduhub.edupi.extension.setPreferenceBoolean
import com.sseduhub.edupi.remote.ApiManager
import com.sseduhub.edupi.remote.ApiServices
import com.sseduhub.edupi.remote.DummyApiManager
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun getApiServiceManager(): ApiServices {
        return ApiManager.getApiServices()
    }

    fun getDummyApiServiceManager(): ApiServices {
        return DummyApiManager.getApiServices()
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