package com.example.travelmate.presentation.feature.travel.view.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmate.domain.model.destination.DestinationUser
import com.example.travelmate.domain.model.destination.Itinerary
import com.example.travelmate.domain.usecase.destination.DestinationUseCase
import com.example.travelmate.domain.usecase.itinerary.ItineraryUseCase
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
class TravelViewModel @Inject constructor(
    private val destinationUseCase: DestinationUseCase,
    private val itineraryUseCase: ItineraryUseCase,
) : ViewModel() {
    private val _destinationData = MutableStateFlow(DestinationUser(emptyList(), null))
    val destinationData: StateFlow<DestinationUser> = _destinationData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    suspend fun getToken(): String? {
        return destinationUseCase.getToken()
    }

    suspend fun saveItinerary(itinerary: Itinerary) {
        return itineraryUseCase.saveItinerary(itinerary)
    }

    fun fetchDestinations(
        token: String,
        categories: List<String> = emptyList(),
        search: String? = null,
    ) {
        viewModelScope.launch {
            destinationUseCase(
                page = 1,
                limit = 10,
                category = categories.joinToString(","),
                token = token,
                search = search,
            ).collect { result ->
                when (result) {
                    ResultResponse.Loading -> {
                        _isLoading.value = true
                    }

                    is ResultResponse.Success -> {
                        _isLoading.value = false
                        result.data?.let { destinationUser ->
                            _destinationData.value = DestinationUser(
                                destinationUser.destinations,
                                destinationUser.pagination
                            )
                        }
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