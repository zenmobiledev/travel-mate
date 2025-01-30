package com.example.travelmate.data.source.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "travel_pref")

object DataStoreConstants {
    val TOKEN = stringPreferencesKey("TOKEN")
}


class PreferenceDataStore(private val dataStore: DataStore<Preferences>) {
    suspend fun setToken(token: String) {
        dataStore.edit {
            it[DataStoreConstants.TOKEN] = token
        }
    }

    suspend fun getToken(): String? = dataStore.data.first()[DataStoreConstants.TOKEN]
}
