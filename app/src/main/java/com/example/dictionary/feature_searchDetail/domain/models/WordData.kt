package com.example.dictionary.feature_searchDetail.domain.models

data class WordData(
    val word: String,
    val phonetic: Phonetic,
    val origin: String?,
    val meanings: List<Meaning>
)
