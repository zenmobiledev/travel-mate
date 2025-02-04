package com.example.travelmate.data.source.local.datasource

import com.example.travelmate.data.source.local.entity.DestinationEntity
import com.example.travelmate.data.source.local.entity.ItineraryEntity
import com.example.travelmate.data.source.local.entity.LoginUserEntity

interface TravelLocalDataSource {
    // TOKEN
    suspend fun getToken(): String?
    suspend fun saveToken(token: String)

    // USER
    suspend fun saveUser(user: LoginUserEntity.User)
    suspend fun getUser(): LoginUserEntity.User
    suspend fun logoutUser()
    suspend fun saveCategory(
        beach: Boolean,
        mountain: Boolean,
        cultural: Boolean,
        culinary: Boolean,
    )

    suspend fun getCategory(): Map<String, Boolean>

    // DESTINATION
    suspend fun saveDataDestination(itinerary: List<DestinationEntity>)
    suspend fun getDataDestination(): List<DestinationEntity>

    // ITINERARY
    suspend fun saveItinerary(itinerary: ItineraryEntity)
    suspend fun getListItinerary(): List<ItineraryEntity>
    suspend fun getItinerary(): ItineraryEntity
    suspend fun updateItinerary(itinerary: ItineraryEntity)
    suspend fun deleteItinerary(itinerary: ItineraryEntity)
}