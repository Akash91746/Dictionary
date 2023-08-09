package com.example.dictionary.feature_searchDetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.feature_searchDetail.domain.repository.DictionaryAPI
import com.example.dictionary.feature_searchDetail.utils.SearchDetailScreenState
import com.example.dictionary.feature_searchHistory.domain.models.SearchData
import com.example.dictionary.feature_searchHistory.domain.repository.SearchDataRepository
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
    private val searchDataRepository: SearchDataRepository,
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
            } else if (data.isNullOrEmpty()) {
                errorMessage = "No data found for $word"
            }

            val wordData = if (data != null) data[0] else null

            if (wordData != null) {
                val title = wordData.word.substring(0,1).uppercase() + wordData.word.substring(1)

                searchDataRepository.insert(
                    SearchData(search = title)
                )
            }

            _state.value = _state.value.copy(
                data = wordData,
                error = errorMessage,
                loading = false
            )
        }
    }

    private fun setLoading(loading: Boolean) {
        _state.value = _state.value.copy(loading = loading)
    }
}