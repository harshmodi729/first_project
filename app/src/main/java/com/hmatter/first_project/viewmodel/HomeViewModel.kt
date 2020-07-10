package com.hmatter.first_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.remote.ApiManager
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val alPopularClasses = MutableLiveData<BaseResult<Any>>()

    fun getPopularClasses() {
        viewModelScope.launch {
            try {
                val response =
                    ApiManager.getApiServices().getPopularClasses()
                if (response.isSuccess()) {
                    response.data?.let {
                        if (response.isSuccess())
                            alPopularClasses.value = BaseResult.Success(it)
                        else
                            alPopularClasses.value =
                                BaseResult.Error(IllegalStateException("Something wrong, please try again later"))
                    } ?: kotlin.run {
                        BaseResult.Error(IllegalStateException("Something wrong, please try again later"))
                    }
                }
            } catch (exception: Exception) {
                alPopularClasses.value = BaseResult.Error(exception)
            }
        }
    }
}