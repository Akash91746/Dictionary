package com.example.dictionary.feature_search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.feature_favorite.domain.models.FavoriteWord
import com.example.dictionary.feature_favorite.domain.repository.FavoriteWordRepository
import com.example.dictionary.feature_search.utils.SearchScreenState
import com.example.dictionary.feature_searchHistory.domain.models.SearchData
import com.example.dictionary.feature_searchHistory.domain.repository.SearchDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val favoriteRepository: FavoriteWordRepository,
    private val searchDataRepository: SearchDataRepository,
) : ViewModel() {


    private val _state = MutableStateFlow(SearchScreenState())

    val state: StateFlow<SearchScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            favoriteRepository.getFavoriteWords(5)
                .combine(searchDataRepository.getDataList(5)) {
                        favoriteWords: List<FavoriteWord>, recentSearch: List<SearchData> ->

                    Data(
                        favoriteList = favoriteWords,
                        recentSearch = recentSearch
                    )
                }.collectLatest {
                    _state.value = _state.value.copy(
                        recentSearchList = it.recentSearch,
                        favoriteWords = it.favoriteList
                    )
                }
        }
    }

    data class Data (
        val favoriteList: List<FavoriteWord>,
        val recentSearch: List<SearchData>
    )

}