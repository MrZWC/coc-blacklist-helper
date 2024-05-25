package com.zwc.cocblacklisthelper.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.databinding.WidgetCommonTitleToolbarLayoutBinding

class CommonTitleToolBar : FrameLayout {
    private lateinit var binding: WidgetCommonTitleToolbarLayoutBinding
    private var title: String? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        binding = WidgetCommonTitleToolbarLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleToolBar)
        title = ta.getString(R.styleable.CommonTitleToolBar_ctb_title)
        ta.recycle()
        initView()
        initData()
    }

    private fun initData() {
        binding.backBtn.setOnClickListener {
            (context as? Activity)?.finish()
        }
        binding.titleText.text = title
    }

    private fun initView() {
    }
}