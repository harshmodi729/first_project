package com.hmatter.first_project.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.common.PreferenceConstants
import com.hmatter.first_project.extension.isBlankOrEmpty
import com.hmatter.first_project.extension.setPreferenceInt
import com.hmatter.first_project.extension.setPreferenceString
import com.hmatter.first_project.model.SignInItem
import kotlinx.coroutines.launch

class SignInViewModel : BaseViewModel() {

    val userSignInData = MutableLiveData<BaseResult<SignInItem>>()

    fun userSignIn(
        context: Context,
        phoneNumber: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                val validationControl = validationControl(phoneNumber)
                if (!validationControl.first) {
                    userSignInData.value =
                        BaseResult.Error(IllegalStateException(), validationControl.second)
                } else {
                    val response = getApiServiceManager().userLogin(phoneNumber, password)
                    if (response.isSuccess()) {
                        response.data?.let {
                            setUserProfileData(context, it)
                            userSignInData.value = BaseResult.Success(it)
                        } ?: kotlin.run {
                            userSignInData.value =
                                BaseResult.Error(
                                    IllegalStateException(),
                                    "Oops something went wrong."
                                )
                        }
                    } else {
                        userSignInData.value =
                            BaseResult.Error(IllegalStateException(), response.message)
                    }
                }
            } catch (exception: Exception) {
                userSignInData.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }

    private fun setUserProfileData(
        context: Context,
        item: SignInItem
    ) {
        val preferenceManager = getPreferenceManager(context)
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
    }

    private fun validationControl(phoneNumber: String): Pair<Boolean, String> {
        var isValid = true
        var message = ""
        if (!phoneNumber.isBlankOrEmpty() && phoneNumber.length != 10) {
            isValid = false
            message = "Please enter valid phone number."
            return Pair(isValid, message)
        }
        return Pair(isValid, message)
    }
}