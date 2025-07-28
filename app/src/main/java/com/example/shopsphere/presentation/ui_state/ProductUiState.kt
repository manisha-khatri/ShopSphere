package com.example.shopsphere.presentation.ui_state

import com.example.shopsphere.domain.model.Product

data class ProductUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null
)
