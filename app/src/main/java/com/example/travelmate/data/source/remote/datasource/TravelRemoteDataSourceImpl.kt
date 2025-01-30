package com.example.travelmate.data.source.remote.datasource

import com.example.travelmate.data.source.remote.api.TravelService
import com.example.travelmate.data.source.remote.model.destination.DestinationResponse
import com.example.travelmate.data.source.remote.model.login.request.LoginUserRequest
import com.example.travelmate.data.source.remote.model.login.response.LoginUserResponse
import retrofit2.Response
import javax.inject.Inject

class TravelRemoteDataSourceImpl @Inject constructor(private val travelService: TravelService) :
    TravelRemoteDataSource {
    override suspend fun loginUser(loginUserRequest: LoginUserRequest): Response<LoginUserResponse> {
        try {
            val response = travelService.loginUser(loginUserRequest)
            return when {
                response.isSuccessful -> response
                response.code() == 400 -> throw Exception("Invalid Email or Password")
                else -> throw Exception(response.code().toString())
            }
        } catch (e: Exception) {
            throw Exception(e.message.toString())
        }
    }

    override suspend fun getAllDestination(
        page: Int,
        token: String,
    ): Response<DestinationResponse> {
        try {
            val response = travelService.getDestinations(
                page = page,
                token = token
            )
            return when {
                response.isSuccessful -> response
                else -> throw Exception(response.code().toString())
            }
        } catch (e: Exception) {
            throw Exception(e.message.toString())
        }
    }
}