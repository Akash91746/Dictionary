package com.example.dictionary.feature_searchDetail.domain.models

data class Definition(
    val antonyms: List<String>,
    val definition: String,
    val example: String?,
    val synonyms: List<String>
)