package com.zwc.cocblacklisthelper.widget.dialog

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.FileProvider
import com.zwc.cocblacklisthelper.databinding.DialogFromationMenuLayoutBinding
import com.zwc.cocblacklisthelper.utils.CocUtils
import com.zwc.cocblacklisthelper.utils.StringUtils
import com.zwc.databaselibrary.entity.Formation
import com.zwc.viewdialog.ViewDialog
import java.io.File

/**
 * author:zuoweichen
 * DAte:2024-05-24 13:40
 * Description:描述
 */
class FormationMenuDialog(
    val activity: Activity,
    val data: Formation,
    val delete: () -> Unit,
) {
    private val binding: DialogFromationMenuLayoutBinding
    private val mViewDialog: ViewDialog
    private val TAG = this.javaClass.name

    init {
        val viewGroup = activity.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        binding =
            DialogFromationMenuLayoutBinding.inflate(activity.layoutInflater, viewGroup, false)
        mViewDialog =
            ViewDialog.Builder(activity).setParentView(viewGroup).setContentView(binding.root)
                .setCancelable(true).create()
        initData()
    }

    private fun initData() {

        binding.useBtn.setOnClickListener {
            CocUtils.checkData(activity, data.url)
        }
        binding.shareBtn.setOnClickListener {
            share()
        }
        binding.copyAll.setOnClickListener {
            StringUtils.copyTextToClipboard(activity, data.url)
            hide()
        }
        binding.deleteBtn.setOnClickListener {
            delete()
            hide()
        }

    }

    private fun share() {
        ShareDialog(activity, data).show()
        hide()
    }

    fun show() {
        mViewDialog.show()
    }

    fun hide() {
        mViewDialog.hide(false)
    }
}