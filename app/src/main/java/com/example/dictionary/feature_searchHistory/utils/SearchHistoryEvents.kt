package com.example.dictionary.feature_searchHistory.utils

import com.example.dictionary.feature_searchHistory.domain.models.SearchData

sealed class SearchHistoryEvents{

    data class OnDeleteItem(val searchData: SearchData): SearchHistoryEvents()

    object OnUndoDelete: SearchHistoryEvents()

}
