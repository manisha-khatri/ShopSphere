package com.example.shopsphere.presentation.product_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shopsphere.presentation.ui_state.ErrorView
import com.example.shopsphere.presentation.ui_state.LoadingView

@Composable
fun ProductListScreen(navController: NavHostController, viewModel: ProductListViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsState().value

    when {
        state.isLoading -> LoadingView()
        state.error != null -> ErrorView(state.error)
        else -> ProductList(products = state.products)
    }
}
