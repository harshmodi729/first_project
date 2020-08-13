package com.sseduhub.edupi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sseduhub.edupi.base.BaseResult
import com.sseduhub.edupi.base.BaseViewModel
import com.sseduhub.edupi.common.Constants
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