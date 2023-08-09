package com.example.dictionary.common.di

import android.content.Context
import com.example.dictionary.common.data.data_source.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesRoomDatabase(
        @ApplicationContext context: Context
    ): AppDatabase{
        return AppDatabase.getInstance(context)
    }

}