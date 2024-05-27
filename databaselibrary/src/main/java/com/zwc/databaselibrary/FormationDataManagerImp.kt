package com.zwc.databaselibrary

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
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): MutableList<Formation> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(formation: Formation) {
        TODO("Not yet implemented")
    }

    override suspend fun update(formation: Formation) {
        TODO("Not yet implemented")
    }
}