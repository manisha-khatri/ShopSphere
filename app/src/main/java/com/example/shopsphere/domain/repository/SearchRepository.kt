package com.example.shopsphere.domain.repository

import com.example.shopsphere.domain.model.SearchSuggestion

interface SearchRepository {
    suspend fun getSearchSuggestions(query: String): List<SearchSuggestion>
    suspend fun getCachedSuggestions(query: String): List<SearchSuggestion>
    suspend fun saveSuggestions(query: String, suggestions: List<SearchSuggestion>)
}

