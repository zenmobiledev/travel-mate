package com.example.travelmate.data.source.local.datasource

import com.example.travelmate.data.source.local.dao.TravelDao
import com.example.travelmate.data.source.local.entity.DestinationEntity
import com.example.travelmate.data.source.local.entity.ItineraryEntity
import com.example.travelmate.data.source.local.entity.LoginUserEntity
import com.example.travelmate.data.source.local.preference.PreferenceDataStore
import javax.inject.Inject

class TravelLocalDataSourceImpl @Inject constructor(
    private val dataStore: PreferenceDataStore,
    private val travelDao: TravelDao,
) : TravelLocalDataSource {
    // User
    override suspend fun getToken(): String? = dataStore.getToken()
    override suspend fun saveToken(token: String) = dataStore.setToken(token)
    override suspend fun saveUser(user: LoginUserEntity.User) {
        dataStore.setUser(user = user)
    }

    override suspend fun getUser(): LoginUserEntity.User {
        return dataStore.getUser()
    }

    override suspend fun logoutUser() {
        dataStore.logout()
    }

    override suspend fun saveCategory(
        beach: Boolean,
        mountain: Boolean,
        cultural: Boolean,
        culinary: Boolean,
    ) {
        dataStore.setCategory(beach, mountain, cultural, culinary)
    }

    override suspend fun getCategory(): Map<String, Boolean> {
        return dataStore.getCategory()
    }


    // DESTINATION
    override suspend fun saveDataDestination(itinerary: List<DestinationEntity>) {
        travelDao.insertDestination(itinerary)
    }

    override suspend fun getDataDestination(): List<DestinationEntity> {
        return travelDao.getDestination()
    }

    // ITINERARY
    override suspend fun saveItinerary(itinerary: ItineraryEntity) {
        return travelDao.insertItinerary(itinerary)
    }

    override suspend fun getListItinerary(): List<ItineraryEntity> {
        return travelDao.getAllItinerary()
    }

    override suspend fun getItinerary(): ItineraryEntity {
        return travelDao.getItemItinerary()
    }

    override suspend fun updateItinerary(itinerary: ItineraryEntity) {
        return travelDao.updateItinerary(itinerary)
    }

    override suspend fun deleteItinerary(itinerary: ItineraryEntity) {
        return travelDao.deleteItinerary(itinerary)
    }
}