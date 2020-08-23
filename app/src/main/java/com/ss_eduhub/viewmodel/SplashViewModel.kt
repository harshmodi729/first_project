package com.ss_eduhub.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import com.ss_eduhub.common.PreferenceConstants
import com.ss_eduhub.extension.getPreferenceBoolean
import com.ss_eduhub.extension.getPreferenceInt
import com.ss_eduhub.extension.getPreferenceString
import com.ss_eduhub.model.SignInItem
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {

    val isLoginLiveData = MutableLiveData<BaseResult<Pair<Boolean, Boolean>>>()

    fun isUserLogin(context: Context) {
        viewModelScope.launch {
            try {
                val preferenceManager = getPreferenceManager(context)
                val isLogin =
                    preferenceManager.getPreferenceBoolean(PreferenceConstants.IS_USER_LOGIN)
                val isAlreadyVisitIntro =
                    preferenceManager.getPreferenceBoolean(PreferenceConstants.IS_ALREADY_VISIT_INTRO)
                val item = SignInItem()
                item.id = preferenceManager.getPreferenceInt(PreferenceConstants.USER_ID)
                item.name = preferenceManager.getPreferenceString(PreferenceConstants.USER_NAME)
                item.email = preferenceManager.getPreferenceString(PreferenceConstants.USER_EMAIL)
                item.mobile = preferenceManager.getPreferenceString(PreferenceConstants.USER_MOBILE)
                item.profile = preferenceManager.getPreferenceString(PreferenceConstants.USER_IMAGE)
                Constants.userProfileData = item
                isLoginLiveData.value = BaseResult.Success(Pair(isLogin, isAlreadyVisitIntro))
            } catch (exception: Exception) {
                isLoginLiveData.value = BaseResult.Error(exception)
            }
        }
    }
}