package com.zwc.cocblacklisthelper.module.addblacklist.view

import android.app.Activity
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.viewModelScope
import com.socks.library.KLog
import com.zwc.cocblacklisthelper.database.DataManager
import com.zwc.cocblacklisthelper.database.entity.User
import com.zwc.cocblacklisthelper.databinding.DialogTextContentLayoutBinding
import com.zwc.viewdialog.ViewDialog
import io.github.idonans.core.util.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * author:zuoweichen
 * DAte:2024-05-24 13:40
 * Description:描述
 */
class TextContentDialog {
    private val binding: DialogTextContentLayoutBinding
    private val mActivity: Activity
    private val mViewDialog: ViewDialog
    private val scope = MainScope()
    private val TAG = this.javaClass.name

    constructor(activity: Activity) {
        this.mActivity = activity
        val viewGroup = activity.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        binding = DialogTextContentLayoutBinding.inflate(activity.layoutInflater, viewGroup, false)
        mViewDialog =
            ViewDialog.Builder(mActivity).setParentView(viewGroup).setContentView(binding.root)
                .setCancelable(true).create()
        initData()
    }

    private fun initData() {
        binding.confirmBtn.setOnClickListener {
            handleData(binding.editText.text.toString())
        }
    }

    private fun handleData(textString: String?) {
        if (textString.isNullOrEmpty()) {
            ToastUtil.show("输入内容为空")
            return
        }

        scope.launch {
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

    fun show() {
        mViewDialog.show()
    }

    fun hide() {
        mViewDialog.hide(false)
    }
}