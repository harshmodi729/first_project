package com.hmatter.first_project.remote

import com.hmatter.first_project.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
    private val apiServices: ApiServices? = null
    private val logging = HttpLoggingInterceptor()
    private val httpClient = OkHttpClient.Builder()

    fun getApiServices(): ApiServices {
        if (apiServices == null) {
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logging)
            }
            val retrofit = Retrofit.Builder()
                //TODO: Change this base url when services are ready
                .baseUrl("https://run.mocky.io/v3/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }
        return apiServices!!
    }
}