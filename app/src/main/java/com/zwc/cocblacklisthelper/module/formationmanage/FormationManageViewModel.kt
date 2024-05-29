package com.zwc.cocblacklisthelper.module.formationmanage

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
import com.zwc.cocblacklisthelper.module.formationmanage.item.FormationItemViewModel
import com.zwc.cocblacklisthelper.widget.loading.MyLoadingLayout
import com.zwc.databaselibrary.entity.Formation
import io.github.idonans.core.util.ToastUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.ItemBinding
import timber.log.Timber

class FormationManageViewModel(application: Application) :
    BaseViewModel<FormationManageModel>(application, FormationManageModel()) {
    var loadingShowTypeField = ObservableInt(MyLoadingLayout.LOADING)
    var observableList = ObservableArrayList<FormationItemViewModel>()

    //封装一个界面发生改变的观察者
    var uc: UIChangeObservable = UIChangeObservable()

    inner class UIChangeObservable {
        //显示编辑地址弹窗
        var showEditDialogObservable: SingleLiveEvent<FormationItemViewModel>
        var showAddDialogObservable: SingleLiveEvent<Any>

        init {
            showEditDialogObservable = SingleLiveEvent()
            showAddDialogObservable = SingleLiveEvent()
        }
    }

    init {
        loadData()
    }

    private var tagType = -1
    fun loadData(type: Int = tagType) {
        tagType = type
        loadingShowTypeField.set(MyLoadingLayout.LOADING)
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
            loadingShowTypeField.set(MyLoadingLayout.ERROR)
        }) {
            val loadAllData = model.loadData(type)
            if (loadAllData.isEmpty()) {
                loadingShowTypeField.set(MyLoadingLayout.EMPTY)
            } else {
                showData(loadAllData)
            }

        }
    }

    private suspend fun showData(list: MutableList<Formation>) {
        val itemList = createItemList(list)
        observableList.clear()
        observableList.addAll(itemList)
        loadingShowTypeField.set(MyLoadingLayout.CONTENT)
    }

    private suspend fun createItemList(list: MutableList<Formation>): ObservableArrayList<FormationItemViewModel> {
        return withContext(Dispatchers.IO) {
            val itemList = ObservableArrayList<FormationItemViewModel>()
            for (user in list) {
                itemList.add(FormationItemViewModel(this@FormationManageViewModel, user) {
                    uc.showEditDialogObservable.value = it
                })
            }
            itemList
        }
    }


    var addOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        uc.showAddDialogObservable.call()
    })
    var itemBinding = ItemBinding.of<FormationItemViewModel>(
        BR.viewModel, R.layout.item_formation_list_layout
    )

    fun delete(itemViewModel: FormationItemViewModel) {
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
            ToastUtil.show("删除失败")
        }) {
            model.delete(itemViewModel.data)
            observableList.remove(itemViewModel)
        }
    }
}