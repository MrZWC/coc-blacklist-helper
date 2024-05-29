package com.zwc.cocblacklisthelper.module.formationmanage

import com.zwc.baselibrary.base.BaseModel
import com.zwc.databaselibrary.DataManager
import com.zwc.databaselibrary.entity.Formation

class FormationManageModel : BaseModel() {
    suspend fun loadData(type: Int = -1): MutableList<Formation> {
        if (type < 0) {
            return DataManager.getFormationManager().getAll()
        }
        return DataManager.getFormationManager().getDataByType(type)
    }
}