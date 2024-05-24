package com.zwc.cocblacklisthelper.module.addblacklist.item

import androidx.databinding.ObservableField
import com.zwc.baselibrary.base.BaseViewModel
import com.zwc.baselibrary.base.MultiItemViewModel
import com.zwc.cocblacklisthelper.database.entity.User

/**
 * author:zuoweichen
 * DAte:2024-05-24 11:47
 * Description:描述
 */
class BlackListUserItemViewModel(viewModel: BaseViewModel<*>, val data: User) :
    MultiItemViewModel<BaseViewModel<*>>(viewModel) {
    var titleObservableField = ObservableField("")

    init {
        titleObservableField.set(data.text)
    }
}