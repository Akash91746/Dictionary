package com.example.dictionary.feature_searchHistory.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionary.common.models.BaseModel
import java.time.OffsetDateTime

@Entity(tableName = "searchData")
data class SearchData(

    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,

    val search: String,

    val createdAt: OffsetDateTime = OffsetDateTime.now(),
): BaseModel()
