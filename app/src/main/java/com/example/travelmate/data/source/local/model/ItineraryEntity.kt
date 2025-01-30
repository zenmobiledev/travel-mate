package com.example.travelmate.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItineraryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val photoUrl: String,
    val date: String,
    val location: String,
    val notes: String? = null,
)
