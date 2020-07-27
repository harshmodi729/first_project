package com.hmatter.first_project.remote

import com.google.gson.JsonObject
import com.hmatter.first_project.common.RequestConstants
import com.hmatter.first_project.model.PopularClassItem
import com.hmatter.first_project.model.VideoCategoryItem
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {

    @FormUrlEncoded
    @POST(RequestConstants.REGISTER_USER)
    suspend fun userRegistration(
        @Field("name") userName: String,
        @Field("email") email: String,
        @Field("mobile") phoneNumber: String,
        @Field("password") password: String
    ): ApiResponse<JsonObject>

    @GET("9756db14-2aa0-44dd-8bc9-30629ea66ddd")
    suspend fun getPopularClasses()
            : ApiResponse<ArrayList<PopularClassItem>>

    @GET("8aa3d6ee-bb06-46ea-8614-1f49846ce2de")
    suspend fun getVideoCategory(): ApiResponse<ArrayList<VideoCategoryItem>>
}