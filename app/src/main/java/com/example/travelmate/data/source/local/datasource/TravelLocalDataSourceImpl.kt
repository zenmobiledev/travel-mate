package com.example.travelmate.data.source.local.datasource

import com.example.travelmate.data.source.local.dao.TravelDao
import com.example.travelmate.data.source.local.model.DestinationEntity
import com.example.travelmate.data.source.local.model.ItineraryEntity
import com.example.travelmate.data.source.local.preference.PreferenceDataStore
import javax.inject.Inject

class TravelLocalDataSourceImpl @Inject constructor(
    private val dataStore: PreferenceDataStore,
    private val travelDao: TravelDao,
) :
    TravelLocalDataSource {
    override suspend fun getToken(): String? = dataStore.getToken()

    override suspend fun saveToken(token: String) = dataStore.setToken(token)

    override suspend fun dataSaveDestination(itinerary: List<DestinationEntity>) {
        travelDao.insertItinerary(itinerary)
    }

    override suspend fun getDataDestination(): List<DestinationEntity> {
        return travelDao.getItinerary()
    }

    override suspend fun saveItinerary(itinerary: ItineraryEntity) {
        travelDao.insertItinerary(itinerary)
    }
}