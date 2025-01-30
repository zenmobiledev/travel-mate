package com.example.travelmate.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.travelmate.data.source.local.model.DestinationEntity
import com.example.travelmate.data.source.local.model.ItineraryEntity

@Dao
interface TravelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItinerary(itineraryEntity: ItineraryEntity)

    @Insert
    suspend fun insertItinerary(itinerary: List<DestinationEntity>)

    @Query("SELECT * FROM destinationentity")
    suspend fun getItinerary(): List<DestinationEntity>

    @Update
    suspend fun updateItinerary(itinerary: DestinationEntity)

    @Delete
    suspend fun deleteItinerary(itinerary: DestinationEntity)
}