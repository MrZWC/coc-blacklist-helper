package com.zwc.cocblacklisthelper.module.addblacklist

import com.zwc.baselibrary.base.BaseModel
import com.zwc.cocblacklisthelper.database.DataManager
import com.zwc.cocblacklisthelper.database.entity.User

class AddBlackListModel : BaseModel() {
    suspend fun loadData(): MutableList<User> {
        return DataManager.getInstance().getAll()
    }
}