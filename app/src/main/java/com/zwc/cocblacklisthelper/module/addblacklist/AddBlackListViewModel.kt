package com.zwc.cocblacklisthelper.module.addblacklist

import android.app.Application
import com.zwc.baselibrary.base.BaseViewModel

class AddBlackListViewModel : BaseViewModel<AddBlackListModel> {

    constructor(application: Application) : super(application)
    constructor(application: Application, model: AddBlackListModel?) : super(application, model)
}