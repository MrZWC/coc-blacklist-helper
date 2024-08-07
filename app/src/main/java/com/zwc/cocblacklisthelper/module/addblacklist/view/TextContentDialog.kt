package com.zwc.cocblacklisthelper.module.addblacklist.view

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.view.Window
import com.zwc.cocblacklisthelper.databinding.DialogTextContentLayoutBinding
import com.zwc.cocblacklisthelper.utils.BlackListUtils
import com.zwc.cocblacklisthelper.utils.KeyboardUtils
import com.zwc.viewdialog.ViewDialog
import io.github.idonans.core.util.ToastUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * author:zuoweichen
 * DAte:2024-05-24 13:40
 * Description:描述
 */
class TextContentDialog(
    activity: Activity,
    private val content: String?,
    private var complete: () -> Unit
) {
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
                .setOnHideListener {
                    KeyboardUtils.closeKeyboard(activity)
                }
                .setOnShowListener {
                    showKeyboard()
                }
                .setCancelable(false).create()
        initData()
    }

    private fun showKeyboard() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.editText.isFocusable = true
            binding.editText.isFocusableInTouchMode = true
            binding.editText.requestFocus()
            KeyboardUtils.showKeyboard(mActivity, binding.editText)
        }, 200)
    }

    private fun initData() {
        if (!content.isNullOrEmpty()) {
            binding.editText.setText(content)
        }
        binding.confirmBtn.setOnClickListener {
            val text = "挂黑名单按格式（私）发我，必须私发，才能快速找到谁挂的谁，骗子结账也好找对人\n" +
                    "5月赛季黑名单\n" +
                    "\uD83C\uDD94少年的你✨言喏#LY0R0PQUP\n" +
                    "\uD83C\uDD94花海＃PL2VU899J"
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
            BlackListUtils.saveBlackListText(textString)
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