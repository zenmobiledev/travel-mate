package com.example.travelmate.data.source.remote.api

import com.example.travelmate.data.source.remote.model.login.request.LoginUserRequest
import com.example.travelmate.data.source.remote.model.login.response.LoginUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TravelService {
    @POST("travel/auth/login")
    suspend fun loginUser(
        @Body loginUser: LoginUserRequest,
    ): Response<LoginUserResponse>
}