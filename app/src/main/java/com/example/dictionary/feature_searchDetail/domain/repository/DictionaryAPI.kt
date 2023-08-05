package com.example.dictionary.feature_searchDetail.domain.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryAPI {

    @GET("entries/en/{word}")
    suspend fun getWordDetail(@Path("word") word: String) : Response<String>

}