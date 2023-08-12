package com.example.dictionary.common.utils

import com.example.dictionary.common.models.BaseModel

abstract class BaseRepositoryImpl <T: BaseModel> (
    private val dao:BaseDao<T>
) : BaseRepository<T> {
     override suspend fun insert(data: T) {
        return dao.insert(data)
    }

     override suspend fun update(data: T) {
        return dao.update(data)
    }

     override suspend fun delete(data: T) {
        return dao.delete(data)
    }
}