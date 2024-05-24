package com.zwc.cocblacklisthelper.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * author:zuoweichen
 * DAte:2024-05-24 11:16
 * Description:描述
 */
@Entity
class User {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    val userId: String
    val text: String

    constructor(id: Int, userId: String, text: String) {
        this.id = id
        this.userId = userId
        this.text = text
    }

    @Ignore
    constructor(userId: String, text: String) {
        this.userId = userId
        this.text = text
    }
}