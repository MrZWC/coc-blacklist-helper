package com.zwc.cocblacklisthelper.module.main

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.viewModelScope
import com.socks.library.KLog
import com.zwc.baselibrary.base.BaseViewModel
import com.zwc.cocblacklisthelper.BR
import com.zwc.cocblacklisthelper.R
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
            observableList.add(BlackListUserItemViewModel(this, user))
        }

    }

    var itemBinding = ItemBinding.of<BlackListUserItemViewModel>(
        BR.viewModel, R.layout.item_user_list_layout
    )
}