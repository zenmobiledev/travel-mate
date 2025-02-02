package com.example.travelmate.data.source.local.entity

data class LoginUserEntity(
    val token: String,
    val user: User,
) {
    data class User(
        val name: String,
        val email: String,
        val photoUrl: String? = null,
    )
}
