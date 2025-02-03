package com.example.travelmate.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItineraryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val category: String,
    val name: String,
    val photoUrl: String,
    val description: String? = null,
    val location: String,
    val rating: Double,
    val date: String,
    val title: String,
    val notes: String,
)
