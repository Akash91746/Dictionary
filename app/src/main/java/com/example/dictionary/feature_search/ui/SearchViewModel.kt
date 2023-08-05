package com.example.dictionary.feature_search.ui

import androidx.lifecycle.ViewModel
import com.example.dictionary.feature_search.utils.SearchScreenEvents
import com.example.dictionary.feature_search.utils.SearchScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel: ViewModel() {


    private val _state = MutableStateFlow(SearchScreenState())

    val state: StateFlow<SearchScreenState>
        get() = _state

    fun onEvent(event: SearchScreenEvents){
        when(event){
            is SearchScreenEvents.OnChangeSearchField -> {
                _state.value = _state.value.copy(search = event.value)
            }
        }
    }

}