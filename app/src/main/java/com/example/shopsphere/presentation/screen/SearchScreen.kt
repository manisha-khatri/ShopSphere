package com.example.shopsphere.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.model.SearchSuggestion
import com.example.shopsphere.presentation.SearchEvent
import com.example.shopsphere.presentation.SearchViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onPress = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                query = state.query, // ⬅️ Pass TextFieldValue
                onQueryChange = { newQuery ->
                    viewModel.onEvent(SearchEvent.QueryChanged(newQuery))
                },
                onSearch = {
                    viewModel.onEvent(SearchEvent.RetrySearch)
                },
                keyboardController = keyboardController,
                focusManager = focusManager
            )

            if (state.isProductLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.products.isNotEmpty()) {
                ProductList(products = state.products)
            }
        }

        if (state.query.text.isNotBlank() && state.suggestions.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 72.dp) // ⬅️ Increase this value to move the dropdown down
                    .zIndex(1f)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    SuggestionsList(
                        suggestions = state.suggestions,
                        isLoading = state.isLoading,
                        query = state.query.text,
                        onSuggestionClick = { suggestion ->
                            viewModel.onEvent(SearchEvent.SuggestionClicked(suggestion))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: TextFieldValue, // ⬅️ Take TextFieldValue
    onQueryChange: (TextFieldValue) -> Unit, // ⬅️ Callback with TextFieldValue
    onSearch: () -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange, // ⬅️ This works now
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onSearch()
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        placeholder = {
            Text(
                text = "Enter your text",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            )
        },
        singleLine = true,
    )
}

@Composable
fun SuggestionsList(
    suggestions: List<SearchSuggestion>,
    isLoading: Boolean,
    query: String,
    onSuggestionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        if (!isLoading) {
            when {
                suggestions.isNotEmpty() -> {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(suggestions) { suggestion ->
                            SuggestionItem(
                                suggestion = suggestion.suggestion,
                                onClick = { onSuggestionClick(suggestion.suggestion) }
                            )
                        }
                    }
                }
                query.isNotBlank() -> {
                    Text(
                        text = "No suggestions found",
                        modifier = Modifier.padding(top = 16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun SuggestionItem(
    suggestion: String,
    onClick: () -> Unit
) {
    Text(
        text = suggestion,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun ProductList(products: List<Product>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(products) { product ->
            SimpleCard(product.name)
        }
    }
}

@Composable
fun SimpleCard(name: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(2.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}