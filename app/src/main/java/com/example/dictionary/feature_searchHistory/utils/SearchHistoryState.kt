package com.example.dictionary.feature_searchHistory.utils

import com.example.dictionary.feature_searchHistory.domain.models.SearchData

data class SearchHistoryState(
    val searchDataList: List<SearchData> = emptyList()
)
