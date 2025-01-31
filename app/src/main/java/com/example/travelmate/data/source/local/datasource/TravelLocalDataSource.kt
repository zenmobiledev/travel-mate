package com.example.travelmate.data.source.local.datasource

import com.example.travelmate.data.source.local.model.DestinationEntity
import com.example.travelmate.data.source.local.model.ItineraryEntity

interface TravelLocalDataSource {
    suspend fun getToken(): String?
    suspend fun saveToken(token: String)
    suspend fun saveDataDestination(itinerary: List<DestinationEntity>)
    suspend fun getDataDestination(): List<DestinationEntity>
    suspend fun saveItinerary(itinerary: ItineraryEntity)
    suspend fun getItinerary(): List<ItineraryEntity>
    suspend fun updateItinerary(itinerary: ItineraryEntity)
    suspend fun deleteItinerary(itinerary: ItineraryEntity)
}