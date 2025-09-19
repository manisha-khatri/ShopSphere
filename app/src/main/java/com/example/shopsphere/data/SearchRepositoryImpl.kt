package com.example.shopsphere.data

import com.example.shopsphere.data.cache.SearchSuggestionsDao
import com.example.shopsphere.data.network.SearchApiService
import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.model.SearchSuggestion
import com.example.shopsphere.domain.repository.SearchRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton
import com.example.shopsphere.util.*

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val api: SearchApiService,
    private val dao: SearchSuggestionsDao
) : SearchRepository {

    override suspend fun getSuggestions(query: String): Result<List<SearchSuggestion>> {
        return try {
            val networkSuggestions = api.getSearchSuggestions(query)
            dao.clearAndInsert(networkSuggestions.map { it.toCachedSuggestion() })
            Result.Success(networkSuggestions.map { it.toSearchSuggestion() })
        } catch (e: Exception) {
            val cachedSuggestions = dao.fetchSuggestions(query).firstOrNull()
            if(cachedSuggestions!=null && cachedSuggestions.isNotEmpty()) {
                Result.Success(cachedSuggestions.map { it.toSearchSuggestion() })
            } else {
                Result.Failure(e)
            }
        }
    }

    override suspend fun getProducts(query: String): Result<List<Product>> {
        return try {
            val networkProducts = api.getProducts(query).products
            Result.Success(networkProducts.map { it.toProduct() })
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}