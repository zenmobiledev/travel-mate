package com.example.travelmate.domain.model.destination

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class DestinationUser(
    val destinations: List<Destination>,
    val pagination: Pagination?,
) {
    @Parcelize
    data class Destination(
        val id: Int,
        val category: String,
        val name: String,
        val photoUrl: String,
        val description: String,
        val location: String,
        val rating: Double,
        val date: String? = null,
        val title: String? = null,
        val notes: String? = null,
    ) : Parcelable

    data class Pagination(
        val currentPage: Int,
        val itemsPerPage: Int,
        val totalItems: Int,
        val totalPages: Int,
    )
}
