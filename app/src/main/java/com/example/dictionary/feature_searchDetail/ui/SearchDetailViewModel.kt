package com.example.dictionary.feature_searchDetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.feature_searchDetail.domain.repository.DictionaryAPI
import com.example.dictionary.feature_searchDetail.utils.SearchDetailScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    private val api: DictionaryAPI,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchDetailScreenState())
    val state: StateFlow<SearchDetailScreenState>
        get() = _state

    private var job: Job? = null
    fun init(word: String) {
        job?.cancel()

        job = viewModelScope.launch {
            setLoading(true)

            val response = api.getWordDetail(word)
            val data = response.body()
            var errorMessage: String? = null

            if (!response.isSuccessful) {
                errorMessage = "Error fetching data"
            } else if (data == null) {
                errorMessage = "No data found for $word"
            }

            Timber.d("Data is $data")

            _state.value = _state.value.copy(
                data = data,
                error = errorMessage,
                loading = false
            )
        }
    }

    private fun setLoading(loading: Boolean) {
        _state.value = _state.value.copy(loading = loading)
    }
}