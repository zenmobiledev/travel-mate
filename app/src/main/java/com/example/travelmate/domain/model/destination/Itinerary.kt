package com.example.travelmate.domain.model.destination

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Itinerary(
    val id: Int,
    val name: String,
    val date: String,
    val photoUrl: String,
    val rating: Double,
    val description: String? = null,
    val location: String,
    val notes: String,
) : Parcelable
