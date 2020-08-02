package com.hmatter.first_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.model.FavoriteCLassesItem
import com.hmatter.first_project.model.PopularClassItem
import com.hmatter.first_project.model.PopularClassesItem
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    val popularClassesLiveData = MutableLiveData<BaseResult<ArrayList<PopularClassItem>>>()
    val favoriteClassesLiveData = MutableLiveData<BaseResult<ArrayList<FavoriteCLassesItem>>>()
    val yourClassesLiveData = MutableLiveData<BaseResult<ArrayList<PopularClassesItem>>>()

    fun getPopularClasses() {
        viewModelScope.launch {
            try {
                val response = getApiServiceManager().getPopularClasses()
                if (response.success) {
                    response.data?.let {
                        if (response.isSuccess())
                            popularClassesLiveData.value = BaseResult.Success(it)
                        else
                            popularClassesLiveData.value =
                                BaseResult.Error(
                                    IllegalStateException(),
                                    "Something wrong, please try again later"
                                )
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
                yourClassesLiveData.value =
                    BaseResult.Error(exception, "Oops something went wrong.")
            }
        }
    }
}