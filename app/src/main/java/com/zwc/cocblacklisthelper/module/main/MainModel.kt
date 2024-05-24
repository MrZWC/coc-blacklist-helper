package com.zwc.cocblacklisthelper.module.main

import com.zwc.baselibrary.base.BaseModel
import com.zwc.cocblacklisthelper.database.DataManager
import com.zwc.cocblacklisthelper.database.entity.User

/**
 * author:zuoweichen
 * DAte:2024-05-24 17:45
 * Description:描述
 */
class MainModel : BaseModel() {
    suspend fun search(key: String): MutableList<User> {
        return DataManager.getInstance().queryByKeyWord(key)
    }
}