package com.example.dictionary.feature_searchHistory.domain.utils

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.annotation.Nullable

object TimeStampConverter {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    @Nullable
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return if (value != null) formatter.parse(value, OffsetDateTime::from) else null
    }

    @TypeConverter
    @JvmStatic
    @Nullable
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}