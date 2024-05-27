package com.zwc.databaselibrary

import android.app.Application
import com.zwc.databaselibrary.common.DataContext


/**
 * author:zuoweichen
 * DAte:2024-05-24 11:25
 * Description:数据管理
 */
interface DataManager {
    companion object {
        fun getUserManager(): UserDataManager {
            return UserDataManagerImp.getInstance()
        }

        fun init(application: Application) {
            DataContext.getInstance().init(application)
        }
    }
}