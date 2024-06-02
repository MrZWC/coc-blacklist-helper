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
     * Type 0:冲杯，1:联赛，2:部落战，3：艺术，4：牛批，5：网红
     */
    val type: Int
    /**
     * 添加时间
     */
    val dataTime: Long



    constructor(
        id: Int,
        url: String,
        imageFilePath: String,
        description: String,
        type: Int,
        dataTime: Long
    ) {
        this.id = id
        this.url = url
        this.imageFilePath = imageFilePath
        this.description = description
        this.type = type
        this.dataTime = dataTime
    }
    @Ignore
    constructor(
        url: String,
        imageFilePath: String,
        description: String,
        type: Int,
        dataTime: Long
    ) {
        this.id = id
        this.url = url
        this.imageFilePath = imageFilePath
        this.description = description
        this.type = type
        this.dataTime = dataTime
    }
}