package com.example.travelmate.presentation.feature.itinerary.view.itinerary

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
class ItineraryViewModel @Inject constructor(private val itineraryUseCase: ItineraryUseCase) :
    ViewModel() {
    private val _itineraryList = MutableStateFlow<List<Itinerary>>(emptyList())
    val itineraryList: StateFlow<List<Itinerary>> = _itineraryList.asStateFlow()

    fun getListItinerary() {
        viewModelScope.launch {
            _itineraryList.value = itineraryUseCase.getItineraryList()
        }
    }
}