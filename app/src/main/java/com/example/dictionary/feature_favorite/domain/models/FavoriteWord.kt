package com.example.dictionary.feature_favorite.domain.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.dictionary.common.models.BaseModel
import java.time.OffsetDateTime

@Entity(indices = [Index(value = ["word"], unique = true)])
data class FavoriteWord(

    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,

    val word: String,

    val createdAt: OffsetDateTime = OffsetDateTime.now(),
): BaseModel() {

    fun formattedWord(): String{
        return this.word.substring(0,1) + this.word.substring(1).lowercase()
    }

}
