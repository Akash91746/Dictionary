package com.example.dictionary.feature_searchHistory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.feature_searchHistory.domain.repository.SearchDataRepository
import com.example.dictionary.feature_searchHistory.utils.SearchHistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val repository: SearchDataRepository
): ViewModel() {

    private val _state = MutableStateFlow(SearchHistoryState())

    val state : StateFlow<SearchHistoryState>
        get() = _state

    private var getJob: Job? = null

    init {
        getJob?.cancel()

        getJob = viewModelScope.launch {
            repository.getDataList().collectLatest {
                _state.value = _state.value.copy(searchDataList = it)
            }
        }
    }

}