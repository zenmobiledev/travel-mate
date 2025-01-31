package com.example.travelmate.presentation.feature.itinerary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmate.domain.model.destination.Itinerary
import com.example.travelmate.domain.usecase.itinerary.ItineraryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryViewModel @Inject constructor(private val itineraryUseCase: ItineraryUseCase) :
    ViewModel() {
    private val _itineraryList = MutableLiveData<List<Itinerary>>()
    val itineraryList: LiveData<List<Itinerary>> = _itineraryList

    fun fetchItinerary() {
        viewModelScope.launch {
            _itineraryList.value = itineraryUseCase.getItinerary()
        }
    }
}