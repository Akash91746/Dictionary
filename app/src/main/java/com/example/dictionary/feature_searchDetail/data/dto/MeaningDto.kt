package com.example.dictionary.feature_searchDetail.data.dto

data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String
)