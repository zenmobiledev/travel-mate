package com.example.travelmate.domain.repositories

import com.example.travelmate.data.source.remote.model.login.request.LoginUserRequest
import com.example.travelmate.domain.model.destination.DestinationUser
import com.example.travelmate.domain.model.destination.Itinerary
import com.example.travelmate.domain.model.login.LoginUser
import com.example.travelmate.utils.ResultResponse
import kotlinx.coroutines.flow.Flow

interface TravelRepository {
    // Login
    suspend fun loginUser(loginUserRequest: LoginUserRequest): Flow<ResultResponse<LoginUser>>
    suspend fun getToken(): String?
    suspend fun saveToken(token: String)

    // Destination
    suspend fun getAllDestination(
        page: Int,
        limit: Int,
        token: String,
    ): Flow<ResultResponse<List<DestinationUser.Destination>>>

    // Itinerary
    suspend fun saveItinerary(itinerary: Itinerary)

    suspend fun getItinerary(): List<Itinerary>

    suspend fun updateItinerary(itinerary: Itinerary)

    suspend fun deleteItinerary(itinerary: Itinerary)
}