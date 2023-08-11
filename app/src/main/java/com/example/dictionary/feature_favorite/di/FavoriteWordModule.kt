package com.example.dictionary.feature_favorite.di

import com.example.dictionary.common.data.data_source.AppDatabase
import com.example.dictionary.feature_favorite.data.repository.FavoriteWordRepositoryImpl
import com.example.dictionary.feature_favorite.domain.repository.FavoriteWordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object FavoriteWordModule {

    @Provides
    fun providesFavoriteWordRepository(
        database: AppDatabase
    ): FavoriteWordRepository{
        return FavoriteWordRepositoryImpl(database.favoriteWordDao)
    }

}