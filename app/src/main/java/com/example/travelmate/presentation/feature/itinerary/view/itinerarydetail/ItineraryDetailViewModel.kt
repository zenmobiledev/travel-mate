package com.example.travelmate.presentation.feature.itinerary.view.itinerarydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmate.domain.model.destination.Itinerary
import com.example.travelmate.domain.usecase.itinerary.ItineraryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryDetailViewModel @Inject constructor(private val itineraryUseCase: ItineraryUseCase) :
    ViewModel() {
    private val _itinerary = MutableStateFlow<Itinerary?>(null)
    val itinerary: StateFlow<Itinerary?> = _itinerary.asStateFlow()

    fun updateItinerary(itinerary: Itinerary) {
        viewModelScope.launch {
            itineraryUseCase.updateItinerary(itinerary)
            getItinerary()
        }
    }

    fun deleteItinerary(itinerary: Itinerary) {
        viewModelScope.launch {
            itineraryUseCase.deleteItinerary(itinerary)
            getItinerary()
        }
    }

    fun getItinerary() {
        viewModelScope.launch {
            _itinerary.value = itineraryUseCase.getItinerary()
        }
    }
}