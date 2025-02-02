package com.example.travelmate.data.source.local.entity

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
    val date: String? = null,
    val notes: String? = null,
)
