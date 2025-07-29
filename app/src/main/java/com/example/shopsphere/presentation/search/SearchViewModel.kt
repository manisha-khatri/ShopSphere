package com.example.shopsphere.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.domain.usecase.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private var searchJob: Job? = null

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChanged -> {
                _uiState.update { it.copy(query = event.query, isLoading = true) }

                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(300) // debounce user input
                    try {
                        val suggestions = searchProductsUseCase(event.query)
                        _uiState.update {
                            it.copy(
                                suggestions = suggestions,
                                isLoading = false,
                                error = null
                            )
                        }
                    } catch (e: Exception) {
                        _uiState.update { it.copy(isLoading = false, error = e.message) }
                    }
                }
            }

            is SearchEvent.SuggestionClicked -> {
                // Handle if needed, e.g., navigate or autofill
                _uiState.update { it.copy(query = event.suggestion) }
            }
        }
    }
}
