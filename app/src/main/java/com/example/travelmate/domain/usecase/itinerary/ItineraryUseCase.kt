package com.example.travelmate.domain.usecase.itinerary

import com.example.travelmate.domain.model.destination.Itinerary
import com.example.travelmate.domain.repositories.TravelRepository
import javax.inject.Inject

class ItineraryUseCase @Inject constructor(private val travelRepository: TravelRepository) {
    suspend fun saveItinerary(itinerary: Itinerary) {
        travelRepository.saveItinerary(itinerary)
    }
}