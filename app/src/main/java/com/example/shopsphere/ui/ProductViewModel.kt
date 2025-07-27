package com.example.shopsphere.ui

import androidx.lifecycle.ViewModel
import com.example.shopsphere.domain.RecentSearches
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(): ViewModel() {

    val recentSearchesState = MutableStateFlow<UIState<RecentSearches>>(UIState.Loading)
    val _recentSearchesState: StateFlow<UIState<RecentSearches>> = recentSearchesState

    fun fetchRecentSearches() {

    }

}