package com.example.travelmate.data.source.local.datastore

interface TravelLocalDataSource {
    suspend fun getToken(): String?
    suspend fun saveToken(token: String)
}