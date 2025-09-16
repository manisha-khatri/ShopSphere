package com.example.shopsphere.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_suggestions")
data class CachedSuggestion(
    @PrimaryKey
    val suggestion: String
)