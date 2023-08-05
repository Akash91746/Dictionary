package com.example.dictionary.feature_searchDetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.feature_searchDetail.domain.repository.DictionaryAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    private val api: DictionaryAPI,
) : ViewModel() {

    fun init(word: String) {
        viewModelScope.launch {
            val response = api.getWordDetail(word)
            Timber.i(response.body())
        }
    }
}