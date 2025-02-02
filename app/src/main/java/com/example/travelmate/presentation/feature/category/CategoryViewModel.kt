package com.example.travelmate.presentation.feature.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmate.domain.usecase.category.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryUseCase: CategoryUseCase) :
    ViewModel() {
    fun saveCategory(beach: Boolean, mountain: Boolean, cultural: Boolean, culinary: Boolean) {
        viewModelScope.launch {
            categoryUseCase.saveCategory(beach, mountain, cultural, culinary)
        }
    }

    suspend fun getCategory(): Map<String, Boolean> {
        return categoryUseCase.getCategory()
    }
}