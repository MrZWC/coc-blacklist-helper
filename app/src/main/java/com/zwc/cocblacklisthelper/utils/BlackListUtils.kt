package com.zwc.cocblacklisthelper.utils

import com.socks.library.KLog
import com.zwc.databaselibrary.DataManager
import com.zwc.databaselibrary.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object BlackListUtils {
    suspend fun saveBlackListText(content: String) = withContext(Dispatchers.IO) {
        val valueText = "\n$content"
        val stringList = valueText.split("\n\uD83C\uDD94")
        val list = mutableListOf<User>()
        for (text in stringList) {
            val trimText = text.trim()
            //花海＃PL2VU899J 戦魂.昊天#YJYL8PGQ8##
            if (trimText.contains("挂黑名单按格式＃") || trimText.contains("赛季黑名单") || trimText.isEmpty()) {
                continue
            }
            val content = "\uD83C\uDD94" + trimText.let {
                if (it.contains("＃")) {
                    it.replace("＃", "#")
                } else {
                    it
                }
            }
            KLog.i("BlackListUtils", content)
            val userId = StringUtils.getStringId(content)
            list.add(User(userId, content))
        }
        DataManager.getUserManager().insertOrReplace(list)
    }
}