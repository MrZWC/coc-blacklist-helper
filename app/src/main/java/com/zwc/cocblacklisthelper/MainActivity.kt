package com.zwc.cocblacklisthelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.zwc.cocblacklisthelper.databinding.ActivityMainBinding
import com.zwc.cocblacklisthelper.module.addblacklist.AddBlackListActivity
import io.github.idonans.core.util.ToastUtil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
    }

    private fun initListener() {
        binding.menuBtn.setOnClickListener {
            showMenu(it)
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
                    ToastUtil.show("添加")
                }

                else -> {}
            }
            return@setOnMenuItemClickListener false
        }
        popup.show()
    }
}