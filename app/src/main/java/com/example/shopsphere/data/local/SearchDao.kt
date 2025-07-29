package com.example.shopsphere.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchDao {

    @Query("SELECT * FROM search_suggestions WHERE query = :query ORDER BY timestamp DESC LIMIT 7")
    suspend fun getSuggestionsForQuery(query: String): List<SearchSuggestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuggestions(suggestions: List<SearchSuggestionEntity>)

    @Query("DELETE FROM search_suggestions WHERE query = :query")
    suspend fun deleteSuggestionsForQuery(query: String)
}
