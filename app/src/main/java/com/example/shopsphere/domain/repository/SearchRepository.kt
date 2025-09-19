package com.example.shopsphere.domain.repository

import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.model.SearchSuggestion
import com.example.shopsphere.util.*

interface SearchRepository {
    suspend fun getSuggestions(query: String): Result<List<SearchSuggestion>>
    suspend fun getProducts(query: String): Result<List<Product>>
}

