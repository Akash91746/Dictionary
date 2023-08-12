package com.example.dictionary.common.utils

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.dictionary.common.models.BaseModel

@Dao
interface BaseDao<T : BaseModel> {
    @Insert
    suspend fun insert(data: T)
    @Update
    suspend fun update(data: T)
    @Delete
    suspend fun delete(data: T)
}