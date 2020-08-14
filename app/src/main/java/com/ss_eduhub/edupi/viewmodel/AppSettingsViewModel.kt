package com.ss_eduhub.edupi.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ss_eduhub.edupi.base.BaseResult
import com.ss_eduhub.edupi.base.BaseViewModel
import com.ss_eduhub.edupi.common.PreferenceConstants
import com.ss_eduhub.edupi.model.AppSettingsItem
import kotlinx.coroutines.launch

class AppSettingsViewModel : BaseViewModel() {

    val preferenceLiveData = MutableLiveData<BaseResult<AppSettingsItem>>()

    fun getAppSettingsPreference(context: Context) {
        viewModelScope.launch {
            try {
                val preference = getPreferenceManager(context)
                val appSettingsItem = AppSettingsItem()
                appSettingsItem.isCellularDataOn = getBoolean(
                    context,
                    PreferenceConstants.IS_CELLULAR_DATA_ON
                )
                appSettingsItem.isStandardVideoQuality = getBoolean(
                    context,
                    PreferenceConstants.IS_STANDARD_VIDEO_QUALITY, true
                )
                appSettingsItem.isDeleteCompleted = getBoolean(
                    context,
                    PreferenceConstants.IS_DELETE_COMPLETED, true
                )
                preferenceLiveData.value = BaseResult.Success(appSettingsItem)
            } catch (exception: Exception) {
                preferenceLiveData.value =
                    BaseResult.Error(IllegalStateException(), exception.localizedMessage!!)
            }
        }
    }
}