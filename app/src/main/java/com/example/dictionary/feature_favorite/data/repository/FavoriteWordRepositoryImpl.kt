package com.example.dictionary.feature_favorite.data.repository

import com.example.dictionary.feature_favorite.data.data_source.FavoriteWordDao
import com.example.dictionary.feature_favorite.domain.models.FavoriteWord
import com.example.dictionary.feature_favorite.domain.repository.FavoriteWordRepository
import kotlinx.coroutines.flow.Flow

class FavoriteWordRepositoryImpl(
    private val dao: FavoriteWordDao,
) : FavoriteWordRepository {

    override suspend fun insert(word: String) {
        return dao.insert(
            FavoriteWord(word = getFormattedWord(word))
        )
    }

    override suspend fun insert(word: FavoriteWord) {
        return dao.insert(word)
    }

    override suspend fun delete(word: String) {
        return dao.delete(getFormattedWord(word))
    }

    override suspend fun delete(word: FavoriteWord) {
        return dao.delete(word)
    }

    override fun getWord(word: String): Flow<FavoriteWord?> {
        return dao.getFavoriteWord(getFormattedWord(word))
    }

    override fun getFavoriteWords(limit: Int): Flow<List<FavoriteWord>> {
        return dao.getFavoriteWords(limit)
    }

    override fun getFavoriteWords(): Flow<List<FavoriteWord>> {
        return dao.getFavoriteWords()
    }

    private fun getFormattedWord(word: String): String {
        return word.trim().uppercase()
    }

}