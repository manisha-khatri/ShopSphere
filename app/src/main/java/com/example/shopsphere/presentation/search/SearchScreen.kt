package com.example.shopsphere.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = state.query,
            onValueChange = {
                viewModel.onEvent(SearchEvent.QueryChanged(it))
            },
            label = { Text("Search Products") },
            modifier = Modifier.fillMaxWidth()
        )

        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
        }

        if (state.suggestions.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.suggestions.size) { index ->
                    val suggestion = state.suggestions[index]
                    Text(
                        text = suggestion.suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(SearchEvent.SuggestionClicked(suggestion.suggestion))
                            }
                            .padding(12.dp)
                    )
                }
            }
        } else if (!state.isLoading && state.query.isNotBlank()) {
            Text(
                text = "No suggestions found",
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Placeholder for product list
        Text("🔧 Product list will appear here...", style = MaterialTheme.typography.bodyLarge)
    }
}
