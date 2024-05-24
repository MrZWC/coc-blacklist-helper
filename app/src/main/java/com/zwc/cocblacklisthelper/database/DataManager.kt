package com.zwc.cocblacklisthelper.database

import com.zwc.cocblacklisthelper.database.entity.User
import com.zwc.cocblacklisthelper.module.MyApplication

/**
 * author:zuoweichen
 * DAte:2024-05-24 11:25
 * Description:数据管理
 */
class DataManager() {
    companion object {
        fun getInstance(): DataManager {
            return SingletonHolder.holder
        }
    }

    private object SingletonHolder {
        val holder = DataManager()
    }

    suspend fun insert(list: MutableList<User>) {
        val userDao = getAppDatabase().userDao()
        userDao.insert(list)
    }

    suspend fun insert(user: User) {
        val userDao = getAppDatabase().userDao()
        userDao.insert(user)
    }

    suspend fun queryByKeyWord(key: String?): MutableList<User> {
        if (key.isNullOrEmpty()) {
            return mutableListOf()
        }
        return getAppDatabase().userDao().queryByKeyWord(key)
    }

    private fun getAppDatabase(): AppDatabase {
        return AppDatabase.getDatabase(MyApplication.getApplication())
    }
}