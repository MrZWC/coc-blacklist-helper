package com.zwc.cocblacklisthelper.module.main

import com.zwc.baselibrary.base.BaseModel
import com.zwc.databaselibrary.DataManager
import com.zwc.databaselibrary.entity.User

/**
 * author:zuoweichen
 * DAte:2024-05-24 17:45
 * Description:描述
 */
class MainModel : BaseModel() {
    suspend fun search(key: String): MutableList<User> {
        return DataManager.getUserManager().queryByKeyWord(key)
    }
}