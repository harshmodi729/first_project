package com.sseduhub.edupi.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sseduhub.edupi.base.BaseResult
import com.sseduhub.edupi.base.BaseViewModel
import com.sseduhub.edupi.common.PreferenceConstants
import com.sseduhub.edupi.extension.isBlankOrEmpty
import com.sseduhub.edupi.extension.setPreferenceBoolean
import com.sseduhub.edupi.extension.setPreferenceInt
import com.sseduhub.edupi.extension.setPreferenceString
import com.sseduhub.edupi.model.SignInItem
import kotlinx.coroutines.launch

class SignInViewModel : BaseViewModel() {

    val signInLiveData = MutableLiveData<BaseResult<SignInItem>>()
    val visitIntroLiveData = MutableLiveData<BaseResult<Boolean>>()

    fun userSignIn(
        context: Context,
        phoneNumber: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                val validationControl = validationControl(phoneNumber, password)
                if (!validationControl.first) {
                    signInLiveData.value =
                        BaseResult.Error(IllegalStateException(), validationControl.second)
                } else {
                    val response = getApiServiceManager().userLogin(phoneNumber, password)
                    if (response.success) {
                        response.data?.let {
                            setUserProfileData(context, it)
                            signInLiveData.value = BaseResult.Success(it)
                        } ?: kotlin.run {
                            signInLiveData.value =
                                BaseResult.Error(
                                    IllegalStateException(),
                                    "Oops something went wrong."
                                )
                        }
                    } else {
                        signInLiveData.value =
                            BaseResult.Error(IllegalStateException(), response.message)
                    }
                }
            } catch (exception: Exception) {
                signInLiveData.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }

    private fun setUserProfileData(
        context: Context,
        item: SignInItem
    ) {
        val preferenceManager = getPreferenceManager(context)
        preferenceManager.setPreferenceBoolean(PreferenceConstants.IS_USER_LOGIN, true)
        preferenceManager.setPreferenceInt(PreferenceConstants.USER_ID, item.id)
        preferenceManager.setPreferenceString(
            PreferenceConstants.USER_NAME,
            item.name
        )
        preferenceManager.setPreferenceString(
            PreferenceConstants.USER_EMAIL,
            item.email
        )
        preferenceManager.setPreferenceString(
            PreferenceConstants.USER_MOBILE,
            item.mobile
        )
        preferenceManager.setPreferenceString(
            PreferenceConstants.USER_IMAGE,
            item.profile
        )
    }

    private fun validationControl(phoneNumber: String, password: String): Pair<Boolean, String> {
        var isValid = true
        var message = ""
        if (phoneNumber.isBlankOrEmpty() || password.isBlankOrEmpty()) {
            isValid = false
            message = "Please enter valid username and password."
            return Pair(isValid, message)
        }
        if (!phoneNumber.isBlankOrEmpty() && phoneNumber.length != 10) {
            isValid = false
            message = "Please enter valid phone number."
            return Pair(isValid, message)
        }
        return Pair(isValid, message)
    }

    fun setVisitIntro(context: Context) {
        viewModelScope.launch {
            try {
                val preferenceManager = getPreferenceManager(context)
                preferenceManager.setPreferenceBoolean(
                    PreferenceConstants.IS_ALREADY_VISIT_INTRO,
                    true
                )
                visitIntroLiveData.value = BaseResult.Success(true)
            } catch (exception: Exception) {
                visitIntroLiveData.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }
}