package com.zwc.databaselibrary

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zwc.databaselibrary.dao.FormationDao
import com.zwc.databaselibrary.dao.UserDao
import com.zwc.databaselibrary.entity.Formation
import com.zwc.databaselibrary.entity.User

/**
 * author:zuoweichen
 * DAte:2023-08-07 9:56
 * Description:数据库管理
 */
@Database(
    entities = [User::class, Formation::class], exportSchema = true, version = 3,
    autoMigrations = [AutoMigration(from = 2, to = 3)]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun formationDao(): FormationDao

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