package com.example.shopsphere.data.repository

import com.example.shopsphere.data.local.LocalDataSource
import com.example.shopsphere.data.remote.RemoteDataSource
import com.example.shopsphere.domain.model.SearchSuggestion
import com.example.shopsphere.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : SearchRepository {

    override suspend fun getSearchSuggestions(query: String): List<SearchSuggestion> {
        // Step 1: Check local DB
        val cached = local.getSuggestions(query)
        if (cached.isNotEmpty()) {
            return cached.map { SearchSuggestion(it.suggestion) }
        }

        // Step 2: Fetch from API
        val remoteSuggestions = remote.fetchSuggestions(query).take(7)
        if (remoteSuggestions.isNotEmpty()) {
            local.saveSuggestions(query, remoteSuggestions)
            return remoteSuggestions.map { SearchSuggestion(it) }
        }

        return emptyList()
    }

    override suspend fun getCachedSuggestions(query: String): List<SearchSuggestion> {
        return local.getSuggestions(query).map { SearchSuggestion(it.suggestion) }
    }

    override suspend fun saveSuggestions(query: String, suggestions: List<SearchSuggestion>) {
        local.saveSuggestions(query, suggestions.map { it.suggestion })
    }
}
