package com.example.shopsphere.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: SearchApiService
) {

    suspend fun fetchSuggestions(query: String): List<String> {
        return try {
            api.getSuggestions(query)
        } catch (e: Exception) {
            emptyList() // fallback in case of network failure
        }
    }
}
