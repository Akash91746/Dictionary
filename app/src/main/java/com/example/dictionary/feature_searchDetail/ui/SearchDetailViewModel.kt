package com.example.dictionary.feature_searchDetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.feature_favorite.domain.repository.FavoriteWordRepository
import com.example.dictionary.feature_searchDetail.data.data_source.DictionaryAPI
import com.example.dictionary.feature_searchDetail.domain.repository.DictionaryRepository
import com.example.dictionary.feature_searchDetail.utils.SearchDetailScreenEvent
import com.example.dictionary.feature_searchDetail.utils.SearchDetailScreenState
import com.example.dictionary.feature_searchHistory.domain.models.SearchData
import com.example.dictionary.feature_searchHistory.domain.repository.SearchDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository,
    private val searchDataRepository: SearchDataRepository,
    private val favoriteWordRepository: FavoriteWordRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchDetailScreenState())
    val state: StateFlow<SearchDetailScreenState>
        get() = _state

    private var job: Job? = null
    private var favoriteFetchJob: Job? = null

    fun onEvent(event: SearchDetailScreenEvent) {
        when (event) {
            is SearchDetailScreenEvent.InitData -> {
                init(event.word)
            }

            is SearchDetailScreenEvent.OnClickFavorite -> {
                state.value.data?.let {
                    toggleFavoriteStatus(it.word, state.value.favorite)
                }
            }
        }
    }

    private fun init(word: String) {
        job?.cancel()

        job = viewModelScope.launch {
            setLoading(true)

            val result = dictionaryRepository.getWordDetail(word)
            val resultData = result.getOrNull()

            val wordData = if (!resultData.isNullOrEmpty()) resultData[0] else null
            val error =
                if (result.exceptionOrNull() != null) result.exceptionOrNull()!!.message else null

            initFavoriteFetch(word)

            _state.value = _state.value.copy(
                data = wordData,
                error = error,
                loading = false
            )

            if (wordData != null) {
                insertSearchData(word)
            }
        }
    }

    private fun initFavoriteFetch(word: String) {
        favoriteFetchJob?.cancel()

        favoriteFetchJob = viewModelScope.launch {
            favoriteWordRepository.getWord(word).collectLatest {
                _state.value = _state.value.copy(
                    favorite = it != null
                )
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        _state.value = _state.value.copy(loading = loading)
    }

    private fun insertSearchData(word: String) = viewModelScope.launch {
        val title = word.substring(0, 1).uppercase() + word.substring(1)

        searchDataRepository.insert(
            SearchData(search = title)
        )
    }

    private fun toggleFavoriteStatus(word: String, isFavorite: Boolean) {
        if (isFavorite) {
            deleteFavorite(word)
        } else {
            insertFavorite(word)
        }
    }

    private fun insertFavorite(word: String) = viewModelScope.launch {
        favoriteWordRepository.insert(word)
    }

    private fun deleteFavorite(word: String) = viewModelScope.launch {
        favoriteWordRepository.delete(word)
    }
}