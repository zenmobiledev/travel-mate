package com.example.travelmate.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DestinationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val category: String,
    val name: String,
    val photoUrl: String,
    val description: String,
    val location: String,
    val rating: Double,
)
