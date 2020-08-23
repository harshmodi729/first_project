package com.ss_eduhub.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import com.ss_eduhub.model.PopularClassesItem
import com.ss_eduhub.model.PopularTagCategoryItem
import com.ss_eduhub.model.VideoCategoryItem
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {

    val videoCategoryLiveData = MutableLiveData<BaseResult<ArrayList<VideoCategoryItem>>>()
    val alPopularCategoryItem = MutableLiveData<BaseResult<ArrayList<PopularTagCategoryItem>>>()
    val alCategoryWiseFilterItem = MutableLiveData<BaseResult<ArrayList<PopularClassesItem>>>()

    fun getVideoCategories() {
        viewModelScope.launch {
            try {
                val response = getDummyApiServiceManager().getVideoCategory()
                if (response.isSuccess()) {
                    response.data?.let {
                        videoCategoryLiveData.value = BaseResult.Success(it)
                    } ?: kotlin.run {
                        videoCategoryLiveData.value =
                            BaseResult.Error(IllegalStateException())
                    }
                } else
                    videoCategoryLiveData.value =
                        BaseResult.Error(IllegalStateException(), response.message)
            } catch (exception: Exception) {
                exception.printStackTrace()
                videoCategoryLiveData.value =
                    BaseResult.Error(exception)
            }
        }
    }

    fun getPopularCategoryList() {
        viewModelScope.launch {
            try {
                val response = getApiServiceManager().getPopularTagList()
                if (response.success) {
                    response.data?.let {
                        alPopularCategoryItem.value = BaseResult.Success(it)
                    } ?: kotlin.run {
                        alPopularCategoryItem.value =
                            BaseResult.Error(IllegalStateException("Oops something went wrong."))
                    }
                } else {
                    alPopularCategoryItem.value =
                        BaseResult.Error(IllegalStateException(), response.message)
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                alPopularCategoryItem.value = BaseResult.Error(exception)
            }
        }
    }

    fun getCatFilterData(categoryId: Int) {
        viewModelScope.launch {
            try {
                val response =
                    getApiServiceManager().getCategoryWiseFilterData(
                        Constants.userProfileData.id,
                        categoryId
                    )
                if (response.success) {
                    response.data?.let {
                        alCategoryWiseFilterItem.value = BaseResult.Success(it)
                    } ?: kotlin.run {
                        alCategoryWiseFilterItem.value = BaseResult.Error(IllegalStateException())
                    }
                } else
                    alCategoryWiseFilterItem.value =
                        BaseResult.Error(IllegalStateException(), response.message)
            } catch (exception: Exception) {
                alCategoryWiseFilterItem.value = BaseResult.Error(exception)
            }
        }
    }
}