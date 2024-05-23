package com.zwc.cocblacklisthelper.widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.FrameLayout
import com.jakewharton.rxbinding4.widget.textChanges
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.databinding.WidgetSearchEditLayoutBinding
import com.zwc.cocblacklisthelper.utils.KeyboardUtils
import io.github.idonans.lang.util.ViewUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * author:zuoweichen
 * DAte:2023/5/17 15:27
 * Description:描述
 */
class SearchEditText : FrameLayout {
    private lateinit var binding: WidgetSearchEditLayoutBinding

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initAttributeSet(context, attrs)
        initListener()
    }


    @SuppressLint("CheckResult")
    private fun initAttributeSet(context: Context, attrs: AttributeSet?) {
        binding = WidgetSearchEditLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.SearchEditText
        )
        val hintString = a.getString(R.styleable.SearchEditText_se_hint)
        binding.searchEdit.hint = hintString

    }

    private fun initListener() {
        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) {
                    ViewUtil.setVisibilityIfChanged(binding.clearBtn, View.GONE)
                } else {
                    ViewUtil.setVisibilityIfChanged(binding.clearBtn, View.VISIBLE)

                }
            }

        })
        binding.searchEdit.textChanges().debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                mSearchListener?.search(it.toString())
            }, {
                Timber.e(it)
            })
        binding.searchEdit.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                ViewUtil.setVisibilityIfChanged(binding.cancelBtn, View.VISIBLE)
            } else {
                KeyboardUtils.closeKeyboard(context, binding.searchEdit)
                val text = binding.searchEdit.text.toString()
                if (text.isEmpty()) {
                    ViewUtil.setVisibilityIfChanged(binding.cancelBtn, View.GONE)
                }
            }
        }
        binding.clearBtn.setOnClickListener {
            binding.searchEdit.text = null
        }
        binding.cancelBtn.setOnClickListener {
            ViewUtil.setVisibilityIfChanged(binding.cancelBtn, View.GONE)
            binding.searchEdit.clearFocus()
            binding.searchEdit.text = null
            KeyboardUtils.closeKeyboard(context, binding.searchEdit)
        }

    }

    private var mSearchListener: SearchListener? = null
    fun setSearchListener(mSearchListener: SearchListener?) {
        this.mSearchListener = mSearchListener
    }

    fun showKeyboard() {
        binding.searchEdit.isFocusable = true
        binding.searchEdit.isFocusableInTouchMode = true
        binding.searchEdit.requestFocus()
        KeyboardUtils.showKeyboard(context, binding.searchEdit)
    }

    fun interface SearchListener {
        fun search(key: String)
    }

    override fun clearFocus() {
        super.clearFocus()
        binding.searchEdit.clearFocus()
    }
}