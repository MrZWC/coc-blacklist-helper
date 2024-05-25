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
@Entity
class User(@PrimaryKey val userId: String, var text: String) {
}