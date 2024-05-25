package com.zwc.cocblacklisthelper.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zwc.cocblacklisthelper.database.entity.User

/**
 * author:zuoweichen
 * DAte:2024-05-24 11:15
 * Description:描述
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: MutableList<User>)

    @Update
    suspend fun update(obj: User)

    @Update
    suspend fun update(list: MutableList<User>)

    @Delete
    suspend fun delete(obj: User)

    @Delete
    suspend fun delete(list: MutableList<User>)

    /**
     * 更具id查询黑名单用户
     * @param userId String
     * @return User?
     */
    @Query("SELECT * FROM user WHERE userId= :userId")
    suspend fun queryById(userId: String): User?

    /**
     * 关键字查询黑名单用户
     * @param key String
     * @return MutableList<User>
     */
    @Query("SELECT * FROM user WHERE  text like '%' || :key || '%'")
    suspend fun queryByKeyWord(key: String): MutableList<User>

    @Query("SELECT * FROM user")
    suspend fun queryAll(): MutableList<User>

    @Query("SELECT COUNT(*) FROM user")
    suspend fun getSize(): Int
}