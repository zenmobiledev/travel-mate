package com.example.travelmate.data.repository

import com.example.travelmate.data.mapper.Mapper
import com.example.travelmate.data.source.remote.datasource.TravelRemoteDataSource
import com.example.travelmate.data.source.remote.model.login.request.LoginUserRequest
import com.example.travelmate.domain.model.LoginUser
import com.example.travelmate.domain.repositories.TravelRepository
import com.example.travelmate.utils.ResultResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TravelRepositoryImpl @Inject constructor(
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
}