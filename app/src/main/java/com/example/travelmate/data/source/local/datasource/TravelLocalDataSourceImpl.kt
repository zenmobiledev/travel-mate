package com.example.travelmate.data.source.local.datasource

import com.example.travelmate.data.source.local.dao.TravelDao
import com.example.travelmate.data.source.local.model.DestinationEntity
import com.example.travelmate.data.source.local.model.ItineraryEntity
import com.example.travelmate.data.source.local.preference.PreferenceDataStore
import javax.inject.Inject

class TravelLocalDataSourceImpl @Inject constructor(
    private val dataStore: PreferenceDataStore,
    private val travelDao: TravelDao,
) : TravelLocalDataSource {
    override suspend fun getToken(): String? = dataStore.getToken()

    override suspend fun saveToken(token: String) = dataStore.setToken(token)

    override suspend fun saveDataDestination(itinerary: List<DestinationEntity>) {
        travelDao.insertDestination(itinerary)
    }

    override suspend fun getDataDestination(): List<DestinationEntity> {
        return travelDao.getDestination()
    }

    override suspend fun saveItinerary(itinerary: ItineraryEntity) {
        return travelDao.insertItinerary(itinerary)
    }

    override suspend fun getItinerary(): List<ItineraryEntity> {
        return travelDao.getAllItinerary()
    }

    override suspend fun updateItinerary(itinerary: ItineraryEntity) {
        return travelDao.updateItinerary(itinerary)
    }

    override suspend fun deleteItinerary(itinerary: ItineraryEntity) {
        return travelDao.deleteItinerary(itinerary)
    }
}