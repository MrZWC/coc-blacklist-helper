package com.zwc.baselibrary.base

/**
 * author:zuoweichen
 * DAte:2023/7/10 15:15
 * Description:描述
 */
interface IBaseResponse<T> {
    fun code(): Int
    fun msg(): String?
    fun data(): T?
    fun isSuccess(): Boolean
}