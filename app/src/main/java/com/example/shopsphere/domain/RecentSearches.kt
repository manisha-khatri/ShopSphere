package com.example.shopsphere.domain

data class RecentSearches(
    val query: String = "",
    val isSearching: Boolean = false,
    val hasFocus: Boolean = false,
    val recentSearches: List<String> = emptyList(),
    val error: String? = null
)
