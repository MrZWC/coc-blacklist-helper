package com.zwc.databaselibrary

import com.zwc.databaselibrary.entity.Formation

abstract class FormationDataManager {
    abstract suspend fun insertOrReplace(formation: Formation)
    abstract suspend fun getAll(): MutableList<Formation>
    abstract suspend fun delete(formation: Formation)
    abstract suspend fun update(formation: Formation)

}