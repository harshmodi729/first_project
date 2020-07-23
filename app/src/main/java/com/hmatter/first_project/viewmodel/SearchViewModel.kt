package com.hmatter.first_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.model.VideoCategoryItem
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {

    val alVideoCategoryItem = MutableLiveData<BaseResult<ArrayList<VideoCategoryItem>>>()

    fun getVideoCategories() {
        viewModelScope.launch {
            try {
                val response = getApiServiceManager().getVideoCategory()
                if (response.isSuccess()) {
                    response.data?.let {
                        alVideoCategoryItem.value = BaseResult.Success(it)
                    } ?: kotlin.run {
                        alVideoCategoryItem.value = BaseResult.Error(
                            IllegalStateException(),
                            "Something wrong, please try again later"
                        )
                    }
                } else
                    alVideoCategoryItem.value = BaseResult.Error(
                        IllegalStateException(),
                        "Something wrong, please try again later"
                    )
            } catch (e: Exception) {
                e.printStackTrace()
                alVideoCategoryItem.value = BaseResult.Error(e, e.localizedMessage!!)
            }
        }
    }
}