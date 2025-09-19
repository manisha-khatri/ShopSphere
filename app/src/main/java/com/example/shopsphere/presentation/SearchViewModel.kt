package com.example.shopsphere.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.domain.usecase.GetProductsUseCase
import com.example.shopsphere.domain.usecase.GetSearchSuggestionsUseCase
import com.example.shopsphere.util.Result
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
        _uiState.update {
            it.copy(
                query = newTextFieldValue,
                suggestions = emptyList(),
                isProductLoading = true,
                error = null,
                products = emptyList()
            )
        }

        viewModelScope.launch {
            when(val result = getProductsUseCase(suggestion)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            products = result.data,
                            isProductLoading = false,
                            error = null
                        )
                    }
                }
                is Result.Failure -> {
                    _uiState.update {
                        it.copy(
                            isProductLoading = false,
                            error = result.throwable.message ?: "Unexpected error"
                        )
                    }
                }
            }
        }
    }

    private fun onQueryChanged(newQuery: TextFieldValue) {
        _uiState.update { it.copy(query = newQuery, isLoading = true, error = null) }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)

            when(val result = getSearchSuggestionsUseCase(newQuery.text)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            suggestions = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Result.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable.message ?: "Unexpected error"
                        )
                    }
                }
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
