package com.example.travelmate.domain.repositories

import com.example.travelmate.data.source.remote.model.login.request.LoginUserRequest
import com.example.travelmate.domain.model.LoginUser
import com.example.travelmate.utils.ResultResponse
import kotlinx.coroutines.flow.Flow

interface TravelRepository {
    suspend fun loginUser(loginUserRequest: LoginUserRequest): Flow<ResultResponse<LoginUser>>
}