package com.example.travelmate.data.source.remote.api

import com.example.travelmate.BuildConfig
import com.example.travelmate.data.source.remote.model.destination.DestinationResponse
import com.example.travelmate.data.source.remote.model.login.request.LoginUserRequest
import com.example.travelmate.data.source.remote.model.login.response.LoginUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TravelService {
    @POST("travel/auth/login")
    suspend fun loginUser(
        @Body loginUser: LoginUserRequest,
    ): Response<LoginUserResponse>

    @GET("travel/destinations")
    suspend fun getDestinations(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("categories") category: String,
        @Query("search") search: String? = null,
        @Header("x-secret-app") secretApp: String = BuildConfig.SECRET_APP,
        @Header("x-user-id") userId: String = BuildConfig.USER_ID,
        @Header("x-token") token: String,
    ): Response<DestinationResponse>
}