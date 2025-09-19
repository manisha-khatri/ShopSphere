package com.example.shopsphere.domain.usecase

import com.example.shopsphere.domain.model.SearchSuggestion
import com.example.shopsphere.domain.repository.SearchRepository
import com.example.shopsphere.util.Result
import javax.inject.Inject

class GetSearchSuggestionsUseCase @Inject constructor(private val repository: SearchRepository) {
    suspend operator fun invoke(query: String): Result<List<SearchSuggestion>> {
        return when(val result = repository.getSuggestions(query)) {
            is Result.Failure -> result
            is Result.Success -> Result.Success(result.data.take(7))
        }
    }
}
