package com.example.travelmate.presentation.feature.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmate.domain.model.destination.DestinationUser
import com.example.travelmate.domain.model.destination.Itinerary
import com.example.travelmate.domain.usecase.destination.DestinationUseCase
import com.example.travelmate.domain.usecase.itinerary.ItineraryUseCase
import com.example.travelmate.utils.ResultResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelViewModel @Inject constructor(
    private val destinationUseCase: DestinationUseCase,
    private val itineraryUseCase: ItineraryUseCase,
) : ViewModel() {
    private val _getAllDestination: MutableStateFlow<List<DestinationUser.Destination>?> =
        MutableStateFlow(emptyList())
    val getAllDestination: StateFlow<List<DestinationUser.Destination>?> =
        _getAllDestination.asStateFlow()

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

    suspend fun updateItinerary(itinerary: Itinerary) {
        return itineraryUseCase.updateItinerary(itinerary)
    }

    suspend fun deleteItinerary(itinerary: Itinerary) {
        return itineraryUseCase.deleteItinerary(itinerary)
    }

    fun getAllDestination(page: Int, limit: Int, token: String) {
        viewModelScope.launch {
            destinationUseCase(
                page = page,
                limit = limit,
                token = token
            ).collect { result ->
                when (result) {
                    ResultResponse.Loading -> _isLoading.value = true
                    is ResultResponse.Success -> {
                        _isLoading.value = false
                        result.data?.let {
                            val oldPage = _getAllDestination.value ?: emptyList()
                            _getAllDestination.value = oldPage + it
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

    fun getItinerary(destinationId: String): Flow<Itinerary?> = flow {
        emit(itineraryUseCase.getItinerary().find { it.id.toString() == destinationId })
    }.flowOn(Dispatchers.IO)
}