package com.zwc.cocblacklisthelper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zwc.cocblacklisthelper.database.dao.UserDao
import com.zwc.cocblacklisthelper.database.entity.User

/**
 * author:zuoweichen
 * DAte:2023-08-07 9:56
 * Description:数据库管理
 */
@Database(
    entities = [User::class], exportSchema = true, version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        /**
         * 数据库名称
         */
        private const val DATABASE_NAME = "coc_black_list.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val dbName = DATABASE_NAME
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, dbName
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}