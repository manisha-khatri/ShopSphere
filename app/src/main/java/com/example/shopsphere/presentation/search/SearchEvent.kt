package com.example.shopsphere.presentation.search

sealed class SearchEvent {
    data class QueryChanged(val query: String) : SearchEvent()
    data class SuggestionClicked(val suggestion: String) : SearchEvent()
    object ClearQuery : SearchEvent()
    object RetrySearch : SearchEvent()
}



