package com.zwc.cocblacklisthelper.widget.dialog

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.zwc.cocblacklisthelper.databinding.DialogMenuLayoutBinding
import com.zwc.cocblacklisthelper.utils.StringUtils
import com.zwc.databaselibrary.entity.User
import com.zwc.viewdialog.ViewDialog
import io.github.idonans.core.util.ToastUtil

/**
 * author:zuoweichen
 * DAte:2024-05-24 13:40
 * Description:描述
 */
class UserMenuDialog(
    val activity: Activity,
    val user: User,
    val main: Boolean,
    val delete: () -> Unit,
    val edit: () -> Unit
) {
    private val binding: DialogMenuLayoutBinding
    private val mViewDialog: ViewDialog
    private val TAG = this.javaClass.name

    init {
        val viewGroup = activity.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        binding = DialogMenuLayoutBinding.inflate(activity.layoutInflater, viewGroup, false)
        mViewDialog =
            ViewDialog.Builder(activity).setParentView(viewGroup).setContentView(binding.root)
                .setCancelable(true).create()
        initData()
    }

    private fun initData() {
        if (main) {
            binding.deleteBtn.visibility = View.GONE
            binding.editBtn.visibility = View.GONE
        }
        binding.copyAll.setOnClickListener {
            StringUtils.copyTextToClipboard(activity, user.text)
            hide()
        }
        binding.copyTag.setOnClickListener {
            if (user.userId.startsWith("#")) {
                StringUtils.copyTextToClipboard(activity, user.userId)
                ToastUtil.show("复制成功")
            } else {
                ToastUtil.show("标签为自定义标签，或者为空")
            }
            hide()
        }
        binding.deleteBtn.setOnClickListener {
            delete()
            hide()
        }
        binding.editBtn.setOnClickListener {
            edit()
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