package com.zwc.cocblacklisthelper.module.addblacklist

import android.app.Application
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.viewModelScope
import com.socks.library.KLog
import com.zwc.baselibrary.base.BaseViewModel
import com.zwc.baselibrary.binding.command.BindingAction
import com.zwc.baselibrary.binding.command.BindingCommand
import com.zwc.baselibrary.bus.event.SingleLiveEvent
import com.zwc.cocblacklisthelper.BR
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.database.DataManager
import com.zwc.cocblacklisthelper.database.entity.User
import com.zwc.cocblacklisthelper.module.addblacklist.item.BlackListUserItemViewModel
import com.zwc.cocblacklisthelper.widget.loading.MyLoadingLayout
import io.github.idonans.core.util.ToastUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.ItemBinding
import timber.log.Timber

class AddBlackListViewModel(application: Application) :
    BaseViewModel<AddBlackListModel>(application, AddBlackListModel()) {
    private val TAG = this.javaClass.name
    var observableList = ObservableArrayList<BlackListUserItemViewModel>()
    var loadingShowTypeField = ObservableInt(MyLoadingLayout.LOADING)
    override fun onCreate() {
        super.onCreate()
    }

    //封装一个界面发生改变的观察者
    var uc: UIChangeObservable = UIChangeObservable()

    inner class UIChangeObservable {
        //显示编辑地址弹窗
        var showAddContentDialogObservable: SingleLiveEvent<Any>
        var showAddOneDialogObservable: SingleLiveEvent<Any>

        init {
            showAddContentDialogObservable = SingleLiveEvent()
            showAddOneDialogObservable = SingleLiveEvent()
        }
    }

    var addContentOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        uc.showAddContentDialogObservable.call()
    })
    var addOneOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        uc.showAddOneDialogObservable.call()
    })

    init {
        loadData()
    }

    fun loadData() {
        loadingShowTypeField.set(MyLoadingLayout.LOADING)
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
            loadingShowTypeField.set(MyLoadingLayout.ERROR)
        }) {
            val loadAllData = model.loadData()
            if (loadAllData.isEmpty()) {
                loadingShowTypeField.set(MyLoadingLayout.EMPTY)
            } else {
                showData(loadAllData)
            }

        }
    }

    private suspend fun showData(list: MutableList<User>) {
        val itemList = ObservableArrayList<BlackListUserItemViewModel>()
        withContext(Dispatchers.IO) {
            for (user in list) {
                itemList.add(BlackListUserItemViewModel(this@AddBlackListViewModel, user))
            }
        }
        observableList.clear()
        observableList.addAll(itemList)
        loadingShowTypeField.set(MyLoadingLayout.CONTENT)
    }

    var itemBinding = ItemBinding.of<BlackListUserItemViewModel>(
        BR.viewModel, R.layout.item_user_list_layout
    )
}