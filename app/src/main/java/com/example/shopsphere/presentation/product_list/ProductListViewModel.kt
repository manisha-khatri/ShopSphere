package com.example.shopsphere.presentation.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.domain.usecase.GetAllProductsUseCase
import com.example.shopsphere.presentation.ui_state.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState(isLoading = true)
            try {
                val products = getAllProductsUseCase()
                _uiState.value = ProductUiState(products = products)
            } catch (e: Exception) {
                _uiState.value = ProductUiState(error = e.message ?: "Unknown error")
            }
        }
    }
}
