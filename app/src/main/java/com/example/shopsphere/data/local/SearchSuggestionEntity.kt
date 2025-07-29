package com.example.shopsphere.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_suggestions")
data class SearchSuggestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val query: String,
    val suggestion: String,
    val timestamp: Long = System.currentTimeMillis()
)
