package com.example.travelmate.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.travelmate.data.source.local.dao.TravelDao
import com.example.travelmate.data.source.local.entity.DestinationEntity
import com.example.travelmate.data.source.local.entity.ItineraryEntity

@Database(
    entities = [DestinationEntity::class, ItineraryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun destinationDao(): TravelDao
}