package com.example.travelmate.domain.usecase.itinerary

import com.example.travelmate.domain.model.destination.Itinerary
import com.example.travelmate.domain.repositories.TravelRepository
import javax.inject.Inject

class ItineraryUseCase @Inject constructor(private val travelRepository: TravelRepository) {
    suspend fun saveItinerary(itinerary: Itinerary) {
        travelRepository.saveItinerary(itinerary)
    }

    suspend fun getItineraryList(): List<Itinerary> {
        return travelRepository.getListItinerary()
    }

    suspend fun getItinerary(): Itinerary {
        return travelRepository.getItinerary()
    }

    suspend fun updateItinerary(itinerary: Itinerary) {
        travelRepository.updateItinerary(itinerary)
    }

    suspend fun deleteItinerary(itinerary: Itinerary) {
        travelRepository.deleteItinerary(itinerary)
    }
}