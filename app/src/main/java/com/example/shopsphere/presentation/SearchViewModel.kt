package com.example.shopsphere.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.domain.usecase.GetProductsUseCase
import com.example.shopsphere.domain.usecase.GetSearchSuggestionsUseCase
import com.example.shopsphere.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchSuggestionsUseCase: GetSearchSuggestionsUseCase,
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    private val queryFlow = MutableStateFlow("")

    init {
        queryFlow
            .debounce(300L) // wait for user to stop typing
            .filter { it.isNotBlank() } // ignore empty queries
            .onEach { query -> fetchSuggestions(query) }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChanged -> onQueryChanged(event.query)
            is SearchEvent.SuggestionClicked -> onProductSearch(event.suggestion)
            is SearchEvent.ClearQuery -> onClearQuery()
            is SearchEvent.RetrySearch -> onRetrySearch()
        }
    }

    private fun onProductSearch(suggestion: String) {
        val newTextFieldValue = TextFieldValue(
            text = suggestion,
            selection = TextRange(suggestion.length)
        )
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
            when (val result = getProductsUseCase(suggestion)) {
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
        _uiState.update {
            it.copy(query = newQuery, isLoading = true, error = null)
        }
        queryFlow.value = newQuery.text
    }

    private fun fetchSuggestions(query: String) {
        viewModelScope.launch {
            when (val result = getSearchSuggestionsUseCase(query)) {
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
            it.copy(
                query = TextFieldValue(""),
                suggestions = emptyList(),
                isLoading = false,
                error = null
            )
        }
        queryFlow.value = ""
    }

    private fun onRetrySearch() {
        uiState.value.query.text.takeIf { it.isNotBlank() }?.let {
            queryFlow.value = it
        }
    }
}
