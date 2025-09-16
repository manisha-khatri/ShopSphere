package com.example.shopsphere.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.model.SearchSuggestion
import com.example.shopsphere.domain.usecase.GetProductsUseCase
import com.example.shopsphere.domain.usecase.GetSearchSuggestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchSuggestionsUseCase: GetSearchSuggestionsUseCase,
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChanged -> onQueryChanged(event.query)
            is SearchEvent.SuggestionClicked -> onSuggestionClicked(event.suggestion)
            is SearchEvent.ClearQuery -> onClearQuery()
            is SearchEvent.RetrySearch -> onRetrySearch()
            is SearchEvent.ProductSearch -> onProductSearch(event.query)
        }
    }

    private fun onProductSearch(query: String) {
        _uiState.update { it.copy(isProductLoading = true, error = null, products = emptyList() ) }

        viewModelScope.launch {
            try {
                val products: List<Product> = getProductsUseCase(query) // ⬅️ Call new use case
                _uiState.update {
                    it.copy(products = products, isProductLoading = false, error = null)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isProductLoading = false, error = e.message) }
            }
        }
    }

    // 🔍 Handle query change
    private fun onQueryChanged(query: String) {
        _uiState.update { it.copy(query = query, isLoading = true, error = null) }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)
            try {
                val suggestions: List<SearchSuggestion> =
                    if (query.isBlank()) emptyList()
                    else getSearchSuggestionsUseCase(query)

                _uiState.update { it.copy(suggestions = suggestions, isLoading = false, error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun onSuggestionClicked(suggestion: String) {
        _uiState.update { it.copy(query = suggestion, suggestions = emptyList()) }
        onEvent(SearchEvent.ProductSearch(suggestion))
    }

    // ❌ Clear query & suggestions
    private fun onClearQuery() {
        _uiState.update { it.copy(query = "", suggestions = emptyList(), isLoading = false, error = null) }
        searchJob?.cancel()
    }

    // 🔄 Retry last query
    private fun onRetrySearch() {
        uiState.value.query.takeIf { it.isNotBlank() }?.let {
            onQueryChanged(it)
        }
    }
}
