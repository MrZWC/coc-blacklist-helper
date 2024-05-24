package com.zwc.cocblacklisthelper.module

import android.app.Application
import com.socks.library.KLog
import com.zwc.cocblacklisthelper.BuildConfig
import timber.log.Timber

/**
 * author:zuoweichen
 * DAte:2024-05-24 9:36
 * Description:描述
 */
class MyApplication : Application() {

    companion object {
        private var instance: MyApplication? = null
        fun getApplication(): MyApplication {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Klog初始化
        KLog.init(BuildConfig.DEBUG)
        Timber.plant(Timber.DebugTree())
    }
}