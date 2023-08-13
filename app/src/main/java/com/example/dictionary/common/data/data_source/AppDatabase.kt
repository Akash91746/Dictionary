package com.example.dictionary.common.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionary.R
import com.example.dictionary.feature_favorite.data.data_source.FavoriteWordDao
import com.example.dictionary.feature_favorite.domain.models.FavoriteWord
import com.example.dictionary.feature_searchHistory.data.data_source.SearchDataDao
import com.example.dictionary.feature_searchHistory.domain.models.SearchData
import com.example.dictionary.feature_searchHistory.domain.utils.TimeStampConverter

@Database(
    entities = [SearchData::class,FavoriteWord::class],
    version = 3
)
@TypeConverters(value = [TimeStampConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract val searchDataDao: SearchDataDao
    abstract val favoriteWordDao: FavoriteWordDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    context.getString(R.string.app_local_database)
                )
                    .build()

                INSTANCE = instance
            }

            return instance
        }

        fun getTestDatabase(context: Context): AppDatabase {
            return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        }

    }

}