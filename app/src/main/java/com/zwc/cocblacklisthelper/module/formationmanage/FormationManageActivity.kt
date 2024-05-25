package com.zwc.cocblacklisthelper.module.formationmanage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zwc.baselibrary.base.BaseActivity
import com.zwc.cocblacklisthelper.BR
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.databinding.ActivityFormationManageBinding
import com.zwc.cocblacklisthelper.module.formationmanage.view.AddFormationDialog

/**
 *阵型管理界面
 * @constructor Create empty Formation manage activity
 */
class FormationManageActivity :
    BaseActivity<ActivityFormationManageBinding, FormationManageViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.uc.showAddDialogObservable.observe(this) {
            AddFormationDialog(this).show()
        }
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_formation_manage
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }
}