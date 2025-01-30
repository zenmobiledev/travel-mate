package com.example.travelmate.domain.model.destination

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class DestinationUser(
    val destinations: List<Destination>,
    val pagination: Pagination,
) {
    @Parcelize
    data class Destination(
        val id: Int,
        val name: String,
        val description: String,
        val location: String,
        val category: String,
        val photoUrl: String,
        val rating: Double,
    ) : Parcelable

    data class Pagination(
        val currentPage: Int,
        val itemsPerPage: Int,
        val totalItems: Int,
        val totalPages: Int,
    )
}
