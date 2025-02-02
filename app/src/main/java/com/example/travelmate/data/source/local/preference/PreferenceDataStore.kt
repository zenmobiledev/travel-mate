package com.example.travelmate.data.source.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.travelmate.data.source.local.entity.LoginUserEntity
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "travel_pref")

object DataStoreConstants {
    // USER
    val TOKEN = stringPreferencesKey("TOKEN")
    val NAME = stringPreferencesKey("NAME")
    val EMAIL = stringPreferencesKey("EMAIL")
    val PHOTO_URL = stringPreferencesKey("PHOTO_URL")

    // CATEGORY
    val BEACH = booleanPreferencesKey("BEACH")
    val MOUNTAIN = booleanPreferencesKey("MOUNTAIN")
    val CULTURAL = booleanPreferencesKey("CULTURAL")
    val CULINARY = booleanPreferencesKey("CULINARY")
}

class PreferenceDataStore(private val dataStore: DataStore<Preferences>) {
    suspend fun setToken(token: String) {
        dataStore.edit {
            it[DataStoreConstants.TOKEN] = token
        }
    }

    suspend fun getToken(): String? = dataStore.data.first()[DataStoreConstants.TOKEN]

    suspend fun setUser(user: LoginUserEntity.User) {
        dataStore.edit {
            it[DataStoreConstants.NAME] = user.name
            it[DataStoreConstants.EMAIL] = user.email
            it[DataStoreConstants.PHOTO_URL] = user.photoUrl ?: ""
        }
    }

    suspend fun getUser(): LoginUserEntity.User {
        val preferences = dataStore.data.first()
        val name = preferences[DataStoreConstants.NAME] ?: ""
        val email = preferences[DataStoreConstants.EMAIL] ?: ""
        val photoUrl = preferences[DataStoreConstants.PHOTO_URL] ?: ""
        return LoginUserEntity.User(name, email, photoUrl)
    }

    suspend fun setCategory(
        beach: Boolean,
        mountain: Boolean,
        cultural: Boolean,
        culinary: Boolean,
    ) {
        dataStore.edit {
            it[DataStoreConstants.BEACH] = beach
            it[DataStoreConstants.MOUNTAIN] = mountain
            it[DataStoreConstants.CULTURAL] = cultural
            it[DataStoreConstants.CULINARY] = culinary
        }
    }

    suspend fun getCategory(): Map<String, Boolean> {
        val preferences = dataStore.data.first()
        return mapOf(
            "Beach" to (preferences[DataStoreConstants.BEACH] ?: false),
            "Mountain" to (preferences[DataStoreConstants.MOUNTAIN] ?: false),
            "Cultural" to (preferences[DataStoreConstants.CULTURAL] ?: false),
            "Culinary" to (preferences[DataStoreConstants.CULINARY] ?: false)
        )
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            listOf(
                DataStoreConstants.TOKEN,
                DataStoreConstants.NAME,
                DataStoreConstants.EMAIL,
                DataStoreConstants.PHOTO_URL,
                DataStoreConstants.BEACH,
                DataStoreConstants.MOUNTAIN,
                DataStoreConstants.CULTURAL,
                DataStoreConstants.CULINARY,
            ).forEach { preferences.remove(it) }
        }
    }
}
