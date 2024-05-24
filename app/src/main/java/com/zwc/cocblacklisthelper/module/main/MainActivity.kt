package com.zwc.cocblacklisthelper.module.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zwc.baselibrary.base.BaseActivity
import com.zwc.cocblacklisthelper.BR
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.databinding.ActivityMainBinding
import com.zwc.cocblacklisthelper.module.about.AboutActivity
import com.zwc.cocblacklisthelper.module.addblacklist.AddBlackListActivity
import com.zwc.cocblacklisthelper.module.formationcopy.FormationCopyActivity
import com.zwc.cocblacklisthelper.widget.dialog.EditUserDialog
import com.zwc.cocblacklisthelper.widget.dialog.MenuDialog

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initListener()
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    private fun initListener() {
        binding.menuBtn.setOnClickListener {
            showMenu(it)
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.uc.showEditDialogObservable.observe(this) {
            val editDialog = MenuDialog(this, it.data, true, {

            }, {
                //修改
                val dialog = EditUserDialog(this, it) {

                }
                dialog.show()
            })
            editDialog.show()
        }
    }

    fun showMenu(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.main, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_add -> {
                    startActivity(Intent(this, AddBlackListActivity::class.java))
                }

                R.id.action_formation_copy -> {
                    startActivity(Intent(this, FormationCopyActivity::class.java))
                }

                R.id.action_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }

                else -> {}
            }
            return@setOnMenuItemClickListener false
        }
        popup.show()
    }
}