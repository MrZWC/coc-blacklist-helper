package com.zwc.cocblacklisthelper.module.main

import android.app.Application
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableInt
import androidx.lifecycle.viewModelScope
import com.socks.library.KLog
import com.zwc.baselibrary.base.BaseViewModel
import com.zwc.baselibrary.bus.event.SingleLiveEvent
import com.zwc.cocblacklisthelper.BR
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.database.DataManager
import com.zwc.cocblacklisthelper.database.entity.User
import com.zwc.cocblacklisthelper.module.addblacklist.item.BlackListUserItemViewModel
import com.zwc.cocblacklisthelper.widget.SearchEditText
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import timber.log.Timber

/**
 * author:zuoweichen
 * DAte:2024-05-24 17:45
 * Description:描述
 */
class MainViewModel(application: Application) : BaseViewModel<MainModel>(application, MainModel()) {
    private val TAG = this.javaClass.name
    var observableList = ObservableArrayList<BlackListUserItemViewModel>()
    var tipsVisibilityObservable = ObservableInt(View.GONE)

    //封装一个界面发生改变的观察者
    var uc: UIChangeObservable = UIChangeObservable()

    inner class UIChangeObservable {
        //显示编辑地址弹窗
        var showEditDialogObservable: SingleLiveEvent<BlackListUserItemViewModel>

        init {
            showEditDialogObservable = SingleLiveEvent()
        }
    }

    init {
        loadTips()
    }

    override fun onResume() {
        super.onResume()
        loadTips()
    }

    private fun loadTips() {
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
        }) {
            val size = DataManager.getInstance().getSize()
            if (size == 0) {
                tipsVisibilityObservable.set(View.VISIBLE)
            } else {
                tipsVisibilityObservable.set(View.GONE)
            }
        }
    }

    val searchListener = object : SearchEditText.SearchListener {
        override fun search(key: String) {
            searchData(key)
        }
    }

    private fun searchData(key: String) {
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
        }) {
            if (key.isEmpty()) {
                observableList.clear()
            } else {
                val userMutableList = model.search(key)
                showData(userMutableList)
            }

        }
    }

    private fun showData(list: MutableList<User>) {
        KLog.i(TAG, list)
        observableList.clear()
        for (user in list) {
            observableList.add(BlackListUserItemViewModel(this, user) {
                uc.showEditDialogObservable.value = it
            })
        }

    }

    var itemBinding = ItemBinding.of<BlackListUserItemViewModel>(
        BR.viewModel, R.layout.item_user_list_layout
    )
}