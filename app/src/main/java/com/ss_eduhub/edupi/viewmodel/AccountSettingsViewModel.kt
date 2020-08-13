package com.ss_eduhub.edupi.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ss_eduhub.edupi.base.BaseResult
import com.ss_eduhub.edupi.base.BaseViewModel
import com.ss_eduhub.edupi.common.PreferenceConstants
import com.ss_eduhub.edupi.extension.*
import com.ss_eduhub.edupi.model.SignInItem
import kotlinx.coroutines.launch
import okhttp3.MultipartBody


class AccountSettingsViewModel : BaseViewModel() {

    val signOutLiveData = MutableLiveData<BaseResult<Boolean>>()
    val uploadImageLiveData = MutableLiveData<BaseResult<String>>()
    val changePhoneNumberLiveData = MutableLiveData<BaseResult<String>>()

    fun changePhoneNumber(context: Context, signInItem: SignInItem) {
        viewModelScope.launch {
            try {
                val validation = validationControl(signInItem)
                if (!validation.first) {
                    changePhoneNumberLiveData.value =
                        BaseResult.Error(IllegalStateException(), validation.second)
                } else {
                    val userId =
                        getPreferenceManager(context).getPreferenceInt(PreferenceConstants.USER_ID)
                    val response = getApiServiceManager().changePhoneNumber(
                        userId,
                        signInItem.mobile.toInt(),
                        signInItem.password
                    )
                    if (response.success) {
                        changePhoneNumberLiveData.value =
                            BaseResult.Success(response.message)
                    } else {
                        changePhoneNumberLiveData.value =
                            BaseResult.Error(IllegalStateException(), response.message)
                    }
                }
            } catch (exception: Exception) {
                changePhoneNumberLiveData.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }

    fun uploadImage(context: Context, fileBody: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                val preference = getPreferenceManager(context)
                val userId = preference.getPreferenceInt(PreferenceConstants.USER_ID)

                val response = getApiServiceManager().changePic(userId, fileBody)
                if (response.success) {
                    response.data?.let {
                        preference.setPreferenceString(
                            PreferenceConstants.USER_IMAGE,
                            it.asString
                        )
                        uploadImageLiveData.value = BaseResult.Success(it.asString)
                    } ?: kotlin.run {
                        uploadImageLiveData.value =
                            BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
                    }
                } else {
                    uploadImageLiveData.value =
                        BaseResult.Error(IllegalStateException(), response.message)
                }
            } catch (exception: Exception) {
                uploadImageLiveData.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }

    fun userSignOut(context: Context) {
        viewModelScope.launch {
            try {
                val preference = getPreferenceManager(context)
                preference.erase()
                preference.setPreferenceBoolean(PreferenceConstants.IS_ALREADY_VISIT_INTRO, true)
                preference.setPreferenceBoolean(PreferenceConstants.IS_USER_LOGIN, false)
                signOutLiveData.value = BaseResult.Success(true)
            } catch (exception: Exception) {
                signOutLiveData.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }

    private fun validationControl(signInItem: SignInItem): Pair<Boolean, String> {
        var isValid = true
        var message = ""
        if (signInItem.mobile.isBlankOrEmpty() ||
            signInItem.password.isBlankOrEmpty()
        ) {
            isValid = false
            message = "Empty or blank is not allowed. Please enter valid details."
            return Pair(isValid, message)
        }
        if (!signInItem.mobile.isBlankOrEmpty() && signInItem.mobile.length != 10) {
            isValid = false
            message = "Please enter valid phone number."
            return Pair(isValid, message)
        }
        return Pair(isValid, message)
    }
}