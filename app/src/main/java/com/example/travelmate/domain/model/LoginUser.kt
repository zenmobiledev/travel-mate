package com.example.travelmate.domain.model

data class LoginUser(
    val token: String,
    val user: User,
) {
    data class User(
        val name: String,
        val email: String,
        val photoUrl: String?,
    )
}
