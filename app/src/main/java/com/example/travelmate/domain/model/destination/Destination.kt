package com.example.travelmate.domain.model.destination

data class DestinationUser(
    val destinations: List<Destination>,
    val pagination: Pagination,
) {
    data class Destination(
        val id: Int,
        val name: String,
        val description: String,
        val location: String,
        val category: String,
        val photoUrl: String,
        val rating: Double,
    )

    data class Pagination(
        val currentPage: Int,
        val itemsPerPage: Int,
        val totalItems: Int,
        val totalPages: Int,
    )
}
