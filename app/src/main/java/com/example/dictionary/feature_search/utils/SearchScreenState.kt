package com.example.dictionary.feature_search.utils

import com.example.dictionary.feature_favorite.domain.models.FavoriteWord
import com.example.dictionary.feature_searchHistory.domain.models.SearchData

data class SearchScreenState(
    val search: String = "",
    val favoriteWords: List<FavoriteWord> = emptyList(),
    val recentSearchList: List<SearchData> = emptyList()
)
