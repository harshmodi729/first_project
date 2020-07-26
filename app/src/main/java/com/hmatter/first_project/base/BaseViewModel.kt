package com.hmatter.first_project.base

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.hmatter.first_project.remote.ApiManager
import com.hmatter.first_project.remote.ApiServices

abstract class BaseViewModel : ViewModel() {

    companion object PreferenceConstant {
        const val IS_CELLULAR_DATA_ON = "is_cellular_data_on"
        const val IS_STANDARD_VIDEO_QUALITY = "is_standard_video_quality"
        const val IS_DELETE_COMPLETED = "is_delete_completed"
    }

    fun getApiServiceManager(): ApiServices {
        return ApiManager.getApiServices()
    }

    fun getPreferenceManager(context: Context): SharedPreferences {
        return BaseSharedPreference.getPreferenceManager(context)
    }
}