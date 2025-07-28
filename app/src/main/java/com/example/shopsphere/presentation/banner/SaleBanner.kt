package com.example.shopsphere.presentation.banner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopsphere.presentation.ui_state.ErrorView
import com.example.shopsphere.presentation.ui_state.LoadingView

@Composable
fun SaleBanner(viewModel: BannerViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsState().value

    when {
        state.isLoading -> LoadingView()
        state.error != null -> ErrorView(state.error)
        else -> BannerSection(saleProducts = state.products)
    }
}
