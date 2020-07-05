package com.hmatter.first_project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.base.BaseObserver
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.model.PopularClassItem
import com.hmatter.first_project.remote.ApiManager
import com.hmatter.first_project.remote.ApiResponse
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val alPopularClasses = MutableLiveData<Any>()

    fun getPopularClasses() {
        viewModelScope.launch {
            try {
                ApiManager().getApiServices().getPopularClasses().subscribeOn(Schedulers.io())
                    .subscribe(object : BaseObserver<ApiResponse<ArrayList<PopularClassItem>>>() {
                        override fun handleResponse(response: ApiResponse<ArrayList<PopularClassItem>>) {
                            response.data.let {
                                if (response.isSuccess())
                                    alPopularClasses.value = BaseResult.Success(it)
                                else
                                    alPopularClasses.value =
                                        BaseResult.Error("Something wrong, please try again later")
                            }
                        }

                        override fun handleError(errorMessage: String) {
                            alPopularClasses.value = BaseResult.Error(errorMessage)
                        }
                    })
            } catch (e: Exception) {
                alPopularClasses.value = BaseResult.Error(e.message!!)
            }
        }
    }
}