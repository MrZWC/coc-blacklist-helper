package com.zwc.cocblacklisthelper.module.formationmanage.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.socks.library.KLog
import com.zwc.cocblacklisthelper.databinding.DialogAddFormationLayoutBinding
import com.zwc.viewdialog.ViewDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class AddFormationDialog(val activity: FragmentActivity) {
    private val binding: DialogAddFormationLayoutBinding
    private val mViewDialog: ViewDialog
    private val chooseCode = 1001
    private val TAG = this.javaClass.name

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
        activity.startActivityForResult(gallery, chooseCode)
    }

    fun show() {
        mViewDialog.show()
    }

    fun hide() {
        mViewDialog.hide(false)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == chooseCode && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                KLog.i(TAG, uri.toString())
                copyUriToAppInternalStorage(activity, uri, "${System.currentTimeMillis()}.jpg")
                Glide.with(activity).load(uri).centerCrop().into(binding.image)
                binding.chooseImageTipsLayout.visibility = ViewGroup.GONE
            }
        }
    }

    /**
     * 复制文件到应用内
     * @param context Context
     * @param fileUri Uri?
     * @param fileName String?
     */
    fun copyUriToAppInternalStorage(context: Context, fileUri: Uri?, fileName: String?) {
        try {
            // 获取内容解析器
            val contentResolver = context.contentResolver
            // 打开URI对应文件的输入流
            val inputStream = contentResolver.openInputStream(fileUri!!)

            // 确保文件不为空
            if (inputStream != null) {
                // 获取应用私有目录的输出流
                val file = File(context.getExternalFilesDir(null), fileName)
                val outputStream = FileOutputStream(file)

                // 创建缓冲区
                val buffer = ByteArray(1024)
                var length: Int

                // 读写文件
                while (inputStream.read(buffer).also { length = it } != -1) {
                    outputStream.write(buffer, 0, length)
                }

                // 关闭流
                outputStream.flush()
                outputStream.close()
                inputStream.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}