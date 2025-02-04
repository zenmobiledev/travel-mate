package com.example.travelmate.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.travelmate.data.source.local.entity.DestinationEntity
import com.example.travelmate.data.source.local.entity.ItineraryEntity

@Dao
interface TravelDao {
    // DESTINATION
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestination(itinerary: List<DestinationEntity>)

    @Query("SELECT * FROM destinationentity")
    suspend fun getDestination(): List<DestinationEntity>

    // ITINERARY
    @Insert
    suspend fun insertItinerary(itinerary: ItineraryEntity)

    @Query("SELECT * FROM itineraryentity")
    suspend fun getAllItinerary(): List<ItineraryEntity>

    @Query("SELECT * FROM itineraryentity")
    suspend fun getItemItinerary(): ItineraryEntity

    @Update
    suspend fun updateItinerary(itinerary: ItineraryEntity)

    @Delete
    suspend fun deleteItinerary(itinerary: ItineraryEntity)
}