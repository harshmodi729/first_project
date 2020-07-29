package com.hmatter.first_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.extension.isBlankOrEmpty
import com.hmatter.first_project.extension.isEmailValid
import com.hmatter.first_project.model.SignUpItem
import kotlinx.coroutines.launch

class SignUpViewModel : BaseViewModel() {

    val userRegistration = MutableLiveData<BaseResult<String>>()

    fun userRegistration(signUpItem: SignUpItem) {
        viewModelScope.launch {
            try {
                val validationControl = validationControl(signUpItem)
                if (!validationControl.first) {
                    userRegistration.value =
                        BaseResult.Error(IllegalStateException(), validationControl.second)
                } else {
                    val response = getApiServiceManager().userRegistration(
                        signUpItem.userName,
                        signUpItem.email,
                        signUpItem.phoneNumber,
                        signUpItem.password
                    )
                    if (response.success) {
                        userRegistration.value =
                            BaseResult.Success(response.message)
                    } else {
                        userRegistration.value =
                            BaseResult.Error(IllegalStateException(), response.message)
                    }
                }
            } catch (exception: Exception) {
                userRegistration.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }

    private fun validationControl(signUpItem: SignUpItem): Pair<Boolean, String> {
        var isValid = true
        var message = ""
        if (signUpItem.userName.isBlankOrEmpty() ||
            signUpItem.email.isBlankOrEmpty() ||
            signUpItem.phoneNumber.isBlankOrEmpty() ||
            signUpItem.password.isBlankOrEmpty() ||
            signUpItem.confirmPassword.isBlankOrEmpty()
        ) {
            isValid = false
            message = "Empty or blank is not allowed. Please enter valid details."
            return Pair(isValid, message)
        }
        if (!signUpItem.email.isBlankOrEmpty() && !signUpItem.email.isEmailValid()) {
            isValid = false
            message = "Email is not valid."
            return Pair(isValid, message)
        }
        if (!signUpItem.phoneNumber.isBlankOrEmpty() && signUpItem.phoneNumber.length != 10) {
            isValid = false
            message = "Please enter valid phone number."
            return Pair(isValid, message)
        }
        if ((!signUpItem.password.isBlankOrEmpty() && !signUpItem.confirmPassword.isBlankOrEmpty())
            && signUpItem.password != signUpItem.confirmPassword
        ) {
            isValid = false
            message = "Confirm password doesn't match."
            return Pair(isValid, message)
        }
        if (signUpItem.password.length <= 3 ||
            signUpItem.confirmPassword.length <= 3
        ) {
            isValid = false
            message = "Password length must be more than 3 letters."
            return Pair(isValid, message)
        }
        if (!signUpItem.isTermsConditionChecked) {
            isValid = false
            message = "Please select terms and conditions."
            return Pair(isValid, message)
        }
        return Pair(isValid, message)
    }
}