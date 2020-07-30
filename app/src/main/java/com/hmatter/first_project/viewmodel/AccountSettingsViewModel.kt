package com.hmatter.first_project.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.common.PreferenceConstants
import com.hmatter.first_project.extension.getPreferenceInt
import com.hmatter.first_project.extension.getPreferenceString
import com.hmatter.first_project.extension.setPreferenceBoolean
import com.hmatter.first_project.model.SignInItem
import kotlinx.coroutines.launch

class AccountSettingsViewModel : BaseViewModel() {

    val userProfile = MutableLiveData<BaseResult<SignInItem>>()
    val userSignOut = MutableLiveData<BaseResult<Boolean>>()

    fun getUserProfileData(context: Context) {
        viewModelScope.launch {
            try {
                val preference = getPreferenceManager(context)
                val item = SignInItem()
                item.id = preference.getPreferenceInt(PreferenceConstants.USER_ID)
                item.name = preference.getPreferenceString(PreferenceConstants.USER_NAME)
                item.email = preference.getPreferenceString(PreferenceConstants.USER_EMAIL)
                item.mobile = preference.getPreferenceString(PreferenceConstants.USER_MOBILE)
                item.password = preference.getPreferenceString(PreferenceConstants.USER_NAME)
                userProfile.value = BaseResult.Success(item)
            } catch (exception: Exception) {
                userProfile.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }

    fun userSignOut(context: Context) {
        viewModelScope.launch {
            try {
                val preference = getPreferenceManager(context)
                preference.edit().clear().commit()
                preference.setPreferenceBoolean(PreferenceConstants.IS_ALREADY_VISIT_INTRO, true)
                preference.setPreferenceBoolean(PreferenceConstants.IS_USER_LOGIN, false)
                userSignOut.value = BaseResult.Success(true)
            } catch (exception: Exception) {
                userProfile.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }
}