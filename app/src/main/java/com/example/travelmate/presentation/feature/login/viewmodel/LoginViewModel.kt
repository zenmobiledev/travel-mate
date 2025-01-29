package com.example.travelmate.presentation.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmate.domain.usecase.login.LoginUserUseCase
import com.example.travelmate.utils.ResultResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUserUseCase: LoginUserUseCase) :
    ViewModel() {
    private val _token: MutableStateFlow<String?> = MutableStateFlow(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginUserUseCase(
                email = email,
                password = password
            ).collect { result ->
                when (result) {
                    ResultResponse.Loading -> _isLoading.value = true
                    is ResultResponse.Success -> {
                        _isLoading.value = false
                        _token.value = result.data?.token
                    }

                    is ResultResponse.Error -> {
                        _isLoading.value = false
                        _errorMessage.emit(result.message)
                    }
                }
            }
        }
    }
}