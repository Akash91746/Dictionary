package com.example.dictionary.feature_searchDetail.domain.models

data class WordDataItem(
    val meanings: List<Meaning>,
    val origin: String?,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val word: String
)