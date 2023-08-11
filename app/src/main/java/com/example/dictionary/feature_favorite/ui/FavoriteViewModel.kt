package com.example.dictionary.feature_favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.feature_favorite.domain.repository.FavoriteWordRepository
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
    private val favoriteWordRepository: FavoriteWordRepository
): ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())

    val state : StateFlow<FavoriteState>
        get() = _state

    private var fetchJob: Job? = null

    init {
        init()
    }

    private fun init(){
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            favoriteWordRepository.getFavoriteWords().collectLatest {
                _state.value = _state.value.copy(list = it)
            }
        }
    }

}