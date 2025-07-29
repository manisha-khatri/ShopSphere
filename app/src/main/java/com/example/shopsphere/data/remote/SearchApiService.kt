package com.example.shopsphere.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("search/suggestions")
    suspend fun getSuggestions(
        @Query("query") query: String
    ): List<String>
}
