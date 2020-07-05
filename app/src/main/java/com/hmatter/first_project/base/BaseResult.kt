package com.hmatter.first_project.base

sealed class BaseResult<out T> {
    data class Success<out T>(val item: T) : BaseResult<T>()
    data class Error(val errorMessage: String)
}