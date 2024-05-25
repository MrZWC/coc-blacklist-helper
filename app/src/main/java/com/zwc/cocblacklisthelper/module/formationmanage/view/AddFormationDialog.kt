package com.zwc.cocblacklisthelper.module.formationmanage.view

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.view.ViewGroup
import android.view.Window
import com.zwc.cocblacklisthelper.databinding.DialogAddFormationLayoutBinding
import com.zwc.viewdialog.ViewDialog

class AddFormationDialog(val activity: Activity) {
    private val binding: DialogAddFormationLayoutBinding
    private val mViewDialog: ViewDialog

    init {
        val viewGroup = activity.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        binding = DialogAddFormationLayoutBinding.inflate(activity.layoutInflater, viewGroup, false)
        mViewDialog = ViewDialog.Builder(activity)
            .setParentView(viewGroup)
            .setContentView(binding.root)
            .dimBackground(true)
            .setCancelable(false)
            .create()
        initData()
    }

    private fun initData() {
        binding.chooseImageBtn.setOnClickListener {
            //选择图片
            openGallery()
        }
        binding.confirmBtn.setOnClickListener {
            //保存
        }
        binding.cancelBtn.setOnClickListener {
            hide()
        }
    }
    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK)
        gallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/")
        activity.startActivityForResult(gallery, 1001)
    }
    fun show() {
        mViewDialog.show()
    }

    fun hide() {
        mViewDialog.hide(false)
    }
}