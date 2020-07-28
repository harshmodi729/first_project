package com.hmatter.first_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.base.BaseViewModel
import com.hmatter.first_project.model.PopularClassItem
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    val alPopularClasses = MutableLiveData<BaseResult<ArrayList<PopularClassItem>>>()

    fun getPopularClasses() {
        viewModelScope.launch {
            try {
                val response = getDummyApiServiceManager().getPopularClasses()
                if (response.isSuccess()) {
                    response.data?.let {
                        if (response.isSuccess())
                            alPopularClasses.value = BaseResult.Success(it)
                        else
                            alPopularClasses.value =
                                BaseResult.Error(
                                    IllegalStateException(),
                                    "Something wrong, please try again later"
                                )
                    } ?: kotlin.run {
                        BaseResult.Error(
                            IllegalStateException(),
                            "Something wrong, please try again later"
                        )
                    }
                } else
                    alPopularClasses.value =
                        BaseResult.Error(
                            IllegalStateException(),
                            "Something wrong, please try again later"
                        )
            } catch (exception: Exception) {
                alPopularClasses.value =
                    BaseResult.Error(exception, "Something wrong, please try again later")
            }
        }
    }
}