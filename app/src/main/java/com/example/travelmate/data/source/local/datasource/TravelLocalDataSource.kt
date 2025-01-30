package com.example.travelmate.data.source.local.datasource

import com.example.travelmate.data.source.local.model.DestinationEntity
import com.example.travelmate.data.source.local.model.ItineraryEntity
import com.example.travelmate.domain.model.destination.Itinerary

interface TravelLocalDataSource {
    suspend fun getToken(): String?
    suspend fun saveToken(token: String)
    suspend fun dataSaveDestination(itinerary: List<DestinationEntity>)
    suspend fun getDataDestination(): List<DestinationEntity>
    suspend fun saveItinerary(itinerary: ItineraryEntity)

}