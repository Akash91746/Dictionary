package com.example.dictionary.feature_searchDetail.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["prefix","array","separator"], requireAll = false)
fun TextView.arrayText(prefix: String,value: List<String>,separator: String?) {
    text = String.format(prefix,value.joinToString(separator = separator ?: ", "))
}