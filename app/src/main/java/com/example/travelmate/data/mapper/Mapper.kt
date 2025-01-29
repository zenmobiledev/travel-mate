package com.example.travelmate.data.mapper

import com.example.travelmate.data.source.remote.model.login.response.LoginUserResponse
import com.example.travelmate.domain.model.LoginUser
import javax.inject.Inject

class Mapper @Inject constructor() {
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
}