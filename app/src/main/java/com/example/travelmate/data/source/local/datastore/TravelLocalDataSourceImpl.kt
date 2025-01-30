package com.example.travelmate.data.source.local.datastore

import com.example.travelmate.data.source.local.pref.PreferenceDataStore
import javax.inject.Inject

class TravelLocalDataSourceImpl @Inject constructor(private val dataStore: PreferenceDataStore) :
    TravelLocalDataSource {
    override suspend fun getToken(): String? = dataStore.getToken()

    override suspend fun saveToken(token: String) = dataStore.setToken(token)
}