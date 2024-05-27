package com.zwc.cocblacklisthelper.module.addblacklist

import com.zwc.baselibrary.base.BaseModel
import com.zwc.databaselibrary.DataManager
import com.zwc.databaselibrary.entity.User

class AddBlackListModel : BaseModel() {
    suspend fun loadData(): MutableList<User> {
        return DataManager.getUserManager().getAll()
    }
}