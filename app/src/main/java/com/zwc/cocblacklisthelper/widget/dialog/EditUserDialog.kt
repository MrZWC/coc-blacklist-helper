package com.zwc.cocblacklisthelper.widget.dialog

import android.app.Activity
import android.view.ViewGroup
import android.view.Window
import com.zwc.cocblacklisthelper.databinding.DialogEditUserLayoutBinding
import com.zwc.cocblacklisthelper.module.addblacklist.item.BlackListUserItemViewModel
import com.zwc.databaselibrary.DataManager
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
class EditUserDialog(
    activity: Activity,
    val item: BlackListUserItemViewModel,
    private var complete: () -> Unit
) {
    private val binding: DialogEditUserLayoutBinding
    private val mActivity: Activity = activity
    private val mViewDialog: ViewDialog
    private val scope = MainScope()
    private val TAG = this.javaClass.name

    init {
        val viewGroup = activity.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        binding = DialogEditUserLayoutBinding.inflate(activity.layoutInflater, viewGroup, false)
        mViewDialog =
            ViewDialog.Builder(mActivity).setParentView(viewGroup).setContentView(binding.root)
                .setCancelable(true).create()
        initData()
    }

    private fun initData() {
        binding.editText.setText(item.data.text)
        binding.root.setOnClickListener { }
        binding.confirmBtn.setOnClickListener {
            save(binding.editText.text.toString())
        }
        binding.cancelBtn.setOnClickListener {
            hide()
        }
    }

    private fun save(textString: String) {
        scope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
            ToastUtil.show("添加失败")
        }) {
            val user = item.data
            user.text = textString
            DataManager.getUserManager().update(user)
            item.changeData(user.text)
            ToastUtil.show("修改成功")
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