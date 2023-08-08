package com.example.dictionary.feature_searchDetail.domain.models

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)