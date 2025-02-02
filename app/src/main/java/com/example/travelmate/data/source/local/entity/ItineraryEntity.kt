package com.example.travelmate.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItineraryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val date: String,
    val location: String,
    val notes: String,
    val rating: Double,
    val description: String? = null,
    val photoUrl: String,
)
