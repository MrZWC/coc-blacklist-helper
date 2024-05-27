package com.zwc.cocblacklisthelper.module.addblacklist

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableInt
import androidx.lifecycle.viewModelScope
import com.zwc.baselibrary.base.BaseViewModel
import com.zwc.baselibrary.binding.command.BindingAction
import com.zwc.baselibrary.binding.command.BindingCommand
import com.zwc.baselibrary.bus.event.SingleLiveEvent
import com.zwc.cocblacklisthelper.BR
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.module.addblacklist.item.BlackListUserItemViewModel
import com.zwc.cocblacklisthelper.widget.SearchEditText
import com.zwc.cocblacklisthelper.widget.loading.MyLoadingLayout
import com.zwc.databaselibrary.DataManager
import com.zwc.databaselibrary.entity.User
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
    var allObservableList = ObservableArrayList<BlackListUserItemViewModel>()
    var loadingShowTypeField = ObservableInt(MyLoadingLayout.LOADING)
    var totalNumberObservableInt = ObservableInt(0)
    override fun onCreate() {
        super.onCreate()
    }

    //封装一个界面发生改变的观察者
    var uc: UIChangeObservable = UIChangeObservable()

    inner class UIChangeObservable {
        //显示编辑地址弹窗
        var showAddContentDialogObservable: SingleLiveEvent<Any>
        var showEditDialogObservable: SingleLiveEvent<BlackListUserItemViewModel>

        init {
            showAddContentDialogObservable = SingleLiveEvent()
            showEditDialogObservable = SingleLiveEvent()
        }
    }

    var addContentOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        uc.showAddContentDialogObservable.call()
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
        val itemList = createItemList(list)
        allObservableList.clear()
        allObservableList.addAll(itemList)
        observableList.clear()
        observableList.addAll(allObservableList)
        totalNumberObservableInt.set(observableList.size)
        loadingShowTypeField.set(MyLoadingLayout.CONTENT)
    }

    private suspend fun createItemList(list: MutableList<User>): ObservableArrayList<BlackListUserItemViewModel> {
        return withContext(Dispatchers.IO) {
            val itemList = ObservableArrayList<BlackListUserItemViewModel>()
            for (user in list) {
                itemList.add(BlackListUserItemViewModel(this@AddBlackListViewModel, user) {
                    uc.showEditDialogObservable.value = it
                })
            }
            itemList
        }
    }

    var itemBinding = ItemBinding.of<BlackListUserItemViewModel>(
        BR.viewModel, R.layout.item_user_list_layout
    )

    fun delete(item: BlackListUserItemViewModel) {
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
        }) {
            DataManager.getUserManager().delete(item.data)
            observableList.remove(item)
            allObservableList.remove(item)
            totalNumberObservableInt.set(totalNumberObservableInt.get() - 1)
        }
    }

    val searchListener = SearchEditText.SearchListener { key -> searchData(key) }
    private var firstEmpty = true
    private fun searchData(key: String) {
        if (key.isEmpty()) {
            if (firstEmpty) {
                firstEmpty = false
                return
            }
            //显示所有黑名单
            observableList.clear()
            observableList.addAll(allObservableList)
            return
        }
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
        }) {
            val users = DataManager.getUserManager().queryByKeyWord(key)
            val itemList = createItemList(users)
            observableList.clear()
            observableList.addAll(itemList)
        }
    }
}