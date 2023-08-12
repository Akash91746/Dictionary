package com.example.dictionary.feature_searchHistory.domain.repository

import com.example.dictionary.common.utils.BaseRepository
import com.example.dictionary.feature_searchHistory.domain.models.SearchData
import kotlinx.coroutines.flow.Flow

interface SearchDataRepository: BaseRepository<SearchData> {
    fun getDataList(): Flow<List<SearchData>>

    fun getDataList(limit: Int): Flow<List<SearchData>>
}