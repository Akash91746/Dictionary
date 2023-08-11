package com.example.dictionary.feature_favorite.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dictionary.feature_favorite.domain.models.FavoriteWord
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteWordDao {

    @Insert
    suspend fun insert(word: FavoriteWord)

    @Delete
    suspend fun delete(word: FavoriteWord)

    @Query("Delete from favoriteword where word = (:word)")
    suspend fun delete(word: String)

    @Query("Select * from favoriteword where word = (:word)")
    fun getFavoriteWord(word: String): Flow<FavoriteWord?>

    @Query("Select * from favoriteword ORDER BY createdAt DESC")
    fun getFavoriteWords(): Flow<List<FavoriteWord>>

    @Query("Select * from favoriteword ORDER BY createdAt DESC LIMIT (:limit)")
    fun getFavoriteWords(limit: Int): Flow<List<FavoriteWord>>
}