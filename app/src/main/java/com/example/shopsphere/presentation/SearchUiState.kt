package com.example.shopsphere.presentation

import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.model.SearchSuggestion

data class SearchUiState(
    val query: String = "",
    val suggestions: List<SearchSuggestion> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,

    val isProductLoading: Boolean = false, // ⬅️ New: Loading state for product search
    val products: List<Product> = emptyList(), // ⬅️ New: List of products
)