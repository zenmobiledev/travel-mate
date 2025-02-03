package com.example.travelmate.presentation.feature.profile

import androidx.lifecycle.ViewModel
import com.example.travelmate.domain.model.login.LoginUser
import com.example.travelmate.domain.usecase.login.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
) : ViewModel() {
    private val _user = MutableStateFlow<LoginUser.User?>(null)
    val user: StateFlow<LoginUser.User?> = _user.asStateFlow()

    suspend fun logout() {
        loginUserUseCase.logout()
    }

    suspend fun profileUser() {
        _user.value = loginUserUseCase.getUser()
    }

    suspend fun saveCategory(
        beach: Boolean,
        mountain: Boolean,
        cultural: Boolean,
        culinary: Boolean,
    ) {
        loginUserUseCase.saveCategory(beach, mountain, cultural, culinary)
    }

    suspend fun getCategory(): Map<String, Boolean> {
        return loginUserUseCase.getCategory()
    }
}