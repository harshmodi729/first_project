package com.hmatter.first_project.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.extension.isBlankOrEmpty
import kotlinx.android.synthetic.main.lay_dialog_change_password.view.*
import kotlinx.coroutines.launch

class AccountSettingsViewModel : BaseViewModel() {

    val changePasswordDialogButtonClick = MutableLiveData<BaseResult<Any>>()

    fun validationControl(dialogView: View) {
        viewModelScope.launch {
            if (dialogView.edOldPassword.isBlankOrEmpty() ||
                dialogView.edOldPassword.isBlankOrEmpty() ||
                dialogView.edCnfPassword.isBlankOrEmpty()
            ) {
                changePasswordDialogButtonClick.value =
                    BaseResult.Error(
                        IllegalStateException(),
                        "Empty or blank is not allowed. Please enter valid password."
                    )
            } else {
                if (dialogView.edNewPassword.text.toString() == dialogView.edCnfPassword.text.toString()) {
                    changePasswordDialogButtonClick.value =
                        BaseResult.Success(
                            "Password change successfully."
                        )
                } else {
                    changePasswordDialogButtonClick.value =
                        BaseResult.Error(
                            IllegalStateException(),
                            "Confirm password doesn't match."
                        )
                }
            }
        }
    }
}