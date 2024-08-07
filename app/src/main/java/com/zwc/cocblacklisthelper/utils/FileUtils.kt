package com.zwc.cocblacklisthelper.utils

import java.io.File
import java.io.FileOutputStream

/**
 * author:zuoweichen
 * DAte:2024-07-30 17:58
 * Description:描述
 */
object FileUtils {
    fun writeTxt(fileName: String, content: String): Boolean {
        try {
            val file = File(fileName)
            if (!file.exists()) {
                file.createNewFile()
            }
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}