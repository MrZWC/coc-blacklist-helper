package com.zwc.cocblacklisthelper.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import java.util.UUID

/**
 * author:zuoweichen
 * DAte:2024-05-24 16:50
 * Description:描述
 */
object StringUtils {
    /**
     * 提取Id
     * @param text String
     * @return String 例：#LY0R0PQUP 如果没有id 则随机生成一个UUID
     */
    fun getStringId(text: String): String {
        if (text.contains("#")) {
            val index = text.indexOf("#")
            val endIndex = if (text.length < index + 10) {
                text.length
            } else {
                index + 10
            }
            return text.substring(index, endIndex)
        }
        return UUID.randomUUID().toString()
    }
     fun copyTextToClipboard(context: Context,text: String) {
        val mClipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        mClipboard.setPrimaryClip(
            ClipData.newPlainText(
                this.javaClass.getSimpleName(),
                text
            )
        )
    }
}