package com.example.dictionary.feature_searchDetail.utils

import com.example.dictionary.feature_searchDetail.domain.models.WordDataItem

data class SearchDetailScreenState(
    val data: WordDataItem? = null,
    val loading: Boolean = false,
    val error: String? = null
)
