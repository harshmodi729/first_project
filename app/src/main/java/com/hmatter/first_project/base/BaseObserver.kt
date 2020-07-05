package com.hmatter.first_project.base

import android.net.ParseException
import com.google.gson.JsonParseException
import com.hmatter.first_project.remote.ApiResponse
import io.reactivex.observers.DisposableObserver
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.UnknownHostException

abstract class BaseObserver<T> : DisposableObserver<T>() {
    override fun onComplete() {

    }

    override fun onNext(response: T) {
        if (response is ApiResponse<*>) {
            if (response.data == null) {
                handleError("No data found.")
            } else
                handleResponse(response)
        }
    }

    override fun onError(e: Throwable?) {
        if (e is IOException) {
            when (e) {
                is UnknownHostException -> handleError("Network exception, please try again later")
                is InterruptedIOException -> handleError("Network exception, please try again later")
                else -> handleError("Something wrong, please try again later")
            }
        } else if (e is HttpException)
            handleError("Network exception, please try again later")
        else if (e is JSONException || e is JsonParseException || e is ParseException) {
            handleError("Something wrong with response")
        } else
            handleError("Something wrong, please try again later")
    }

    abstract fun handleResponse(response: T)

    abstract fun handleError(errorMessage: String)
}