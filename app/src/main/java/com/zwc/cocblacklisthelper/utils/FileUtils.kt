package com.zwc.cocblacklisthelper.utils

import android.content.Context
import android.net.Uri
import com.zwc.baselibrary.util.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset

/**
 * author:zuoweichen
 * DAte:2024-07-30 17:58
 * Description:描述
 */
object FileUtils {
    fun writeTxt(filePath: String, content: String, charset: Charset = Charsets.UTF_8): Boolean {
        try {
            if (!FileUtils.createOrExistsFile(filePath)) {
                return false
            }
            val file = File(filePath)
            file.writeText(content, charset)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    /**
     * 通过uri复制文件到应用内
     * @param context Context
     * @param fileUri Uri
     * @param fileName String
     * @return String?
     */
    fun copyUriToAppInternalStorage(context: Context, fileUri: Uri, filePath: String): String? {
        try {
            // 获取内容解析器
            val contentResolver = context.contentResolver
            // 打开URI对应文件的输入流
            val inputStream = contentResolver.openInputStream(fileUri)

            // 确保文件不为空
            if (inputStream != null) {
                // 获取应用私有目录的输出流

                FileUtils.createOrExistsFile(filePath)
                val file = File(filePath)
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

    fun readTxt(filePath: String,charset: Charset=Charsets.UTF_8): String {
        val file = File(filePath)
        if (!file.exists()) {
            throw Exception("文件不存在")
        }
        return file.readText(charset)
    }
}