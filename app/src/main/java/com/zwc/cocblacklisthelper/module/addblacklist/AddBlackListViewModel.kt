package com.zwc.cocblacklisthelper.module.addblacklist

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
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
import io.github.idonans.core.util.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.ItemBinding

class AddBlackListViewModel : BaseViewModel<AddBlackListModel> {
    private val TAG = this.javaClass.name
    var textObservableField = ObservableField("")
    var observableList = ObservableArrayList<BlackListUserItemViewModel>()

    constructor(application: Application) : super(application)
    constructor(application: Application, model: AddBlackListModel?) : super(application, model)

    override fun onCreate() {
        super.onCreate()
        val text = "挂黑名单按格式（私）发我，必须私发，才能快速找到谁挂的谁，骗子结账也好找对人\n" +
                "5月赛季黑名单\n" +
                "\uD83C\uDD94少年的你✨言喏#LY0R0PQUP\n" +
                "\uD83C\uDD94我养了一只可爱的小猪在家里。"
        textObservableField.set(text)
    }

    //封装一个界面发生改变的观察者
    var uc: UIChangeObservable = UIChangeObservable()

    inner class UIChangeObservable {
        //显示编辑地址弹窗
        var showAddContentDialogObservable: SingleLiveEvent<Any>

        init {
            showAddContentDialogObservable = SingleLiveEvent()
        }
    }

    /**
     * 文本添加确认监听
     */
    var textConfirmOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        handleData(textObservableField.get())
    })
    var addContentOnClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        uc.showAddContentDialogObservable.call()
    })

    private fun handleData(textString: String?) {
        if (textString.isNullOrEmpty()) {
            ToastUtil.show("输入内容为空")
            return
        }

        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                val stringList = textString.split("\n")
                val list = mutableListOf<User>()
                for (text in stringList) {
                    if (text.contains("挂黑名单按格式") || text.contains("赛季黑名单")) {
                        continue
                    }
                    KLog.i(TAG, text)
                    list.add(User("", text))
                }
                DataManager.getInstance().insert(list)
            }
            ToastUtil.show("添加成功")

        }
    }

    var itemBinding = ItemBinding.of<BlackListUserItemViewModel>(
        BR.viewModel, R.layout.item_user_list_layout
    )
}