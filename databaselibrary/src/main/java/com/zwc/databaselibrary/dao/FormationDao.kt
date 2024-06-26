package com.zwc.databaselibrary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zwc.databaselibrary.entity.Formation

/**
 * author:zuoweichen
 * DAte:2024-05-27 17:15
 * Description:描述
 */
@Dao
interface FormationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: Formation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: MutableList<Formation>)

    @Update
    suspend fun update(obj: Formation)

    @Update
    suspend fun update(list: MutableList<Formation>)

    @Delete
    suspend fun delete(obj: Formation)

    @Delete
    suspend fun delete(list: MutableList<Formation>)

    @Query("SELECT * FROM formation order by dataTime desc")
    suspend fun queryAll(): MutableList<Formation>

    @Query("SELECT * FROM formation WHERE type=:type order by dataTime desc")
    suspend fun getDataByTypeIn(type: Int): MutableList<Formation>
}