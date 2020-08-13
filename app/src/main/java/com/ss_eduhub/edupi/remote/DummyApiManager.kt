package com.ss_eduhub.edupi.remote

import com.google.gson.GsonBuilder
import com.ss_eduhub.edupi.BuildConfig
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit

class DummyApiManager {
    companion object {
        private var apiServices: ApiServices? = null

        fun getApiServices(): ApiServices {
            if (apiServices == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://run.mocky.io/v3/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                        GsonConverterFactory.create(
                            GsonBuilder().setLenient().create()
                        )
                    )
                    .client(getHttpClient())
                    .build()
                apiServices = retrofit.create(ApiServices::class.java)
            }
            return apiServices!!
        }

        private fun getHttpClient(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(2, TimeUnit.MINUTES)
            httpClient.connectTimeout(60, TimeUnit.SECONDS)
            httpClient.cookieJar(JavaNetCookieJar(CookieManager()))

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logging)
            }

            return httpClient.build()
        }
    }
}