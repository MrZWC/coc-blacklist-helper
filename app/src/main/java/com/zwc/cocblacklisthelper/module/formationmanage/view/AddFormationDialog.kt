package com.zwc.cocblacklisthelper.module.formationmanage.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.socks.library.KLog
import com.zwc.baselibrary.base.LoadingDialog
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.databinding.DialogAddFormationLayoutBinding
import com.zwc.cocblacklisthelper.utils.KeyboardUtils
import com.zwc.databaselibrary.DataManager
import com.zwc.databaselibrary.entity.Formation
import com.zwc.viewdialog.ViewDialog
import io.github.idonans.core.util.ToastUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class AddFormationDialog(val activity: FragmentActivity, private val callBack: () -> Unit) {
    private val binding: DialogAddFormationLayoutBinding
    private val mViewDialog: ViewDialog
    private val chooseCode = 1001
    private val TAG = this.javaClass.name
    private val loadingDialog = LoadingDialog(activity)
    val scope = activity.lifecycleScope

    /**
     * 选择的图片 Uri
     */
    private var imageUri: Uri? = null

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
        mViewDialog.decorView.setOnTouchListener { v, event ->
            KeyboardUtils.closeKeyboard(activity)
            false
        }
        binding.chooseImageBtn.setOnClickListener {
            //选择图片
            openGallery()
        }
        binding.confirmBtn.setOnClickListener {
            //保存
            save()
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

    private fun save() {
        val url = binding.editText.text.toString().trim()
        if (url.isEmpty()) {
            ToastUtil.show("请输入阵型链接")
            return
        }
        if (imageUri == null) {
            ToastUtil.show("请选择阵型图片")
            return
        }
        loadingDialog.show()
        scope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e(throwable)
            loadingDialog.hide()
            ToastUtil.show(throwable.message)
        }) {
            val data = withContext(Dispatchers.IO) {
                val imageFilePath = copyUriToAppInternalStorage(
                    activity,
                    imageUri!!,
                    "${System.currentTimeMillis()}.jpg"
                )
                if (imageFilePath.isNullOrEmpty()) {

                    throw Exception("复制图片到应用内失败")
                }
                val time = System.currentTimeMillis()
                val type = when (binding.radioGroup.checkedRadioButtonId) {
                    R.id.ra_btn1 -> 1
                    R.id.ra_btn2 -> 2
                    R.id.ra_btn3 -> 3
                    R.id.ra_btn4 -> 4
                    R.id.ra_btn5 -> 5
                    else -> 0
                }
                Formation(url, imageFilePath, binding.descriptionEdit.text.toString(), type, time)
            }

            DataManager.getFormationManager().insertOrReplace(data)
            loadingDialog.hide()
            callBack()
            hide()
            ToastUtil.show("保存成功")
        }

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
                imageUri = uri
                KLog.i(TAG, uri.toString())
                Glide.with(activity).load(imageUri).centerCrop().into(binding.image)
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
    fun copyUriToAppInternalStorage(context: Context, fileUri: Uri, fileName: String): String? {
        try {
            // 获取内容解析器
            val contentResolver = context.contentResolver
            // 打开URI对应文件的输入流
            val inputStream = contentResolver.openInputStream(fileUri)

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
                return file.absolutePath
            } else {
                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}