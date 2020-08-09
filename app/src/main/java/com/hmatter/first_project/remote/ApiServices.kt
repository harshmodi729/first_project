package com.hmatter.first_project.remote

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.hmatter.first_project.common.RequestConstants
import com.hmatter.first_project.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

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

    @Multipart
    @POST(RequestConstants.CHANGE_PICTURE)
    suspend fun changePic(
        @Part("user_id") userId: Int,
        @Part file: MultipartBody.Part
    ): ApiResponse<JsonElement>

    @FormUrlEncoded
    @POST(RequestConstants.CHANGE_MOBILE)
    suspend fun changePhoneNumber(
        @Field("id") userId: Int,
        @Field("mobile") mobile: Int,
        @Field("password") password: String
    ): ApiResponse<JsonObject>

    @FormUrlEncoded
    @POST(RequestConstants.GET_POPULAR_CLASS_DATA)
    suspend fun getPopularClasses(@Field("user_id") userId: Int): ApiResponse<ArrayList<PopularClassesItem>>

    @FormUrlEncoded
    @POST(RequestConstants.ADD_WISH_LIST)
    suspend fun addToWishList(
        @Field("user_id") userId: Int,
        @Field("class_id") classId: Int,
        @Field("status") isAdd: Int
    ): ApiResponse<JsonElement>

    @FormUrlEncoded
    @POST(RequestConstants.GET_WISH_LIST)
    suspend fun getFavoriteClasses(@Field("user_id") userId: Int): ApiResponse<ArrayList<PopularClassesItem>>

    @GET("e12782f8-224c-48f5-b63e-0ed640272462")
    suspend fun getYourClasses()
            : ApiResponse<ArrayList<PopularClassItem>>

    @GET("8aa3d6ee-bb06-46ea-8614-1f49846ce2de")
    suspend fun getVideoCategory(): DummyApiResponse<ArrayList<VideoCategoryItem>>

    @GET(RequestConstants.GET_POPULAR_TAG)
    suspend fun getPopularTagList(): ApiResponse<ArrayList<PopularTagCategoryItem>>

}