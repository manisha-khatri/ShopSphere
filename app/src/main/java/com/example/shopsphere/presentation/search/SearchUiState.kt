package com.example.shopsphere.presentation.search

import com.example.shopsphere.domain.model.SearchSuggestion

data class SearchUiState(
    val query: String = "",
    val suggestions: List<SearchSuggestion> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
