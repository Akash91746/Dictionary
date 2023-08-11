package com.example.dictionary.feature_searchDetail.domain.repository

import com.example.dictionary.feature_searchDetail.domain.models.WordData

interface DictionaryRepository {
    suspend fun getWordDetail(word: String): Result<WordData>
}