package com.example.shopsphere.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
            is SearchEvent.SuggestionClicked -> onProductSearch(event.suggestion)
            is SearchEvent.ClearQuery -> onClearQuery()
            is SearchEvent.RetrySearch -> onRetrySearch()
        }
    }

    private fun onProductSearch(suggestion: String) {
        val newTextFieldValue = TextFieldValue(text = suggestion, selection = TextRange(suggestion.length))
        _uiState.update { it.copy(query = newTextFieldValue, suggestions = emptyList(), isProductLoading = true, error = null, products = emptyList()) }

        viewModelScope.launch {
            try {
                val products: List<Product> = getProductsUseCase(suggestion) // ⬅️ Call new use case
                _uiState.update {
                    it.copy(products = products, isProductLoading = false, error = null)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isProductLoading = false, error = e.message) }
            }
        }
    }

    // 🔍 Handle query change
    private fun onQueryChanged(newQuery: TextFieldValue) {
        _uiState.update { it.copy(query = newQuery, isLoading = true, error = null) }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)
            try {
                val suggestions: List<SearchSuggestion> =
                    if (newQuery.text.isBlank()) emptyList()
                    else getSearchSuggestionsUseCase(newQuery.text)

                _uiState.update { it.copy(suggestions = suggestions, isLoading = false, error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }




    private fun onClearQuery() {
        _uiState.update {
            it.copy(query = TextFieldValue(""), suggestions = emptyList(), isLoading = false, error = null)
        }
        searchJob?.cancel()
    }

    // 🔄 Retry last query
    private fun onRetrySearch() {
        uiState.value.query.text.takeIf { it.isNotBlank() }?.let {
            onQueryChanged(uiState.value.query)
        }
    }
}
