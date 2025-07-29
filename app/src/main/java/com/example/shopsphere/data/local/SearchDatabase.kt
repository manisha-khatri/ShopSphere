package com.example.shopsphere.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SearchSuggestionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
}
