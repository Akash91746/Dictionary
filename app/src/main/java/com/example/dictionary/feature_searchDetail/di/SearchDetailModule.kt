package com.example.dictionary.feature_searchDetail.di

import android.content.Context
import com.example.dictionary.R
import com.example.dictionary.feature_searchDetail.data.data_source.DictionaryAPI
import com.example.dictionary.feature_searchDetail.data.repository.DictionaryRepositoryImpl
import com.example.dictionary.feature_searchDetail.domain.repository.DictionaryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object SearchDetailModule {

    @Provides
    fun providesRetrofit(
        @ApplicationContext context: Context
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.dictionary_base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesDictionaryAPI(
        retrofit: Retrofit
    ): DictionaryAPI {
        return retrofit.create(DictionaryAPI::class.java)
    }

    @Provides
    fun providesWordDataRepository(
        dictionaryAPI: DictionaryAPI
    ): DictionaryRepository{
        return DictionaryRepositoryImpl(dictionaryAPI)
    }

}