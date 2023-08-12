package com.example.dictionary.feature_favorite.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.dictionary.common.data.data_source.AppDatabase
import com.example.dictionary.feature_favorite.domain.models.FavoriteWord
import com.example.dictionary.feature_favorite.domain.repository.FavoriteWordRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.google.common.truth.Truth.assertThat

@SmallTest
@HiltAndroidTest
class FavoriteWordRepositoryTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Inject
    lateinit var database: AppDatabase

    private lateinit var repository: FavoriteWordRepository

    @Before
    fun setUp(){
        hiltRule.inject()
        repository = FavoriteWordRepositoryImpl(database.favoriteWordDao)
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertData() = runBlocking{

        repository.insert("Test")

        val word = repository.getWord("Test").firstOrNull()

        assertThat(word).isNotNull()

    }

    @Test
    fun deleteData() = runBlocking {
        repository.insert("Test")

        repository.delete("Test")

        val word = repository.getWord("Test").firstOrNull()

        assertThat(word).isNull()
    }

    @Test
    fun updateData() = runBlocking {
        val word = FavoriteWord(id = 1, word = "Test")

        repository.insert(word)

        repository.update(word.copy(word="Test 2"))

        val list = repository.getFavoriteWords().firstOrNull()

        assertThat(list).isNotNull()

        assertThat(list).isNotEmpty()

        assertThat(list!![0].word).isEqualTo("Test 2")
    }

}