package com.zwc.databaselibrary

import com.zwc.databaselibrary.common.DataContext
import com.zwc.databaselibrary.entity.User


/**
 * author:zuoweichen
 * DAte:2024-05-27 17:29
 * Description:描述
 */
internal class UserDataManagerImp private constructor() : UserDataManager() {
    companion object {
        fun getInstance(): UserDataManagerImp {
            return SingletonHolder.holder
        }
    }

    private object SingletonHolder {
        val holder = UserDataManagerImp()
    }

    override suspend fun insertOrReplace(list: MutableList<User>) {
        val userDao = getAppDatabase().userDao()
        userDao.insert(list)
    }

    override suspend fun insertOrReplace(user: User) {
        insertOrReplace(mutableListOf(user))
    }

    override suspend fun getAll(): MutableList<User> {
        val userDao = getAppDatabase().userDao()
        return userDao.queryAll()
    }

    override suspend fun delete(user: User) {
        val userDao = getAppDatabase().userDao()
        userDao.delete(user)
    }

    override suspend fun update(user: User) {
        val userDao = getAppDatabase().userDao()
        userDao.update(user)
    }

    override suspend fun getSize(): Int {
        val userDao = getAppDatabase().userDao()
        return userDao.getSize()
    }

    override suspend fun queryByKeyWord(key: String?): MutableList<User> {
        if (key.isNullOrEmpty()) {
            return mutableListOf()
        }
        return getAppDatabase().userDao().queryByKeyWord(key)
    }

    private fun getAppDatabase(): AppDatabase {
        return AppDatabase.getDatabase(DataContext.getInstance().getContext())
    }
}