package com.example.dictionary.feature_searchDetail.domain.models

import com.example.dictionary.common.models.BaseModel

data class Meaning(
    val partOfSpeech: String,
    val definition: String,
    val example: String?,
    val synonyms: List<String>,
    val antonyms: List<String>
): BaseModel() {
    override val id: Int
        get() = partOfSpeech.hashCode()
}
