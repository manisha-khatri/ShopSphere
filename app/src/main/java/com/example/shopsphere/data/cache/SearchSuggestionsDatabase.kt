package com.example.shopsphere.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CachedSuggestion::class],
    version = 1,
    exportSchema = false
)
abstract class SearchSuggestionsDatabase : RoomDatabase() {
    abstract fun searchSuggestionsDao(): SearchSuggestionsDao
}