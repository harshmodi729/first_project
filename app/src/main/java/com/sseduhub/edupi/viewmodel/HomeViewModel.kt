package com.sseduhub.edupi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sseduhub.edupi.base.BaseResult
import com.sseduhub.edupi.base.BaseViewModel
import com.sseduhub.edupi.common.Constants
import com.sseduhub.edupi.model.PopularClassItem
import com.sseduhub.edupi.model.PopularClassesItem
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    val popularClassesLiveData = MutableLiveData<BaseResult<ArrayList<PopularClassesItem>>>()
    val favoriteClassesLiveData = MutableLiveData<BaseResult<ArrayList<PopularClassesItem>>>()
    val yourClassesLiveData = MutableLiveData<BaseResult<ArrayList<PopularClassItem>>>()

    fun getPopularClasses() {
        viewModelScope.launch {
            try {
                val response =
                    getApiServiceManager().getPopularClasses(Constants.userProfileData.id)
                if (response.success) {
                    response.data?.let {
                        popularClassesLiveData.value = BaseResult.Success(it)
                    } ?: kotlin.run {
                        BaseResult.Error(
                            IllegalStateException(),
                            "Oops something went wrong."
                        )
                    }
                } else
                    popularClassesLiveData.value =
                        BaseResult.Error(
                            IllegalStateException(),
                            response.message
                        )
            } catch (exception: Exception) {
                popularClassesLiveData.value =
                    BaseResult.Error(exception, "Oops something went wrong.")
            }
        }
    }

    fun getFavoriteClasses() {
        viewModelScope.launch {
            try {
                val response =
                    getApiServiceManager().getFavoriteClasses(Constants.userProfileData.id)
                if (response.success) {
                    response.data?.let {
                        favoriteClassesLiveData.value = BaseResult.Success(it)
                    } ?: kotlin.run {
                        favoriteClassesLiveData.value =
                            BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
                    }
                } else {
                    favoriteClassesLiveData.value =
                        BaseResult.Error(IllegalStateException(), response.message)
                }
            } catch (exception: Exception) {
                favoriteClassesLiveData.value =
                    BaseResult.Error(IllegalStateException(), "Oops something went wrong.")
            }
        }
    }

    fun getYourClasses() {
        viewModelScope.launch {
            try {
                val response = getDummyApiServiceManager().getYourClasses()
                if (response.success) {
                    response.data?.let {
                        yourClassesLiveData.value = BaseResult.Success(it)
                    } ?: kotlin.run {
                        BaseResult.Error(
                            IllegalStateException(),
                            "Oops something went wrong."
                        )
                    }
                } else
                    yourClassesLiveData.value =
                        BaseResult.Error(
                            IllegalStateException(),
                            response.message
                        )
            } catch (exception: Exception) {
                yourClassesLiveData.value =
                    BaseResult.Error(exception, "Oops something went wrong.")
            }
        }
    }
}