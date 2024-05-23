package com.zwc.baselibrary.base;


import androidx.annotation.NonNull;

/**
 * ClassName MultiItemViewModel
 * User: zuoweichen
 * Date: 2021/4/19 11:37
 * Description: RecycleView多布局ItemViewModel是封装
 */
public class MultiItemViewModel<VM extends BaseViewModel<?>> extends ItemViewModel<VM> {
    protected Object multiType;

    public Object getItemType() {
        return multiType;
    }

    public void multiItemType(@NonNull Object multiType) {
        this.multiType = multiType;
    }

    public MultiItemViewModel(@NonNull VM viewModel) {
        super(viewModel);
    }
}
