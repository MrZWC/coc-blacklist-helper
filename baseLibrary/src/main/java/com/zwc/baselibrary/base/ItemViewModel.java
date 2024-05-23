package com.zwc.baselibrary.base;


import androidx.annotation.NonNull;

/**
 * ClassName ItemViewModel
 * User: zuoweichen
 * Date: 2021/4/19 11:37
 * Description: 描述
 */
public class ItemViewModel<VM extends BaseViewModel<?>> {
    protected VM viewModel;

    public ItemViewModel(@NonNull VM viewModel) {
        this.viewModel = viewModel;
    }
}
