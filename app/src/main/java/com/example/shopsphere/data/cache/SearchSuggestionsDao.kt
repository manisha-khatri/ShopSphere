package com.example.shopsphere.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchSuggestionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuggestions(suggestions: List<CachedSuggestion>)

    @Query("SELECT * FROM cached_suggestions WHERE suggestion LIKE '%' || :query || '%'")
    fun getSuggestions(query: String): Flow<List<CachedSuggestion>>

    @Query("DELETE FROM cached_suggestions")
    suspend fun clearSuggestions()

    @Transaction
    suspend fun clearAndInsert(suggestions: List<CachedSuggestion>) {
        clearSuggestions()
        insertSuggestions(suggestions)
    }
}