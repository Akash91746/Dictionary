package com.example.dictionary.common.utils

import com.example.dictionary.feature_favorite.domain.models.FavoriteWord

interface BaseRepository<T>  {

    suspend fun insert(data: T)

    suspend fun update(data: T)

    suspend fun delete(data: T)

}