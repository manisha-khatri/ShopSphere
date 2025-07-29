package com.example.shopsphere.data.local

import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: SearchDao) {

    suspend fun getSuggestions(query: String): List<SearchSuggestionEntity> {
        return dao.getSuggestionsForQuery(query)
    }

    suspend fun saveSuggestions(query: String, suggestions: List<String>) {
        // Optional: Clear existing for same query before inserting new
        dao.deleteSuggestionsForQuery(query)

        val entities = suggestions.map {
            SearchSuggestionEntity(query = query, suggestion = it)
        }

        dao.insertSuggestions(entities)
    }
}
