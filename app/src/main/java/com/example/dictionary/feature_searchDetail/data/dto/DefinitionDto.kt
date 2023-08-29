package com.example.dictionary.feature_searchDetail.data.dto

data class DefinitionDto(
    val antonyms: List<String>,
    val definition: String,
    val example: String?,
    val synonyms: List<String>
)