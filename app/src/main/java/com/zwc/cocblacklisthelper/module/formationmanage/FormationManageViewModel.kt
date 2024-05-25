package com.zwc.cocblacklisthelper.module.formationmanage

import android.app.Application
import com.zwc.baselibrary.base.BaseViewModel
import com.zwc.baselibrary.binding.command.BindingAction
import com.zwc.baselibrary.binding.command.BindingCommand
import com.zwc.baselibrary.bus.event.SingleLiveEvent
import com.zwc.cocblacklisthelper.module.addblacklist.item.BlackListUserItemViewModel

class FormationManageViewModel(application: Application) :
    BaseViewModel<FormationManageModel>(application, FormationManageModel()) {

    //封装一个界面发生改变的观察者
    var uc: UIChangeObservable = UIChangeObservable()

    inner class UIChangeObservable {
        //显示编辑地址弹窗
        var showAddDialogObservable: SingleLiveEvent<BlackListUserItemViewModel>

        init {
            showAddDialogObservable = SingleLiveEvent()
        }
    }

    var addOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        uc.showAddDialogObservable.call()
    })
}