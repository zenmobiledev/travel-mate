package com.example.travelmate.domain.model.destination

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Itinerary(
    val id: Int,
    val category: String,
    val name: String,
    val photoUrl: String,
    val description: String? = null,
    val location: String,
    val rating: Double,
    val date: String,
    val title: String,
    val notes: String,
) : Parcelable
