package com.example.travelmate.domain.usecase.category

import com.example.travelmate.domain.repositories.TravelRepository
import javax.inject.Inject

class CategoryUseCase @Inject constructor(private val repository: TravelRepository) {
    suspend fun saveCategory(
        beach: Boolean,
        mountain: Boolean,
        cultural: Boolean,
        culinary: Boolean,
    ) {
        return repository.saveCategory(
            beach = beach,
            mountain = mountain,
            cultural = cultural,
            culinary = culinary,
        )
    }

    suspend fun getCategory(): Map<String, Boolean> {
        return repository.getCategory()
    }
}