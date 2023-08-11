package com.example.dictionary.feature_favorite.utils

import com.example.dictionary.feature_favorite.domain.models.FavoriteWord

data class FavoriteState(
    val list: List<FavoriteWord> = emptyList()
)
