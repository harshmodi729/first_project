package com.hmatter.first_project.remote

import com.hmatter.first_project.model.PopularClassItem
import com.hmatter.first_project.model.VideoCategoryItem
import retrofit2.http.GET

interface ApiServices {

    @GET("9756db14-2aa0-44dd-8bc9-30629ea66ddd")
    suspend fun getPopularClasses()
            : ApiResponse<ArrayList<PopularClassItem>>

    @GET("8aa3d6ee-bb06-46ea-8614-1f49846ce2de")
    suspend fun getVideoCategory(): ApiResponse<ArrayList<VideoCategoryItem>>
}