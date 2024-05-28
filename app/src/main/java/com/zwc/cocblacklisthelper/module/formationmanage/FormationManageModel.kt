package com.zwc.cocblacklisthelper.module.formationmanage

import com.zwc.baselibrary.base.BaseModel
import com.zwc.databaselibrary.DataManager
import com.zwc.databaselibrary.entity.Formation

class FormationManageModel : BaseModel() {
    suspend fun loadData(): MutableList<Formation> {
        return DataManager.getFormationManager().getAll()
    }
}