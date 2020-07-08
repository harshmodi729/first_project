package com.hmatter.first_project.remote

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiServices {

    @GET()
    fun getPopularClasses(@Url url: String = "https://run.mocky.io/v3/9756db14-2aa0-44dd-8bc9-30629ea66ddd")
            : ApiResponse<JsonObject>
}