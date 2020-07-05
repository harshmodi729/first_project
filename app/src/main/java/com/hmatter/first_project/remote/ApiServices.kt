package com.hmatter.first_project.remote

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiServices {

    @GET("9756db14-2aa0-44dd-8bc9-30629ea66ddd")
    fun getPopularClasses(): Observable<ApiResponse<JsonObject>>
}