package com.zwc.databaselibrary.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * author:zuoweichen
 * DAte:2023-08-07 9:33
 * Description:room dao 基类
 */
interface BaseDAO<T> {
    @Insert
    suspend fun insert(obj: T)

    @Insert
    suspend fun insert(list: MutableList<T>)

    @Update
    suspend fun update(obj: T)

    @Update
    suspend fun update(list: MutableList<T>)

    @Delete
    suspend fun delete(obj: T)

    @Delete
    suspend fun delete(list: MutableList<T>)
}