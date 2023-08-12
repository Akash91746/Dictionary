package com.example.dictionary.feature_favorite.domain.repository

import com.example.dictionary.common.utils.BaseRepository
import com.example.dictionary.feature_favorite.domain.models.FavoriteWord
import kotlinx.coroutines.flow.Flow

interface FavoriteWordRepository : BaseRepository<FavoriteWord> {

    suspend fun insert(word: String)

    suspend fun delete(word: String)

    fun getWord(word:String): Flow<FavoriteWord?>

    fun getFavoriteWords(): Flow<List<FavoriteWord>>

    fun getFavoriteWords(limit: Int): Flow<List<FavoriteWord>>

}