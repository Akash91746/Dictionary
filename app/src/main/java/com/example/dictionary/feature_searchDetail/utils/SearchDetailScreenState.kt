package com.example.dictionary.feature_searchDetail.utils

import com.example.dictionary.feature_searchDetail.domain.models.WordData

data class SearchDetailScreenState(
    val data: WordData? = null,
    val loading: Boolean = false,
    val error: String? = null,
    val favorite: Boolean = false
)
