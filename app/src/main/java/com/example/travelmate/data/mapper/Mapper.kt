package com.example.travelmate.data.mapper

import com.example.travelmate.data.source.remote.model.destination.DestinationResponse
import com.example.travelmate.data.source.remote.model.login.response.LoginUserResponse
import com.example.travelmate.domain.model.destination.DestinationUser
import com.example.travelmate.domain.model.login.LoginUser
import javax.inject.Inject

class Mapper @Inject constructor() {
    // LOGIN
    fun mapResponseToDomain(response: LoginUserResponse): LoginUser {
        return LoginUser(
            token = response.data.token,
            user = mapResponseToDomain(response.data.user)
        )
    }

    private fun mapResponseToDomain(user: LoginUserResponse.Data.User): LoginUser.User {
        return LoginUser.User(
            name = user.email,
            email = user.name,
            photoUrl = user.photoUrl ?: ""
        )
    }

    // DESTINATION
    fun mapResponseToDomain(response: DestinationResponse): DestinationUser {
        return DestinationUser(
            destinations = response.data.destinations.map { mapResponseToDomain(it) },
            pagination = mapResponseToDomain(response.data.pagination)
        )
    }

    private fun mapResponseToDomain(destination: DestinationResponse.Data.Destination): DestinationUser.Destination {
        return DestinationUser.Destination(
            id = destination.id,
            name = destination.name,
            description = destination.description,
            location = destination.location,
            category = destination.category,
            photoUrl = destination.photoUrl,
            rating = destination.rating
        )
    }

    private fun mapResponseToDomain(pagination: DestinationResponse.Data.Pagination): DestinationUser.Pagination {
        return DestinationUser.Pagination(
            currentPage = pagination.currentPage,
            itemsPerPage = pagination.itemsPerPage,
            totalItems = pagination.totalItems,
            totalPages = pagination.totalPages
        )
    }
}