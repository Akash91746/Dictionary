package com.example.dictionary.feature_searchHistory.di

import com.example.dictionary.common.data.data_source.AppDatabase
import com.example.dictionary.feature_searchHistory.data.repository.SearchDataRepositoryImpl
import com.example.dictionary.feature_searchHistory.domain.repository.SearchDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object SearchHistoryModule {

    @Provides
    fun providesSearchHistoryRepository(
        database: AppDatabase
    ): SearchDataRepository{
        return SearchDataRepositoryImpl(database.searchDataDao)
    }

}