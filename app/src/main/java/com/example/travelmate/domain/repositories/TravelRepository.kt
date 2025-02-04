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
    suspend fun logout()
    suspend fun setUser(user: LoginUser.User)
    suspend fun getUser(): LoginUser.User
    suspend fun saveCategory(
        beach: Boolean,
        mountain: Boolean,
        cultural: Boolean,
        culinary: Boolean,
    )

    suspend fun getCategory(): Map<String, Boolean>

    // Destination
    suspend fun getDestinations(
        page: Int,
        limit: Int,
        category: String? = null,
        token: String,
        search: String? = null,
    ): Flow<ResultResponse<DestinationUser>>

    // Itinerary
    suspend fun saveItinerary(itinerary: Itinerary)

    suspend fun getListItinerary(): List<Itinerary>

    suspend fun getItinerary(): Itinerary

    suspend fun updateItinerary(itinerary: Itinerary)

    suspend fun deleteItinerary(itinerary: Itinerary)
}