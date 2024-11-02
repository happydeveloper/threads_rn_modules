package com.threadsproject

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


//https://graph.threads.net/oauth/access_token
interface ThreadsInterface {

    @Multipart
    @POST("oauth/access_token")
    suspend fun getAccessToken(
        @Part("client_id") clientId: RequestBody,
        @Part("client_secret") clientSecret: RequestBody,
        @Part("grant_type") grantType: RequestBody,
        @Part("redirect_uri") redirectUri: RequestBody,
        @Part("code") code: RequestBody
    ): Map<String, Any>?


    @FormUrlEncoded
    @POST("user/edit")
    suspend fun getLongTermToken(
        @Field("first_name") first: String?,
        @Field("last_name") last: String?
    ): Map<String, Any>?

    @GET("access_token")
    suspend fun getLongTermToken(@Query("grant_type") grantType: String = "th_exchange_token",
                         @Query("client_secret") clientSecret: String = "69b605f9d43c13e773f39deb4f358f7d",
                         @Query("access_token") accessToken: String
    ): Map<String, Any>?

    @POST("v1.0/{user_id}/threads")
    suspend fun uploadThreadPost(@Path("user_id") userId: String,
                         @Query("media_type") mediaType: String = "TEXT",
                         @Query("text") text: String,
                         @Query("access_token") accessToken: String
    ): Map<String, Any>?

    @POST("v1.0/{user_id}/threads_publish")
    suspend fun makeThreadPublic(@Path("user_id") userId: String,
                         @Query("creation_id") mediaId: String,
                         @Query("access_token") accessToken: String
    ): Map<String, Any>?

//    @Multipart
//    @POST("oauth/access_token")
//    fun getAccessToken(
//        @Part("client_id") clientId: RequestBody,
//        @Part("client_secret") clientSecret: RequestBody,
//        @Part("grant_type") grantType: RequestBody,
//        @Part("redirect_uri") redirectUri: RequestBody,
//        @Part("code") code: RequestBody
//    ): Call<Map<String, Any>?>
//
//
//    @FormUrlEncoded
//    @POST("user/edit")
//    fun getLongTermToken(
//        @Field("first_name") first: String?,
//        @Field("last_name") last: String?
//    ): Call<Map<String, Any>?>
//
//    @GET("access_token")
//    fun getLongTermToken(@Query("grant_type") grantType: String = "th_exchange_token",
//                                 @Query("client_secret") clientSecret: String = "69b605f9d43c13e773f39deb4f358f7d",
//                                 @Query("access_token") accessToken: String
//    ): Call<Map<String, Any>?>
//
//    @POST("v1.0/{user_id}/threads")
//    fun uploadThreadPost(@Path("user_id") userId: String,
//                                 @Query("media_type") mediaType: String = "TEXT",
//                                 @Query("text") text: String,
//                                 @Query("access_token") accessToken: String
//    ): Call<Map<String, Any>?>
//
//    @POST("v1.0/{user_id}/threads_publish")
//    fun makeThreadPublic(@Path("user_id") userId: String,
//                                 @Query("creation_id") mediaId: String,
//                                 @Query("access_token") accessToken: String
//    ): Call<Map<String, Any>?>


//    let urlString = "https://graph.threads.net/v1.0/\(userId)/threads_publish?creation_id=\(mediaId)&access_token=\(accessToken)"


}

