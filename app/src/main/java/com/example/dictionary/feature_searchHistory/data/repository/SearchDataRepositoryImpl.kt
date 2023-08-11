package com.example.dictionary.feature_searchHistory.data.repository

import com.example.dictionary.feature_searchHistory.data.data_source.SearchDataDao
import com.example.dictionary.feature_searchHistory.domain.models.SearchData
import com.example.dictionary.feature_searchHistory.domain.repository.SearchDataRepository
import kotlinx.coroutines.flow.Flow

class SearchDataRepositoryImpl(
    private val dao: SearchDataDao,
) : SearchDataRepository {

    override suspend fun insert(searchData: SearchData) {
        return dao.insert(searchData)
    }

    override suspend fun update(searchData: SearchData) {
        return dao.update(searchData)
    }

    override suspend fun delete(searchData: SearchData) {
        return dao.delete(searchData)
    }

    override fun getDataList(): Flow<List<SearchData>> {
        return dao.getData()
    }

    override fun getDataList(limit: Int): Flow<List<SearchData>> {
        return dao.getData(limit)
    }

}