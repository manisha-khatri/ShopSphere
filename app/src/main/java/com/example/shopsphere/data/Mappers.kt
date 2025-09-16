package com.example.shopsphere.data

import com.example.shopsphere.data.cache.CachedSuggestion
import com.example.shopsphere.data.network.ProductDto
import com.example.shopsphere.data.network.SearchSuggestionDto
import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.model.SearchSuggestion

// Mapper from Network DTO to Domain Model
fun SearchSuggestionDto.toSearchSuggestion(): SearchSuggestion {
    return SearchSuggestion(
        suggestion = this.text
    )
}

// Mapper from Cached Entity to Domain Model
fun CachedSuggestion.toSearchSuggestion(): SearchSuggestion {
    return SearchSuggestion(
        suggestion = this.suggestion
    )
}

// Mapper from Network DTO to Cached Entity
fun SearchSuggestionDto.toCachedSuggestion(): CachedSuggestion {
    return CachedSuggestion(
        suggestion = this.text
    )
}

fun ProductDto.toProduct(): Product {
    return Product(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl
    )
}