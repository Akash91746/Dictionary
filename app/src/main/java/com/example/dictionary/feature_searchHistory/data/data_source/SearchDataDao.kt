package com.example.dictionary.feature_searchHistory.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dictionary.common.utils.BaseDao
import com.example.dictionary.feature_searchHistory.domain.models.SearchData
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDataDao : BaseDao<SearchData> {

    @Query("SELECT * FROM searchData ORDER BY createdAt DESC")
    fun getData(): Flow<List<SearchData>>

    @Query("Select * FROM searchData ORDER BY createdAt DESC LIMIT (:limit)")
    fun getData(limit: Int): Flow<List<SearchData>>
}