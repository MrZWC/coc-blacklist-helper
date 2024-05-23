package com.zwc.baselibrary.base;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.zwc.baselibrary.R;
import com.zwc.viewdialog.ViewDialog;

/**
 * ClassName ProgressDialog
 * User: zuoweichen
 * Date: 2021/4/20 10:00
 * Description: 描述
 */
public class LoadingDialog {
    private ViewDialog mViewDialog;
    private Activity mActivity;
    private TextView titleText;
    private String title;

    public LoadingDialog(Activity activity) {
        this(activity, "");
    }

    public LoadingDialog(Activity activity, String title) {
        mActivity = activity;
        this.title = title;
        ViewGroup parentView = mActivity.findViewById(Window.ID_ANDROID_CONTENT);
        mViewDialog = new ViewDialog.Builder(mActivity)
                .setParentView(parentView)
                .setCancelable(false)
                .dimBackground(true)
                .setContentView(R.layout.dialog_tips_load_title_layout)
                .create();
        initView();
    }

    private void initView() {
        titleText = mViewDialog.findViewById(R.id.tips_loading_msg);
        if (TextUtils.isEmpty(title)) {
            titleText.setVisibility(View.GONE);
        } else {
            titleText.setVisibility(View.VISIBLE);
            titleText.setText(title);
        }

    }

    public void setTitle(String title) {
        this.title = title;
        if (titleText != null) {
            if (TextUtils.isEmpty(title)) {
                titleText.setVisibility(View.GONE);
            } else {
                titleText.setVisibility(View.VISIBLE);
                titleText.setText(title);
            }
        }
    }

    public void show() {
        mViewDialog.show();
    }

    public void hide() {
        mViewDialog.hide(false);
    }
}
