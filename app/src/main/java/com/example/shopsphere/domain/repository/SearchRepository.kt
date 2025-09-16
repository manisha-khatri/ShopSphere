package com.example.shopsphere.domain.repository

import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.model.SearchSuggestion

interface SearchRepository {
    suspend fun getSuggestions(query: String): List<SearchSuggestion>
    suspend fun getProducts(query: String): List<Product>
}

