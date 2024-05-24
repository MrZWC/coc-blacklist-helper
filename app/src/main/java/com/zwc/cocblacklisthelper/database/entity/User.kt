package com.zwc.cocblacklisthelper.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

/**
 * author:zuoweichen
 * DAte:2024-05-24 11:16
 * Description:描述
 */
@Entity(
    indices = [Index(
        value = ["userId","id"],
        unique = true
    )]
)
class User {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    val userId: String
    var text: String

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