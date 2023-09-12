package com.example.dictionary.feature_searchDetail.data.repository

import com.example.dictionary.feature_searchDetail.data.data_source.DictionaryAPI
import com.example.dictionary.feature_searchDetail.data.dto.MeaningDto
import com.example.dictionary.feature_searchDetail.data.dto.WordDataItemDto
import com.example.dictionary.feature_searchDetail.domain.models.Meaning
import com.example.dictionary.feature_searchDetail.domain.models.Phonetic
import com.example.dictionary.feature_searchDetail.domain.models.WordData
import com.example.dictionary.feature_searchDetail.domain.repository.DictionaryRepository

class DictionaryRepositoryImpl(
    private val api: DictionaryAPI
): DictionaryRepository {

    override suspend fun getWordDetail(word: String): Result<WordData> {

        val result = api.getWordDetail(word)

        if(!result.isSuccessful){
            return Result.failure(Throwable("Error Fetching data"))
        }

        val data = result.body() ?: return Result.failure(Throwable("No data found"))

        val dataItemDto = data[0]

        val phonetic = getPhonetic(dataItemDto)

        val meanings = dataItemDto.meanings.map {
            return@map getMeaning(it)
        }

        val wordData = WordData(
            word = word,
            origin = dataItemDto.origin,
            phonetic = phonetic,
            meanings = meanings
        )

        return Result.success(wordData)
    }

    private fun getMeaning(meaningDto: MeaningDto): Meaning {
        val synonyms = emptyList<String>()
        val antonyms = emptyList<String>()
        var definition = ""
        var example: String? = null

        meaningDto.definitions.forEach {dto ->

            if(definition.isNotEmpty()) {
                definition += "\n${dto.definition}"
            }else {
                definition = dto.definition
            }

            if(!dto.example.isNullOrEmpty()) {
                if(example == null){
                    example = ""
                }
                example += if(!example.isNullOrEmpty()) {
                    "\n${dto.example}"
                }else {
                    dto.example
                }
            }

            synonyms.plus(dto.synonyms)
            antonyms.plus(dto.antonyms)
        }

        return Meaning(
            partOfSpeech = meaningDto.partOfSpeech,
            definition,
            example,
            synonyms = synonyms,
            antonyms = antonyms
        )
    }

    private fun getPhonetic(dataItemDto: WordDataItemDto): Phonetic {
        var phonetic = Phonetic(value = dataItemDto.phonetic)

        dataItemDto.phonetics.forEach { ph ->

            if(!ph.audio.isNullOrEmpty()){
                phonetic = Phonetic(value = ph.text, audio = ph.audio)
                return phonetic
            }
        }

        return phonetic
    }

}