package com.zwc.databaselibrary

import com.zwc.databaselibrary.common.DataContext
import com.zwc.databaselibrary.entity.Formation

internal class FormationDataManagerImp private constructor() : FormationDataManager() {
    companion object {
        fun getInstance(): FormationDataManagerImp {
            return SingletonHolder.holder
        }
    }

    private object SingletonHolder {
        val holder = FormationDataManagerImp()
    }

    override suspend fun insertOrReplace(formation: Formation) {
        val formationDao = getAppDatabase().formationDao()
        formationDao.insert(formation)
    }

    override suspend fun getAll(): MutableList<Formation> {
        val formationDao = getAppDatabase().formationDao()
        return formationDao.queryAll()
    }

    override suspend fun getDataByType(type: Int): MutableList<Formation> {
        val formationDao = getAppDatabase().formationDao()
        return formationDao.getDataByTypeIn(type)
    }

    override suspend fun delete(formation: Formation) {
        val formationDao = getAppDatabase().formationDao()
        formationDao.delete(formation)
    }

    override suspend fun update(formation: Formation) {
        val formationDao = getAppDatabase().formationDao()
        formationDao.update(formation)
    }

    private fun getAppDatabase(): AppDatabase {
        return AppDatabase.getDatabase(DataContext.getInstance().getContext())
    }
}