package com.zwc.baselibrary.base;

/**
 * ClassName IModel
 * User: zuoweichen
 * Date: 2021/4/16 15:18
 * Description: 描述
 */
public interface IModel {
    /**
     * ViewModel销毁时清除Model，与ViewModel共消亡。Model层同样不能持有长生命周期对象
     */
    void onCleared();
}
