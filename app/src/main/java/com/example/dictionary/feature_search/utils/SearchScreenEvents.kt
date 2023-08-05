package com.example.dictionary.feature_search.utils

sealed class SearchScreenEvents {

    class OnChangeSearchField(val value: String) : SearchScreenEvents()

}
