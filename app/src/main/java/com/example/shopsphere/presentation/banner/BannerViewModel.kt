package com.example.shopsphere.presentation.banner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.usecase.GetSaleProductsUseCase
import com.example.shopsphere.presentation.ui_state.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(
    private val getSaleProductsUseCase: GetSaleProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState

    init {
        fetchSaleProducts()
    }

    private fun fetchSaleProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState(isLoading = true)
            try {
                val products: List<Product> = getSaleProductsUseCase()
                _uiState.value = ProductUiState(products = products)
            } catch (e: Exception) {
                _uiState.value = ProductUiState(error = e.message ?: "Unknown Error")
            }
        }
    }
}
