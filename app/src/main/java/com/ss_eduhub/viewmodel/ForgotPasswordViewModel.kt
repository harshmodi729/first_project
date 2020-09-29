package com.ss_eduhub.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.PreferenceConstants
import com.ss_eduhub.extension.getPreferenceInt
import com.ss_eduhub.extension.isBlankOrEmpty
import com.ss_eduhub.extension.setPreferenceInt
import com.ss_eduhub.model.ForgotPasswordItem
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : BaseViewModel() {

    private var isFromForgotPassword = true
    val verifyMobileLiveData = MutableLiveData<BaseResult<String>>()
    val resetPasswordLiveData = MutableLiveData<BaseResult<String>>()

    fun verifyMobileNumber(context: Context, mobile: String) {
        viewModelScope.launch {
            try {
                val response = getApiServiceManager().forgotPassword(mobile)
                if (response.success) {
                    response.data?.let {
                        getPreferenceManager(context).setPreferenceInt(
                            PreferenceConstants.USER_ID,
                            it[0].asJsonObject["id"].asInt
                        )
                        verifyMobileLiveData.value = BaseResult.Success(response.message)
                    } ?: kotlin.run {
                        verifyMobileLiveData.value =
                            BaseResult.Error(IllegalStateException(), response.message)
                    }
                } else {
                    verifyMobileLiveData.value =
                        BaseResult.Error(IllegalStateException(), response.message)
                }
            } catch (exception: Exception) {
                verifyMobileLiveData.value = BaseResult.Error(exception)
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
                        resetPasswordLiveData.value = BaseResult.Success(response.message)
                    } else {
                        resetPasswordLiveData.value =
                            BaseResult.Error(IllegalStateException(), response.message)
                    }
                } else {
                    resetPasswordLiveData.value =
                        BaseResult.Error(IllegalStateException(), validation.second)
                }
            } catch (exception: Exception) {
                resetPasswordLiveData.value = BaseResult.Error(exception)
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