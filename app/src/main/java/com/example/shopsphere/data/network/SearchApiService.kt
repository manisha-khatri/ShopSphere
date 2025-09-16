package com.example.shopsphere.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("suggestions")
    suspend fun getSearchSuggestions(@Query("text") query: String = ""): List<SearchSuggestionDto>

    @GET("products")
    suspend fun getProducts(@Query("query") query: String = ""): ProductsResponse
}