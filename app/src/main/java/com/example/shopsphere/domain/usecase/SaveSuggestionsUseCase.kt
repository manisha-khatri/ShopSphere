package com.example.shopsphere.domain.usecase

import com.example.shopsphere.domain.model.SearchSuggestion
import com.example.shopsphere.domain.repository.SearchRepository
import javax.inject.Inject

class SaveSuggestionsUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String, suggestions: List<SearchSuggestion>) {
        repository.saveSuggestions(query, suggestions)
    }
}