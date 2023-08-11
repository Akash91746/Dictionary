package com.example.dictionary.feature_searchHistory.domain.repository

import com.example.dictionary.feature_searchHistory.domain.models.SearchData
import kotlinx.coroutines.flow.Flow

interface SearchDataRepository {

    suspend fun insert(searchData: SearchData)

    suspend fun update(searchData: SearchData)

    suspend fun delete(searchData: SearchData)

    fun getDataList(): Flow<List<SearchData>>


    fun getDataList(limit: Int): Flow<List<SearchData>>
}