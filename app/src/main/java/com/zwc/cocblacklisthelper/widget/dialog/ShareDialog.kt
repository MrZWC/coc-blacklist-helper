package com.zwc.cocblacklisthelper.widget.dialog

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.FileProvider
import com.zwc.cocblacklisthelper.databinding.DialogShareLayoutBinding
import com.zwc.databaselibrary.entity.Formation
import com.zwc.viewdialog.ViewDialog
import java.io.File

/**
 * author:zuoweichen
 * DAte:2024-05-30 14:41
 * Description:描述
 */
class ShareDialog(
    val activity: Activity,
    val data: Formation,
) {
    private val binding: DialogShareLayoutBinding
    private val mViewDialog: ViewDialog
    private val TAG = this.javaClass.name

    init {
        val viewGroup = activity.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        binding =
            DialogShareLayoutBinding.inflate(activity.layoutInflater, viewGroup, false)
        mViewDialog =
            ViewDialog.Builder(activity).setParentView(viewGroup).setContentView(binding.root)
                .dimBackground(true)
                .defaultAnimation()
                .setCancelable(true)
                .create()
        initData()
    }

    private fun initData() {
        binding.shareTextBtn.setOnClickListener {
            shareText()
        }
        binding.shareImageBtn.setOnClickListener {
            shareImage()
        }
        binding.root.setOnClickListener { }
    }

    fun show() {
        mViewDialog.show()
    }

    fun hide() {
        mViewDialog.hide(false)
    }

    private fun shareImage() {
        val intent = Intent(Intent.ACTION_SEND)
        if (data.url.isNotEmpty()) {
            intent.type = "image/*"
            val uri = FileProvider.getUriForFile(
                activity,
                activity.packageName + ".provider",
                File(data.imageFilePath)
            )
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            activity.startActivity(Intent.createChooser(intent, "分享"))
        }
    }

    private fun shareText() {
        val intent = Intent(Intent.ACTION_SEND)
        if (data.url.isNotEmpty()) {
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, data.url)
            activity.startActivity(Intent.createChooser(intent, "分享"))
        }
    }
}