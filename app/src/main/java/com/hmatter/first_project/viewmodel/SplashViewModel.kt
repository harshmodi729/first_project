package com.hmatter.first_project.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.common.PreferenceConstants
import com.hmatter.first_project.extension.getPreferenceBoolean
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {

    val userLoginCheck = MutableLiveData<BaseResult<Pair<Boolean, Boolean>>>()

    fun isUserLogin(context: Context) {
        viewModelScope.launch {
            try {
                val preference = getPreferenceManager(context)
                val isLogin = preference.getPreferenceBoolean(PreferenceConstants.IS_USER_LOGIN)
                val isAlreadyVisitIntro =
                    preference.getPreferenceBoolean(PreferenceConstants.IS_ALREADY_VISIT_INTRO)
                userLoginCheck.value = BaseResult.Success(Pair(isLogin, isAlreadyVisitIntro))
            } catch (exception: Exception) {
                userLoginCheck.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }
}