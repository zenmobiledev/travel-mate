package com.example.travelmate.domain.model.destination

data class Itinerary(
    val id: Int,
    val name: String,
    val description: String,
    val photoUrl: String,
    val category: String,
    val rating: Double,
    val date: String,
    val location: String,
    val notes: String? = null,
)
