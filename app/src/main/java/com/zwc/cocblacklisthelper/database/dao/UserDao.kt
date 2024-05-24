package com.zwc.cocblacklisthelper.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.zwc.cocblacklisthelper.database.entity.User

/**
 * author:zuoweichen
 * DAte:2024-05-24 11:15
 * Description:描述
 */
@Dao
interface UserDao : BaseDAO<User> {
    /**
     * 更具id查询黑名单用户
     * @param userId String
     * @return User?
     */
    /*@Query("SELECT * FROM user WHERE userId= :userId")
    suspend fun queryById(userId: String): User?
*/
    /**
     * 关键字查询黑名单用户
     * @param key String
     * @return MutableList<User>
     */
    @Query("SELECT * FROM user WHERE  text like '%' || :key || '%'")
    suspend fun queryByKeyWord( key: String): MutableList<User>
}