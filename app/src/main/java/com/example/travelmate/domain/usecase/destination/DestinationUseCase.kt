package com.example.travelmate.domain.usecase.destination

import com.example.travelmate.domain.model.destination.DestinationUser
import com.example.travelmate.domain.repositories.TravelRepository
import com.example.travelmate.utils.ResultResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DestinationUseCase @Inject constructor(private val travelRepository: TravelRepository) {
    suspend operator fun invoke(
        page: Int,
        limit: Int,
        category: String? = null,
        token: String,
        search: String? = null,
    ): Flow<ResultResponse<DestinationUser>> {
        return travelRepository.getDestinations(
            page = page,
            limit = limit,
            category = category,
            token = token,
            search = search
        )
    }

    suspend fun getToken(): String? {
        return travelRepository.getToken()
    }
}