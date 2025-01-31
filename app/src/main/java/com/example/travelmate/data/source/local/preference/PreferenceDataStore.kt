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
    val BEACH = stringPreferencesKey("BEACH")
    val MOUNTAIN = stringPreferencesKey("MOUNTAIN")
    val CULTURAL = stringPreferencesKey("CULTURAL")
    val CULINARY = stringPreferencesKey("CULINARY")
}


class PreferenceDataStore(private val dataStore: DataStore<Preferences>) {
    suspend fun setToken(token: String) {
        dataStore.edit {
            it[DataStoreConstants.TOKEN] = token
        }
    }

    suspend fun getToken(): String? = dataStore.data.first()[DataStoreConstants.TOKEN]

    suspend fun setBeach(beach: Boolean) {
        dataStore.edit {
            it[DataStoreConstants.BEACH] = beach.toString()
        }
    }

    suspend fun getBeach(): Boolean =
        dataStore.data.first()[DataStoreConstants.BEACH]?.toBoolean() ?: false

    suspend fun setMountain(mountain: Boolean) {
        dataStore.edit {
            it[DataStoreConstants.MOUNTAIN] = mountain.toString()
        }
    }

    suspend fun getMountain(): Boolean =
        dataStore.data.first()[DataStoreConstants.MOUNTAIN]?.toBoolean() ?: false

    suspend fun setCultural(cultural: Boolean) {
        dataStore.edit {
            it[DataStoreConstants.CULTURAL] = cultural.toString()
        }
    }

    suspend fun getCultural(): Boolean =
        dataStore.data.first()[DataStoreConstants.CULTURAL]?.toBoolean() ?: false

    suspend fun setCulinary(culinary: Boolean) {
        dataStore.edit {
            it[DataStoreConstants.CULINARY] = culinary.toString()
        }
    }

    suspend fun getCulinary(): Boolean =
        dataStore.data.first()[DataStoreConstants.CULINARY]?.toBoolean() ?: false
}
