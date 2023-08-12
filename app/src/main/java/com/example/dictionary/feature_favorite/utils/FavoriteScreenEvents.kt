package com.example.dictionary.feature_favorite.utils

import com.example.dictionary.feature_favorite.domain.models.FavoriteWord

sealed class FavoriteScreenEvents {
    data class OnDelete(val item: FavoriteWord) : FavoriteScreenEvents()

    object OnUndoDelete : FavoriteScreenEvents()

}
