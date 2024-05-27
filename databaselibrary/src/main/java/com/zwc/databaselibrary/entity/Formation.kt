package com.zwc.databaselibrary.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * author:zuoweichen
 * DAte:2024-05-27 17:09
 * Description:描述
 */
@Entity
class Formation {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    /**
     * 阵型url
     */
    val url: String

    /**
     * 阵型图片文件地址
     */
    val imageFilePath: String

    /**
     * 描述
     */
    val description: String

    /**
     * 添加时间
     */
    val dataTime: Long

    constructor(id: Int, url: String, imageFilePath: String, description: String, dataTime: Long) {
        this.id = id
        this.url = url
        this.imageFilePath = imageFilePath
        this.description = description
        this.dataTime = dataTime
    }

    @Ignore
    constructor(url: String, imageFilePath: String, description: String, dataTime: Long) {
        this.url = url
        this.imageFilePath = imageFilePath
        this.description = description
        this.dataTime = dataTime
    }
}