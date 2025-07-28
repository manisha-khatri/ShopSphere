package com.example.shopsphere.presentation.search

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shopsphere.presentation.product_list.ProductList
import com.example.shopsphere.presentation.ui_state.ErrorView
import com.example.shopsphere.presentation.ui_state.LoadingView

@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsState().value
    val query by viewModel.searchQuery.collectAsState()

    SearchBar(
        query = query,
        onQueryChanged = viewModel::onQueryChanged
    )

    when {
        state.isLoading -> LoadingView()
        state.error != null -> ErrorView(state.error)
        else -> ProductList(products = state.products)
    }
}
