package com.zwc.cocblacklisthelper.module.addblacklist

import android.app.AlertDialog
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableInt
import androidx.lifecycle.viewModelScope
import com.socks.library.KLog
import com.zwc.baselibrary.base.BaseViewModel
import com.zwc.baselibrary.binding.command.BindingAction
import com.zwc.baselibrary.binding.command.BindingCommand
import com.zwc.baselibrary.bus.event.SingleLiveEvent
import com.zwc.cocblacklisthelper.BR
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.module.addblacklist.item.BlackListUserItemViewModel
import com.zwc.cocblacklisthelper.utils.FileUtils
import com.zwc.cocblacklisthelper.utils.TopActivity
import com.zwc.cocblacklisthelper.widget.SearchEditText
import com.zwc.cocblacklisthelper.widget.loading.MyLoadingLayout
import com.zwc.databaselibrary.DataManager
import com.zwc.databaselibrary.entity.User
import io.github.idonans.core.util.ToastUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.ItemBinding
import timber.log.Timber
import java.io.File
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddBlackListViewModel(application: Application) :
    BaseViewModel<AddBlackListModel>(application, AddBlackListModel()) {
    private val TAG = this.javaClass.name
    var observableList = ObservableArrayList<BlackListUserItemViewModel>()
    var allObservableList = ObservableArrayList<BlackListUserItemViewModel>()
    var loadingShowTypeField = ObservableInt(MyLoadingLayout.LOADING)
    override fun onCreate() {
        super.onCreate()
    }

    //封装一个界面发生改变的观察者
    var uc: UIChangeObservable = UIChangeObservable()

    inner class UIChangeObservable {
        var showAddContentDialogObservable: SingleLiveEvent<String?>

        //显示编辑地址弹窗
        var showEditDialogObservable: SingleLiveEvent<BlackListUserItemViewModel>

        init {
            showAddContentDialogObservable = SingleLiveEvent()
            showEditDialogObservable = SingleLiveEvent()
        }
    }

    var addContentOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        uc.showAddContentDialogObservable.call()
    })
    var deleteAllOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        TopActivity.getInstance().get()?.apply {
            AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定删除所有黑名单吗？")
                .setPositiveButton(
                    "确定"
                ) { dialog, which -> deleteAll() }
                .setNegativeButton(
                    "取消"
                ) { dialog, which -> dialog.dismiss() }
                .show()
        }

    })
    var shareOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        TopActivity.getInstance().get()?.apply {
            AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("以文件格式分享黑名单")
                .setPositiveButton(
                    "确定"
                ) { dialog, which ->
                    shareBlackListTxt()
                }
                .setNegativeButton(
                    "取消"
                ) { dialog, which -> dialog.dismiss() }
                .show()
        }

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

    private fun deleteAll() {
        showDialog()
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
            dismissDialog()
        }) {
            DataManager.getUserManager().deleteAll()
            observableList.clear()
            allObservableList.clear()
            dismissDialog()
        }
    }

    private fun shareBlackListTxt() {
        showDialog()
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
            dismissDialog()
            ToastUtil.show("文件生成异常")
        }) {
            val users = DataManager.getUserManager().getAll()
            val filePath = withContext(Dispatchers.IO) {
                val stringBuilder = StringBuilder()
                for (user in users) {
                    stringBuilder.append(user.text)
                    stringBuilder.append("\n")
                }
                application.externalCacheDir?.let {
                    val sDateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)
                    val curDateTime = Date()
                    val timeString = sDateFormat.format(curDateTime)
                    val filePath = it.absolutePath + "/share/" + "悟空黑名单分享${timeString}.hmd"
                    if (FileUtils.writeTxt(filePath, stringBuilder.toString())) {
                        return@withContext filePath
                    }
                    ""
                }
            }
            dismissDialog()
            if (!filePath.isNullOrEmpty()) {
                share(filePath)
            } else {
                ToastUtil.show("文件生成失败")
            }
        }
    }

    private fun share(filePath: String) {
        TopActivity.getInstance().get()?.let {
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("hmd")?: "*/*"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = mimeType
            val uri = FileProvider.getUriForFile(
                it,
                it.packageName + ".provider",
                File(filePath)
            )
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            it.startActivity(Intent.createChooser(intent, "分享"))
        }

    }

    /**
     * 导入黑名单
     * @param uri Uri
     */
    fun importBlackList(uri: Uri) {
        showDialog()
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
            dismissDialog()
        }) {
            val content = withContext(Dispatchers.IO) {
                val filePath =
                    application.externalCacheDir!!.absolutePath + "/import/" + "blacklist.txt"
                FileUtils.copyUriToAppInternalStorage(application, uri, filePath)
                val content = FileUtils.readTxt(filePath)
                KLog.i("importText", content)
                content
            }
            dismissDialog()
            uc.showAddContentDialogObservable.value = content
            /* if (!filePath.isNullOrEmpty()) {
                 val users = FileUtils.readTxt(filePath)
                 if (users.isNotEmpty()) {
                     DataManager.getUserManager().insertAll(users)
                 }
             }*/
        }
    }
}