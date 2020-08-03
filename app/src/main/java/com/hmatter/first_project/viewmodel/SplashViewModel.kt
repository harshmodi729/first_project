package com.hmatter.first_project.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.common.Constants
import com.hmatter.first_project.common.PreferenceConstants
import com.hmatter.first_project.extension.getPreferenceBoolean
import com.hmatter.first_project.extension.getPreferenceInt
import com.hmatter.first_project.extension.getPreferenceString
import com.hmatter.first_project.model.SignInItem
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
                isLoginLiveData.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }
}