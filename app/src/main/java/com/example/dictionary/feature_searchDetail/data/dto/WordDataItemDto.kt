package com.example.dictionary.feature_searchDetail.data.dto

data class WordDataItemDto(
    val meanings: List<MeaningDto>,
    val origin: String?,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val word: String
)