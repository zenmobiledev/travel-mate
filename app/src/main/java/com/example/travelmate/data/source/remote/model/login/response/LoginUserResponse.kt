package com.example.travelmate.data.source.remote.model.login.response


import com.google.gson.annotations.SerializedName

data class LoginUserResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
) {
    data class Data(
        @SerializedName("token")
        val token: String,
        @SerializedName("user")
        val user: User,
    ) {
        data class User(
            @SerializedName("email")
            val email: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("photo_url")
            val photoUrl: String?,
        )
    }
}