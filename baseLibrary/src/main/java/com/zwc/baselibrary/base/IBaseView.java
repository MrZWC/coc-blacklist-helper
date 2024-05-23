package com.zwc.baselibrary.base;

/**
 * ClassName IBaseView
 * User: zuoweichen
 * Date: 2021/4/16 15:19
 * Description: 描述
 */
public interface IBaseView {
    /**
     * 初始化界面传递参数
     */
    void initParam();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();
}
