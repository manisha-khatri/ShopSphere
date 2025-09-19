package com.example.shopsphere.presentation

import androidx.compose.ui.text.input.TextFieldValue

sealed class SearchEvent {
    data class QueryChanged(val query: TextFieldValue) : SearchEvent()
    data class SuggestionClicked(val suggestion: String) : SearchEvent()
    object ClearQuery : SearchEvent()
    object RetrySearch : SearchEvent()
}