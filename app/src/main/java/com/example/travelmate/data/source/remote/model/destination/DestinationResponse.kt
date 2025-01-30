package com.example.travelmate.data.source.remote.model.destination


import com.google.gson.annotations.SerializedName

data class DestinationResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("destinations")
        val destinations: List<Destination>,
        @SerializedName("pagination")
        val pagination: Pagination
    ) {
        data class Destination(
            @SerializedName("category")
            val category: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("location")
            val location: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("photo_url")
            val photoUrl: String,
            @SerializedName("rating")
            val rating: Double
        )

        data class Pagination(
            @SerializedName("current_page")
            val currentPage: Int,
            @SerializedName("items_per_page")
            val itemsPerPage: Int,
            @SerializedName("total_items")
            val totalItems: Int,
            @SerializedName("total_pages")
            val totalPages: Int
        )
    }
}