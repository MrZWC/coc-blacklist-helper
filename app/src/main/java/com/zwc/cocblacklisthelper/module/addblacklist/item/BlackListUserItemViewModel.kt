package com.zwc.cocblacklisthelper.module.addblacklist.item

import androidx.databinding.ObservableField
import com.zwc.baselibrary.base.BaseViewModel
import com.zwc.baselibrary.base.MultiItemViewModel
import com.zwc.baselibrary.binding.command.BindingAction
import com.zwc.baselibrary.binding.command.BindingCommand
import com.zwc.cocblacklisthelper.database.entity.User

/**
 * author:zuoweichen
 * DAte:2024-05-24 11:47
 * Description:描述
 */
class BlackListUserItemViewModel(
    viewModel: BaseViewModel<*>,
    val data: User,
    onLongCLick: (item: BlackListUserItemViewModel) -> Unit
) :
    MultiItemViewModel<BaseViewModel<*>>(viewModel) {

    var titleObservableField = ObservableField("")

    init {
        titleObservableField.set(data.text)
    }

    var onLongClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        onLongCLick(this)
    })

    fun changeData(text: String) {
        titleObservableField.set(text)
    }

    override fun equals(other: Any?): Boolean {
        if (other is BlackListUserItemViewModel) {
            return this.data.userId == other.data.userId
        }
        return super.equals(other)

    }
}