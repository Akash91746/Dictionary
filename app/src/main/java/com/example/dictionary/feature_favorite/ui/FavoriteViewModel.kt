package com.example.dictionary.feature_favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.feature_favorite.domain.models.FavoriteWord
import com.example.dictionary.feature_favorite.domain.repository.FavoriteWordRepository
import com.example.dictionary.feature_favorite.utils.FavoriteScreenEvents
import com.example.dictionary.feature_favorite.utils.FavoriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteWordRepository
): ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())

    val state : StateFlow<FavoriteState>
        get() = _state

    private var fetchJob: Job? = null

    private var lastDeletedItem: FavoriteWord? = null

    init {
        init()
    }

    fun onEvent(event: FavoriteScreenEvents){
        when(event) {
            is FavoriteScreenEvents.OnDelete -> {
                deleteFavoriteWord(event.item)
                lastDeletedItem = event.item
            }
            is FavoriteScreenEvents.OnUndoDelete -> {
                lastDeletedItem?.let {
                    insertFavoriteWord(it)
                }
            }
        }
    }

    private fun init(){
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            repository.getFavoriteWords().collectLatest {
                _state.value = _state.value.copy(list = it)
            }
        }
    }

    private fun deleteFavoriteWord(word: FavoriteWord) = viewModelScope.launch {
        repository.delete(word)
    }

    private fun insertFavoriteWord(word: FavoriteWord) = viewModelScope.launch {
        repository.insert(word)
    }

}