package com.hmatter.first_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.model.FavoriteCLassesItem
import com.hmatter.first_project.model.PopularClassItem
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    val popularClassesLiveData = MutableLiveData<BaseResult<ArrayList<PopularClassItem>>>()
    val favoriteClassesLiveData = MutableLiveData<BaseResult<ArrayList<FavoriteCLassesItem>>>()
    val yourClassesLiveData = MutableLiveData<BaseResult<ArrayList<PopularClassItem>>>()

    fun getPopularClasses() {
        viewModelScope.launch {
            try {
                val response = getDummyApiServiceManager().getPopularClasses()
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
                val response = getDummyApiServiceManager().getFavoriteClasses()
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
                val response = getDummyApiServiceManager().getPopularClasses()
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