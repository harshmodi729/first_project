package com.hmatter.first_project.remote

import com.google.gson.JsonObject
import com.hmatter.first_project.common.RequestConstants
import com.hmatter.first_project.model.FavoriteCLassesItem
import com.hmatter.first_project.model.PopularClassItem
import com.hmatter.first_project.model.SignInItem
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
        @Field("password") password: String,
        @Field("token") token: String? = "",
        @Field("social_id") socialId: String? = "",
        @Field("social_flag") socialFlag: String? = "",
        @Field("last_seen") lastSeen: String? = ""
    ): ApiResponse<SignInItem>

    @FormUrlEncoded
    @POST(RequestConstants.LOGIN_USER)
    suspend fun userLogin(
        @Field("mobile") phoneNumber: String,
        @Field("password") password: String,
        @Field("last_seen") lastSeen: String? = "",
        @Field("token") token: String? = ""
    ): ApiResponse<SignInItem>

    @FormUrlEncoded
    @POST(RequestConstants.FORGET_PASSWORD)
    suspend fun forgotPassword(@Field("mobile") mobile: String): ApiResponse<JsonObject>

    @FormUrlEncoded
    @POST(RequestConstants.CHANGE_PASSWORD)
    suspend fun changePassword(
        @Field("user_id") userId: Int,
        @Field("old_password") oldPassword: String,
        @Field("new_password") newPassword: String
    ): ApiResponse<JsonObject>

    @GET("e12782f8-224c-48f5-b63e-0ed640272462")
    suspend fun getPopularClasses()
            : ApiResponse<ArrayList<PopularClassItem>>

    @GET("076880d6-a58b-4cdd-8325-fdad56844ba9")
    suspend fun getVideoCategory(): ApiResponse<ArrayList<VideoCategoryItem>>

    @GET("56931fb1-cdd1-48fc-a7c8-b53ab7413f30")
    suspend fun getFavoriteClasses(): ApiResponse<ArrayList<FavoriteCLassesItem>>
}