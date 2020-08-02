package com.hmatter.first_project.remote

import com.google.gson.JsonObject
import com.hmatter.first_project.common.RequestConstants
import com.hmatter.first_project.model.PopularClassesItem
import com.hmatter.first_project.model.PopularTagCategoryItem
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

    @GET(RequestConstants.GET_POPULAR_CLASS_DATA)
    suspend fun getPopularClasses(): ApiResponse<ArrayList<PopularClassesItem>>

    @GET("8aa3d6ee-bb06-46ea-8614-1f49846ce2de")
    suspend fun getVideoCategory(): DummyApiResponse<ArrayList<VideoCategoryItem>>

    @GET(RequestConstants.GET_POPULAR_TAG)
    suspend fun getPopularTagList(): ApiResponse<ArrayList<PopularTagCategoryItem>>

}