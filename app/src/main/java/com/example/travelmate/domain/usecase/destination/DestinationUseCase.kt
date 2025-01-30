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
        token: String,
    ): Flow<ResultResponse<List<DestinationUser.Destination>>> {
        return travelRepository.getAllDestination(
            page = page,
            limit = limit,
            token = token
        )
    }

    suspend fun getToken(): String? {
        return travelRepository.getToken()
    }
}