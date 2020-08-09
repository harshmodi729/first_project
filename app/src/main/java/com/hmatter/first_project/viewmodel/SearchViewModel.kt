package com.hmatter.first_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.common.Constants
import com.hmatter.first_project.model.PopularClassesItem
import com.hmatter.first_project.model.PopularTagCategoryItem
import com.hmatter.first_project.model.VideoCategoryItem
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
                        videoCategoryLiveData.value = BaseResult.Error(
                            IllegalStateException(),
                            response.message
                        )
                    }
                } else
                    videoCategoryLiveData.value = BaseResult.Error(
                        IllegalStateException(),
                        "Oops something went wrong."
                    )
            } catch (e: Exception) {
                e.printStackTrace()
                videoCategoryLiveData.value = BaseResult.Error(e, e.localizedMessage!!)
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
                        alPopularCategoryItem.value = BaseResult.Error(
                            IllegalStateException(),
                            "Something wrong, please try again later"
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                alPopularCategoryItem.value = BaseResult.Error(e, e.localizedMessage!!)
            }
        }
    }

    fun getCatFilterData(categoryId: Int) {
        viewModelScope.launch {
            try {
                val response =
                    getApiServiceManager().getCategoryWiseFilterData(categoryId)
                if (response.success) {
                    response.data?.let {
                        if (response.success)
                            alCategoryWiseFilterItem.value = BaseResult.Success(it)
                        else
                            alCategoryWiseFilterItem.value =
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
                    alCategoryWiseFilterItem.value =
                        BaseResult.Error(
                            IllegalStateException(),
                            response.message
                        )
            } catch (exception: Exception) {
                alCategoryWiseFilterItem.value =
                    BaseResult.Error(exception, "Oops something went wrong.")
            }
        }
    }
}