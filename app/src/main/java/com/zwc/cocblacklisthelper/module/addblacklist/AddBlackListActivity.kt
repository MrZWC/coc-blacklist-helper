package com.zwc.cocblacklisthelper.module.addblacklist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zwc.baselibrary.base.BaseActivity
import com.zwc.cocblacklisthelper.BR
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.databinding.ActivityAddBlackListBinding
import com.zwc.cocblacklisthelper.module.addblacklist.view.TextContentDialog
import com.zwc.cocblacklisthelper.widget.dialog.EditUserDialog
import com.zwc.cocblacklisthelper.widget.dialog.MenuDialog

class AddBlackListActivity : BaseActivity<ActivityAddBlackListBinding, AddBlackListViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun initData() {
        super.initData()

    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.uc.showAddContentDialogObservable.observe(this) {
            val textContentDialog = TextContentDialog(this) {
                viewModel.loadData()
            }
            textContentDialog.show()
        }
        viewModel.uc.showEditDialogObservable.observe(this) {
            val editDialog = MenuDialog(this, it.data,false, {
                //删除
                viewModel.delete(it)
            }, {
                //修改
                val dialog = EditUserDialog(this, it) {

                }
                dialog.show()
            })
            editDialog.show()
        }
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_add_black_list
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }
}