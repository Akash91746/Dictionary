package com.example.dictionary.feature_searchDetail.utils

sealed class SearchDetailScreenEvent {

    data class InitData(val word: String)  : SearchDetailScreenEvent()

    object OnClickFavorite : SearchDetailScreenEvent()

}
