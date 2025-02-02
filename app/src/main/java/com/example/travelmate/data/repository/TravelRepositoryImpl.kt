package com.example.travelmate.data.repository

import android.util.Log
import com.example.travelmate.data.mapper.Mapper
import com.example.travelmate.data.source.local.datasource.TravelLocalDataSource
import com.example.travelmate.data.source.remote.datasource.TravelRemoteDataSource
import com.example.travelmate.data.source.remote.model.login.request.LoginUserRequest
import com.example.travelmate.domain.model.destination.DestinationUser
import com.example.travelmate.domain.model.destination.Itinerary
import com.example.travelmate.domain.model.login.LoginUser
import com.example.travelmate.domain.repositories.TravelRepository
import com.example.travelmate.utils.ResultResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TravelRepositoryImpl @Inject constructor(
    private val travelLocalDataStore: TravelLocalDataSource,
    private val travelRemoteDataSource: TravelRemoteDataSource,
    private val mapper: Mapper,
) : TravelRepository {
    override suspend fun loginUser(loginUserRequest: LoginUserRequest): Flow<ResultResponse<LoginUser>> {
        return flow {
            emit(ResultResponse.Loading)
            try {
                val response = travelRemoteDataSource.loginUser(loginUserRequest)
                if (response.isSuccessful) {
                    val domainLoginUser = response.body()?.let {
                        mapper.mapResponseToDomain(it)
                    }
                    emit(ResultResponse.Success(domainLoginUser))
                } else {
                    emit(ResultResponse.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(ResultResponse.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getToken(): String? {
        Log.d("RepositoryImpl", "getToken: ${travelLocalDataStore.getToken()}")
        return travelLocalDataStore.getToken()
    }

    override suspend fun saveToken(token: String) = travelLocalDataStore.saveToken(token)

    override suspend fun getAllDestination(
        page: Int,
        limit: Int,
        token: String,
    ): Flow<ResultResponse<List<DestinationUser.Destination>>> {
        return flow {
            emit(ResultResponse.Loading)
            try {
                val response = travelRemoteDataSource.getAllDestination(
                    page = page,
                    limit = limit,
                    token = token
                )
                if (response.isSuccessful) {
                    val remoteDestination = response.body()?.let {
                        mapper.mapResponseToEntities(it)
                    } ?: emptyList()
                    val domainDestination = response.body()?.let {
                        mapper.mapResponseToDomain(it)
                    }
                    travelLocalDataStore.saveDataDestination(remoteDestination)
                    emit(ResultResponse.Success(domainDestination?.destinations))
                } else {
                    emit(ResultResponse.Error(response.message()))
                }
            } catch (e: Exception) {
                val cachedEntities = travelLocalDataStore.getDataDestination()
                val cachedDomain = mapper.mapEntitiesToDomain(cachedEntities)
                emit(ResultResponse.Success(cachedDomain.destinations))
            }
        }
    }

    override suspend fun saveItinerary(itinerary: Itinerary) {
        travelLocalDataStore.saveItinerary(mapper.mapDomainToEntities(itinerary))
    }

    override suspend fun getItinerary(): List<Itinerary> {
        return mapper.mapEntitiesToDomain(travelLocalDataStore.getItinerary())
    }

    override suspend fun updateItinerary(itinerary: Itinerary) {
        travelLocalDataStore.updateItinerary(mapper.mapDomainToEntities(itinerary))
    }

    override suspend fun deleteItinerary(itinerary: Itinerary) {
        travelLocalDataStore.deleteItinerary(mapper.mapDomainToEntities(itinerary))
    }
}