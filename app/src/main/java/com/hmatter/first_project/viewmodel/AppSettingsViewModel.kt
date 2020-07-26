package com.hmatter.first_project.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.extension.getPreferenceBoolean
import com.hmatter.first_project.extension.setPreferenceBoolean
import com.hmatter.first_project.model.AppSettingsItem
import kotlinx.coroutines.launch

class AppSettingsViewModel : BaseViewModel() {

    val preferenceData = MutableLiveData<BaseResult<AppSettingsItem>>()

    fun setCellularData(
        context: Context,
        isCellularData: Boolean
    ) {
        viewModelScope.launch {
            val preference = getPreferenceManager(context)
            preference.setPreferenceBoolean(PreferenceConstant.IS_CELLULAR_DATA_ON, isCellularData)
        }
    }

    fun setStandardVideoQuality(
        context: Context,
        isStandardVideoQuality: Boolean
    ) {
        viewModelScope.launch {
            val preference = getPreferenceManager(context)
            preference.setPreferenceBoolean(
                PreferenceConstant.IS_STANDARD_VIDEO_QUALITY,
                isStandardVideoQuality
            )
            getAppSettingsPreference(context)
        }
    }

    fun setDeleteCompleted(
        context: Context,
        isDeleteCompleted: Boolean
    ) {
        viewModelScope.launch {
            val preference = getPreferenceManager(context)
            preference.setPreferenceBoolean(
                PreferenceConstant.IS_DELETE_COMPLETED,
                isDeleteCompleted
            )
            getAppSettingsPreference(context)
        }
    }

    fun getAppSettingsPreference(context: Context) {
        viewModelScope.launch {
            try {
                val preference = getPreferenceManager(context)
                val appSettingsItem = AppSettingsItem()
                appSettingsItem.isCellularDataOn = preference.getPreferenceBoolean(
                    IS_CELLULAR_DATA_ON
                )
                appSettingsItem.isStandardQuality = preference.getPreferenceBoolean(
                    IS_STANDARD_VIDEO_QUALITY, true
                )
                appSettingsItem.isDeleteCompleted = preference.getPreferenceBoolean(
                    IS_DELETE_COMPLETED, true
                )
                preferenceData.value = BaseResult.Success(appSettingsItem)
            } catch (exception: Exception) {
                preferenceData.value =
                    BaseResult.Error(IllegalStateException(), exception.localizedMessage!!)
            }
        }
    }
}