package com.zwc.cocblacklisthelper.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import io.github.idonans.core.util.ToastUtil

object CocUtils {
    /**
     * 检查数据
     * @param text String?
     */
    fun checkData(activity: Activity, text: String?) {
        var urlText = text
        if (urlText.isNullOrEmpty()) {
            ToastUtil.show("阵型链接为空")
            return
        }
        if (urlText.indexOf("=tencent") != -1 || urlText.indexOf("=IOS") != -1 || urlText.indexOf(
                "=iOS"
            ) != -1 || urlText.indexOf("=ios") != -1
        ) {
            var startNum: Int = urlText.indexOf("?")
            urlText = urlText.substring(startNum + 1)
            urlText = "clashofclans://" + urlText
            jumpToCoc(activity, urlText.toUri())
        } else {
            ToastUtil.show("暂不支持此链接\n国服和国际服数据不互通")
        }
    }

    /**
     * 跳转
     * @param uri Uri
     */
    private fun jumpToCoc(activity: Activity, uri: Uri) {
        val componentName = ComponentName(
            "com.tencent.tmgp.supercell.clashofclans", "com.supercell.titan.tencent.GameAppTencent"
        )
        val intent = Intent()
        intent.component = componentName
        intent.setData(uri)
        activity.startActivity(intent)
    }
}