package com.zwc.cocblacklisthelper.module.addblacklist.item

import androidx.databinding.ObservableField
import com.zwc.baselibrary.base.MultiItemViewModel
import com.zwc.cocblacklisthelper.database.entity.User
import com.zwc.cocblacklisthelper.module.addblacklist.AddBlackListViewModel

/**
 * author:zuoweichen
 * DAte:2024-05-24 11:47
 * Description:描述
 */
class BlackListUserItemViewModel(viewModel: AddBlackListViewModel, val data: User) :
    MultiItemViewModel<AddBlackListViewModel>(viewModel) {
    var titleObservableField = ObservableField("")

    init {
        titleObservableField.set(data.text)
    }
}