package com.hmatter.first_project.base

import androidx.lifecycle.ViewModel
import com.hmatter.first_project.remote.ApiManager
import com.hmatter.first_project.remote.ApiServices

abstract class BaseViewModel : ViewModel() {

    fun getApiServiceManager(): ApiServices {
        return ApiManager.getApiServices()
    }
}