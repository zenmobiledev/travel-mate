package com.example.travelmate.domain.usecase.login

import com.example.travelmate.data.source.remote.model.login.request.LoginUserRequest
import com.example.travelmate.domain.model.login.LoginUser
import com.example.travelmate.domain.repositories.TravelRepository
import com.example.travelmate.utils.ResultResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repository: TravelRepository) {
    suspend operator fun invoke(email: String, password: String): Flow<ResultResponse<LoginUser>> =
        repository.loginUser(
            LoginUserRequest(
                email = email,
                password = password
            )
        )

    suspend fun saveToken(token: String) {
        return repository.saveToken(token)
    }
}