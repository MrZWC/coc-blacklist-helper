package com.zwc.baselibrary.base

import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.zwc.viewdialog.ViewDialog

/**
 * author:zuoweichen
 * DAte:2023/5/23 15:22
 * Description:描述
 */
abstract class BaseDialog<V : ViewDataBinding, VM : BaseViewModel<*>>(
    protected val activity: FragmentActivity, protected val parentView: ViewGroup? = null
) : DefaultLifecycleObserver {
    protected var binding: V
    protected val viewDialog: ViewDialog
    protected var viewModel: VM

    init {
        val viewGroup = parentView ?: activity.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        binding = DataBindingUtil.inflate(activity.layoutInflater, layoutId(), viewGroup, false)
        viewDialog = ViewDialog.Builder(activity)
            .setParentView(viewGroup)
            .setContentView(binding.root)
            .create()
        viewModel = initViewModel()
        binding.setVariable(initVariableId(), viewModel)
        binding.lifecycleOwner = activity
        activity.lifecycle.addObserver(viewModel)
        activity.lifecycle.removeObserver(this)
        initData()
        initListener()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
    }

    open fun initData() {

    }

    open fun initListener() {

    }

    private fun initViewDataBinding() {

    }

    protected abstract fun layoutId(): Int
    protected abstract fun initVariableId(): Int
    protected abstract fun initViewModel(): VM
    open fun initViewObservable() {

    }

    open fun show() {
        viewDialog.show()
    }

    open fun hide() {
        viewDialog.hide(false)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        binding.unbind()
    }
}