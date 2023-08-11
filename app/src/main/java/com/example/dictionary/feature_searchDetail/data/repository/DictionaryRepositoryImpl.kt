package com.example.dictionary.feature_searchDetail.data.repository

import com.example.dictionary.feature_searchDetail.data.data_source.DictionaryAPI
import com.example.dictionary.feature_searchDetail.domain.models.WordData
import com.example.dictionary.feature_searchDetail.domain.repository.DictionaryRepository

class DictionaryRepositoryImpl(
    private val api: DictionaryAPI
): DictionaryRepository {

    override suspend fun getWordDetail(word: String): Result<WordData> {

        val result = api.getWordDetail(word)

        if(!result.isSuccessful){
            return Result.failure(Throwable("Error Fetching data"))
        }

        val data = result.body() ?: return Result.failure(Throwable("No data found"))

        return Result.success(data)
    }

}