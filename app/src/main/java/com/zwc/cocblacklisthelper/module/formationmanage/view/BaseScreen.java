package com.zwc.cocblacklisthelper.module.formationmanage.view;

/**
 * ClassName BaseScreen
 * User: zuoweichen
 * Date: 2021/4/30 14:01
 * Description: 描述
 */
public class BaseScreen {
    /**
     * 是否选中
     */
    public boolean selected;
    /**
     * 名称
     */
    public String title;

    /**
     * 选中数据
     */
    public int data;



    public BaseScreen(boolean selected, String title, int data) {
        this.selected = selected;
        this.title = title;
        this.data = data;
    }
}
