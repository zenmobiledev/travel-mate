package com.example.travelmate.presentation.feature.itinerary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItineraryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is itinerary Fragment"
    }
    val text: LiveData<String> = _text
}