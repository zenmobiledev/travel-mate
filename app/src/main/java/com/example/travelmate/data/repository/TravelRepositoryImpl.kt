package com.example.travelmate.data.repository

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
    // LOGIN
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

    // USER
    override suspend fun getToken(): String? = travelLocalDataStore.getToken()

    override suspend fun saveToken(token: String) = travelLocalDataStore.saveToken(token)
    override suspend fun logout() {
        travelLocalDataStore.logoutUser()
    }

    override suspend fun setUser(user: LoginUser.User) {
        travelLocalDataStore.saveUser(mapper.mapDomainToEntities(user))
    }

    override suspend fun getUser(): LoginUser.User {
        return mapper.mapEntitiesToDomain(travelLocalDataStore.getUser())
    }

    override suspend fun saveCategory(
        beach: Boolean,
        mountain: Boolean,
        cultural: Boolean,
        culinary: Boolean,
    ) {
        travelLocalDataStore.saveCategory(beach, mountain, cultural, culinary)
    }

    override suspend fun getCategory(): Map<String, Boolean> {
        return travelLocalDataStore.getCategory()
    }

    // DESTINATION
    override suspend fun getDestinations(
        page: Int,
        limit: Int,
        category: String?,
        token: String,
        search: String?,
    ): Flow<ResultResponse<DestinationUser>> {
        return flow {
            emit(ResultResponse.Loading)
            try {
                val selectedCategories = travelLocalDataStore.getCategory()
                    .filterValues { it }
                    .keys.joinToString(",")

                val response = travelRemoteDataSource.getDestinations(
                    page = page,
                    limit = limit,
                    category = selectedCategories,
                    token = token,
                    search = search
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        val remoteDestination = mapper.mapResponseToEntities(it)
                        val domainDestination = mapper.mapResponseToDomain(it)
                        if (search != null) {
                            travelLocalDataStore.saveDataDestination(remoteDestination)
                        }

                        emit(ResultResponse.Success(domainDestination))
                    }
                } else {
                    emit(ResultResponse.Error(response.message()))
                }
            } catch (e: Exception) {
                val cachedEntities = travelLocalDataStore.getDataDestination()
                if (search.isNullOrEmpty()) {
                    if (cachedEntities.isEmpty()) {
                        emit(ResultResponse.Error("No cached data available"))
                    } else {
                        val cachedDomain = mapper.mapEntitiesToDomain(cachedEntities)
                        emit(ResultResponse.Success(cachedDomain))
                    }
                } else {
                    emit(ResultResponse.Error(e.message.toString()))
                }
            }
        }
    }

    // ITINERARY
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