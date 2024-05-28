package com.zwc.cocblacklisthelper.module.formationmanage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.zwc.cocblacklisthelper.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2021/4/9
 * Time: 16:52
 * describe: 描述
 */
public class ScreenButton extends FrameLayout {
    private TextView title;

    public ScreenButton(@NonNull Context context) {
        super(context);
        initialize(context, null);
    }

    public ScreenButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);

    }

    public ScreenButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        inflate(context, R.layout.widget_screen_button_layout, this);
        initView();
        initData();
    }

    private void initView() {
        title = findViewById(R.id.title);
    }

    private void initData() {

    }

    public void setText(String text) {
        title.setText(text);
    }
}
