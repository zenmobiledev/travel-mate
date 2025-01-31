package com.example.travelmate.data.mapper

import com.example.travelmate.data.source.local.model.DestinationEntity
import com.example.travelmate.data.source.local.model.ItineraryEntity
import com.example.travelmate.data.source.remote.model.destination.DestinationResponse
import com.example.travelmate.data.source.remote.model.login.response.LoginUserResponse
import com.example.travelmate.domain.model.destination.DestinationUser
import com.example.travelmate.domain.model.destination.Itinerary
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
//            pagination = mapResponseToDomain(response.data.pagination)
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

//    private fun mapResponseToDomain(pagination: DestinationResponse.Data.Pagination): DestinationUser.Pagination {
//        return DestinationUser.Pagination(
//            currentPage = pagination.currentPage,
//            itemsPerPage = pagination.itemsPerPage,
//            totalItems = pagination.totalItems,
//            totalPages = pagination.totalPages
//        )
//    }

    fun mapResponseToEntities(response: DestinationResponse): List<DestinationEntity> {
        return response.data.destinations.map {
            DestinationEntity(
                id = it.id,
                category = it.category,
                description = it.description,
                location = it.location,
                name = it.name,
                photoUrl = it.photoUrl,
                rating = it.rating,
            )
        }
    }

    fun mapEntitiesToDomain(entity: List<DestinationEntity>): DestinationUser {
        return DestinationUser(
            destinations = entity.map {
                DestinationUser.Destination(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    location = it.location,
                    category = it.category,
                    photoUrl = it.photoUrl,
                    rating = it.rating
                )
            }
        )
    }

    fun mapEntitiesToDomain(entity: List<ItineraryEntity>): List<Itinerary> {
        return entity.map {
            Itinerary(
                id = it.id,
                name = it.name,
                date = it.date,
                location = it.location,
                notes = it.notes,
                photo = it.photo,
                rating = it.rating,
                description = it.description
            )
        }
    }

    // ITINERARY
    fun mapDomainToEntities(entity: DestinationUser.Destination): DestinationEntity {
        return DestinationEntity(
            id = entity.id,
            name = entity.name,
            photoUrl = entity.photoUrl,
            location = entity.location,
            description = entity.description,
            category = entity.category,
            rating = entity.rating
        )
    }

    fun mapDomainToEntities(entity: Itinerary): ItineraryEntity {
        return ItineraryEntity(
            id = entity.id,
            name = entity.name,
            date = entity.date,
            location = entity.location,
            notes = entity.notes,
            photo = entity.photo,
            rating = entity.rating,
            description = entity.description
        )
    }
}