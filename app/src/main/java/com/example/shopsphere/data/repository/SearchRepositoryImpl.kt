package com.example.shopsphere.data.repository

import com.example.shopsphere.data.cache.SearchSuggestionsDao
import com.example.shopsphere.data.network.SearchApiService
import com.example.shopsphere.data.toCachedSuggestion
import com.example.shopsphere.data.toProduct
import com.example.shopsphere.data.toSearchSuggestion
import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.model.SearchSuggestion
import com.example.shopsphere.domain.repository.SearchRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val api: SearchApiService,
    private val dao: SearchSuggestionsDao
) : SearchRepository {

    override suspend fun getSuggestions(query: String): List<SearchSuggestion> {
        return try {
            val networkSuggestions = api.getSearchSuggestions(query)
            dao.clearAndInsert(networkSuggestions.map { it.toCachedSuggestion() })
            networkSuggestions.map { it.toSearchSuggestion() }
        } catch (e: Exception) {
            val cachedSuggestions = dao.fetchSuggestions(query).firstOrNull() ?: emptyList()
            cachedSuggestions.map { it.toSearchSuggestion() }
        }
    }

    override suspend fun getProducts(query: String): List<Product> {
        return try {
            val networkProducts = api.getProducts(query).products
            networkProducts.map { it.toProduct() }
        } catch (e: Exception) {
            throw e
        }
    }
}