package com.example.dictionary.feature_favorite.domain.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(indices = [Index(value = ["word"], unique = true)])
data class FavoriteWord(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val word: String,

    val createdAt: OffsetDateTime = OffsetDateTime.now(),
)
