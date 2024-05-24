package com.zwc.cocblacklisthelper.module

import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.databinding.ActivityMainBinding
import com.zwc.cocblacklisthelper.module.addblacklist.AddBlackListActivity
import com.zwc.cocblacklisthelper.module.formationcopy.FormationCopyActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
                }

                R.id.action_formation_copy -> {
                    startActivity(Intent(this, FormationCopyActivity::class.java))
                }

                else -> {}
            }
            return@setOnMenuItemClickListener false
        }
        popup.show()
    }
}