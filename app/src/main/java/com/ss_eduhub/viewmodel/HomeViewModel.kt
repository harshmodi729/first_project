package com.ss_eduhub.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import com.ss_eduhub.model.PopularClassItem
import com.ss_eduhub.model.PopularClassesItem
import com.ss_eduhub.model.SliderItem
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    val bannersLiveData = MutableLiveData<BaseResult<ArrayList<SliderItem>>>()
    val popularClassesLiveData = MutableLiveData<BaseResult<ArrayList<PopularClassesItem>>>()
    val favoriteClassesLiveData = MutableLiveData<BaseResult<ArrayList<PopularClassesItem>>>()
    val yourClassesLiveData = MutableLiveData<BaseResult<ArrayList<PopularClassItem>>>()

    fun getBanners() {
        viewModelScope.launch {
            try {
                val response = getApiServiceManager().getBanners(Constants.userProfileData.id)
                if (response.success) {
                    bannersLiveData.value = BaseResult.Success(response.data!!)
                } else
                    bannersLiveData.value =
                        BaseResult.Error(IllegalStateException(), response.message)
            } catch (exception: Exception) {
                bannersLiveData.value = BaseResult.Error(exception)
            }
        }
    }

    fun getPopularClasses() {
        viewModelScope.launch {
            try {
                val response =
                    getApiServiceManager().getPopularClasses(Constants.userProfileData.id)
                if (response.success) {
                    response.data?.let {
                        popularClassesLiveData.value = BaseResult.Success(it)
                    } ?: kotlin.run {
                        BaseResult.Error(IllegalStateException())
                    }
                } else
                    popularClassesLiveData.value =
                        BaseResult.Error(IllegalStateException(), response.message)
            } catch (exception: Exception) {
                popularClassesLiveData.value = BaseResult.Error(exception)
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
                            BaseResult.Error(IllegalStateException())
                    }
                } else {
                    favoriteClassesLiveData.value =
                        BaseResult.Error(IllegalStateException(), response.message)
                }
            } catch (exception: Exception) {
                favoriteClassesLiveData.value = BaseResult.Error(exception)
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
                        BaseResult.Error(IllegalStateException())
                    }
                } else
                    yourClassesLiveData.value =
                        BaseResult.Error(IllegalStateException(), response.message)
            } catch (exception: Exception) {
                yourClassesLiveData.value = BaseResult.Error(exception)
            }
        }
    }
}