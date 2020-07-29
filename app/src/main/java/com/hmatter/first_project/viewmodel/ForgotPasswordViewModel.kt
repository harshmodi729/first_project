package com.hmatter.first_project.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.common.PreferenceConstants
import com.hmatter.first_project.extension.getPreferenceInt
import com.hmatter.first_project.extension.isBlankOrEmpty
import com.hmatter.first_project.model.ForgotPasswordItem
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : BaseViewModel() {

    private var isFromForgotPassword = true
    val verifyMobile = MutableLiveData<BaseResult<String>>()
    val userResetPassword = MutableLiveData<BaseResult<String>>()

    fun verifyMobileNumber(mobile: String) {
        viewModelScope.launch {
            try {
                val response = getApiServiceManager().forgotPassword(mobile)
                if (response.success) {
                    verifyMobile.value = BaseResult.Success(response.message)
                } else {
                    verifyMobile.value = BaseResult.Error(IllegalStateException(), response.message)
                }
            } catch (exception: Exception) {
                verifyMobile.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }

    fun resetPassword(
        context: Context,
        forgotPasswordItem: ForgotPasswordItem,
        isFromForgotPassword: Boolean
    ) {
        this.isFromForgotPassword = isFromForgotPassword
        viewModelScope.launch {
            try {
                val validation = validationControl(forgotPasswordItem)
                if (validation.first) {
                    val userId =
                        getPreferenceManager(context).getPreferenceInt(PreferenceConstants.USER_ID)
                    val response = getApiServiceManager().changePassword(
                        userId,
                        forgotPasswordItem.oldPassword,
                        forgotPasswordItem.newPassword
                    )
                    if (response.success) {
                        userResetPassword.value = BaseResult.Success(response.message)
                    } else {
                        userResetPassword.value =
                            BaseResult.Error(IllegalStateException(), response.message)
                    }
                } else {
                    userResetPassword.value =
                        BaseResult.Error(IllegalStateException(), validation.second)
                }
            } catch (exception: Exception) {
                userResetPassword.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }

    private fun validationControl(forgotPasswordItem: ForgotPasswordItem): Pair<Boolean, String> {
        var isValid = true
        var message = ""
        if (forgotPasswordItem.oldPassword.isBlankOrEmpty() ||
            forgotPasswordItem.newPassword.isBlankOrEmpty()
        ) {
            isValid = false
            message = "Empty or blank is not allowed. Please enter valid password."
            return Pair(isValid, message)
        }
        if (forgotPasswordItem.newPassword.length <= 3) {
            isValid = false
            message = "Password length must be more than 3 letters."
            return Pair(isValid, message)
        }
        if (!isFromForgotPassword) {
            if (forgotPasswordItem.newPassword != forgotPasswordItem.confirmPassword) {
                isValid = false
                message = "Confirm password doesn't match."
                return Pair(isValid, message)
            }
        }
        return Pair(isValid, message)
    }
}