package com.zwc.cocblacklisthelper.module.formationcopy

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.databinding.ActivityFormationCopyBinding
import io.github.idonans.core.util.ToastUtil

class FormationCopyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormationCopyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormationCopyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        handleIntent()
        initListener()
    }

    private fun handleIntent() {
        /**
         * 接收intent请求
         */
        intent.data?.apply {
            checkData(this.toString())
        }
    }

    private fun initListener() {

        /**
         * 使用阵型点击事件，替换链接关键字之后通过intent打开应用
         */
        binding.confirmBtn.setOnClickListener {
            checkData(binding.editText.text.toString())
        }

        /**
         * 重置按钮
         */
        binding.clearBtn.setOnClickListener {
            binding.editText.text = null;
        }
    }

    /**
     * 检查数据
     * @param text String?
     */
    private fun checkData(text: String?) {
        var urlText = text
        if (urlText.isNullOrEmpty()) {
            return
        }
        if (urlText.indexOf("=tencent") != -1 || urlText.indexOf("=IOS") != -1 || urlText.indexOf(
                "=iOS"
            ) != -1 || urlText.indexOf("=ios") != -1
        ) {
            var startNum: Int = urlText.indexOf("?")
            urlText = urlText.substring(startNum + 1)
            urlText = "clashofclans://" + urlText
            jumpToCoc(urlText.toUri())
        } else {
            ToastUtil.show("暂不支持此链接\n国服和国际服数据不互通")
        }
    }

    /**
     * 跳转
     * @param uri Uri
     */
    private fun jumpToCoc(uri: Uri) {
        val componentName = ComponentName(
            "com.tencent.tmgp.supercell.clashofclans", "com.supercell.titan.tencent.GameAppTencent"
        )
        val intent = Intent()
        intent.component = componentName
        intent.setData(uri)
        startActivity(intent)
    }
}