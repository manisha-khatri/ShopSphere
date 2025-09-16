package com.example.shopsphere.presentation

sealed class SearchEvent {
    data class QueryChanged(val query: String) : SearchEvent()
    data class SuggestionClicked(val suggestion: String) : SearchEvent()
    data class ProductSearch(val query: String) : SearchEvent()
    object ClearQuery : SearchEvent()
    object RetrySearch : SearchEvent()
}