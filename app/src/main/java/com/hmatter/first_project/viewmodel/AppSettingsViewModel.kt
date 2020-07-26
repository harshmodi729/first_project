package com.hmatter.first_project.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.common.PreferenceConstant
import com.hmatter.first_project.model.AppSettingsItem
import kotlinx.coroutines.launch

class AppSettingsViewModel : BaseViewModel() {

    val preferenceData = MutableLiveData<BaseResult<AppSettingsItem>>()

    fun getAppSettingsPreference(context: Context) {
        viewModelScope.launch {
            try {
                val preference = getPreferenceManager(context)
                val appSettingsItem = AppSettingsItem()
                appSettingsItem.isCellularDataOn = getBoolean(
                    context,
                    PreferenceConstant.IS_CELLULAR_DATA_ON
                )
                appSettingsItem.isStandardVideoQuality = getBoolean(
                    context,
                    PreferenceConstant.IS_STANDARD_VIDEO_QUALITY, true
                )
                appSettingsItem.isDeleteCompleted = getBoolean(
                    context,
                    PreferenceConstant.IS_DELETE_COMPLETED, true
                )
                preferenceData.value = BaseResult.Success(appSettingsItem)
            } catch (exception: Exception) {
                preferenceData.value =
                    BaseResult.Error(IllegalStateException(), exception.localizedMessage!!)
            }
        }
    }
}