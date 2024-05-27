package com.zwc.cocblacklisthelper.module.addblacklist.view

import android.app.Activity
import android.view.ViewGroup
import android.view.Window
import com.socks.library.KLog
import com.zwc.cocblacklisthelper.database.DataManager
import com.zwc.cocblacklisthelper.database.entity.User
import com.zwc.cocblacklisthelper.databinding.DialogTextContentLayoutBinding
import com.zwc.cocblacklisthelper.utils.StringUtils
import com.zwc.viewdialog.ViewDialog
import io.github.idonans.core.util.ToastUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * author:zuoweichen
 * DAte:2024-05-24 13:40
 * Description:描述
 */
class TextContentDialog(activity: Activity, private var complete: () -> Unit) {
    private val binding: DialogTextContentLayoutBinding
    private val mActivity: Activity = activity
    private val mViewDialog: ViewDialog
    private val scope = MainScope()
    private val TAG = this.javaClass.name

    init {
        val viewGroup = activity.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        binding = DialogTextContentLayoutBinding.inflate(activity.layoutInflater, viewGroup, false)
        mViewDialog =
            ViewDialog.Builder(mActivity).setParentView(viewGroup).setContentView(binding.root)
                .setCancelable(false).create()
        initData()
    }

    private fun initData() {
        binding.confirmBtn.setOnClickListener {
            val text = "挂黑名单按格式（私）发我，必须私发，才能快速找到谁挂的谁，骗子结账也好找对人\n" +
                    "5月赛季黑名单\n" +
                    "\uD83C\uDD94少年的你✨言喏#LY0R0PQUP\n"
            handleData(binding.editText.text.toString())
            //handleData(text)
        }
        binding.cancelBtn.setOnClickListener {
            hide()
        }
    }

    private fun handleData(textString: String?) {
        if (textString.isNullOrEmpty()) {
            ToastUtil.show("输入内容为空")
            return
        }

        scope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
            ToastUtil.show("添加失败")
            //"\uD83C\uDD94"
        }) {
            withContext(Dispatchers.IO) {
                val valueText = "\n$textString"
                val stringList = valueText.split("\n\uD83C\uDD94")
                val list = mutableListOf<User>()
                for (text in stringList) {
                    val trimText = text.trim()
                    if (trimText.contains("挂黑名单按格式") || trimText.contains("赛季黑名单")) {
                        continue
                    }
                    val content = "\uD83C\uDD94" + trimText
                    KLog.i(TAG, content)
                    val userId = StringUtils.getStringId(content)
                    list.add(User(userId, content))
                }
                DataManager.getUserManager().insertOrReplace(list)
            }
            ToastUtil.show("添加成功")
            complete()
            hide()
        }
    }

    fun show() {
        mViewDialog.show()
    }

    fun hide() {
        mViewDialog.hide(false)
    }
}