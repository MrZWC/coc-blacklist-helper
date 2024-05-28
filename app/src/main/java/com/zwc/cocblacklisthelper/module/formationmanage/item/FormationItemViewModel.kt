package com.zwc.cocblacklisthelper.module.formationmanage.item

import androidx.databinding.ObservableField
import com.zwc.baselibrary.base.BaseViewModel
import com.zwc.baselibrary.base.MultiItemViewModel
import com.zwc.baselibrary.binding.command.BindingAction
import com.zwc.baselibrary.binding.command.BindingCommand
import com.zwc.databaselibrary.entity.Formation

class FormationItemViewModel(
    viewModel: BaseViewModel<*>,
    val data: Formation,
    onLongCLick: (item: FormationItemViewModel) -> Unit
) :
    MultiItemViewModel<BaseViewModel<*>>(viewModel) {
    var imageObservableFile = ObservableField(data.imageFilePath)
    var onLongClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        onLongCLick(this)
    })
}