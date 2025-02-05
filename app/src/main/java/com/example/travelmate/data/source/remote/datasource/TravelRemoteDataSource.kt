package com.example.travelmate.data.source.remote.datasource

import com.example.travelmate.data.source.remote.model.destination.DestinationResponse
import com.example.travelmate.data.source.remote.model.login.request.LoginUserRequest
import com.example.travelmate.data.source.remote.model.login.response.LoginUserResponse
import retrofit2.Response

interface TravelRemoteDataSource {
    suspend fun loginUser(loginUserRequest: LoginUserRequest): Response<LoginUserResponse>

    suspend fun getDestinations(
        page: Int,
        limit: Int,
        category: String,
        token: String,
        search: String? = null
    ): Response<DestinationResponse>
}