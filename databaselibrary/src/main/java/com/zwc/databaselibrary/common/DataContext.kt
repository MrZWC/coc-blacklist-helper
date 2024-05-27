package com.zwc.databaselibrary.common

import android.content.Context
import com.zwc.databaselibrary.UserDataManagerImp
import java.lang.Exception

/**
 * author:zuoweichen
 * DAte:2024-05-27 17:51
 * Description:描述
 */
class DataContext {
    companion object {
        fun getInstance(): DataContext {
            return SingletonHolder.holder
        }
    }

    private var context: Context? = null
    fun init(context: Context) {
        this.context = context
    }

    fun getContext(): Context {
        if (context == null) {
            throw Exception("DataContext is not init")
        }
        return context!!
    }

    private object SingletonHolder {
        val holder = DataContext()
    }
}