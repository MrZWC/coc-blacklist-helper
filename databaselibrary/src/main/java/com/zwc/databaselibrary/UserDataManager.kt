package com.zwc.databaselibrary

import com.zwc.databaselibrary.entity.User

/**
 * author:zuoweichen
 * DAte:2024-05-27 17:48
 * Description:描述
 */
abstract class UserDataManager {
    abstract suspend fun insertOrReplace(list: MutableList<User>)


    abstract suspend fun insertOrReplace(user: User)

    abstract suspend fun getAll(): MutableList<User>

    abstract suspend fun delete(user: User)
    abstract suspend fun deleteAll()

    abstract suspend fun update(user: User)
    abstract suspend fun getSize(): Int

    abstract suspend fun queryByKeyWord(key: String?): MutableList<User>
}