package com.example.shopsphere.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.domain.usecase.SearchProductsUseCase
import com.example.shopsphere.presentation.ui_state.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _uiState = MutableStateFlow(ProductUiState())

    val uiState: StateFlow<ProductUiState> = _uiState
    val searchQuery: StateFlow<String> = _searchQuery

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        performSearch(newQuery)
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                _uiState.value = ProductUiState()
                return@launch
            }

            _uiState.value = ProductUiState(isLoading = true)
            try {
                val results = searchProductsUseCase(query)
                _uiState.value = ProductUiState(products = results)
            } catch (e: Exception) {
                _uiState.value = ProductUiState(error = e.message ?: "Search failed")
            }
        }
    }
}
