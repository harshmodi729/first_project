package com.ss_eduhub.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import kotlinx.coroutines.launch

class VideoViewModel : BaseViewModel() {

    val addWishListLiveData = MutableLiveData<BaseResult<String>>()

    fun addToWishList(classId: Int, isAdd: Int) {
        viewModelScope.launch {
            try {
                val response = getApiServiceManager().addToWishList(
                    Constants.userProfileData.id,
                    classId,
                    isAdd
                )
                if (response.success) {
                    addWishListLiveData.value = BaseResult.Success(response.message)
                } else {
                    addWishListLiveData.value =
                        BaseResult.Error(IllegalStateException(), response.message)
                }
            } catch (exception: Exception) {
                addWishListLiveData.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }
}