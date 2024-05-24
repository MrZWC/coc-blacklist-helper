package com.zwc.cocblacklisthelper.widget.loading;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.zwc.cocblacklisthelper.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName TaskModelEmptyView
 * User: zuoweichen
 * Date: 2021/5/13 11:04
 * Description: 自定义局部loading view 可嵌套使用
 */
public class MyLoadingLayout extends FrameLayout {
    public static final int CONTENT = 0;
    public static final int ERROR = 1;
    public static final int LOADING = 2;
    public static final int EMPTY = 3;

    @IntDef({CONTENT, ERROR, LOADING, EMPTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowType {
    }

    int mEmptyResId = NO_ID, mLoadingResId = NO_ID, mErrorResId = NO_ID;
    int mContentId = NO_ID;
    Map<Integer, View> mLayouts = new HashMap<>();
    LayoutInflater mInflater;
    private String mEmptyText;
    OnClickListener mRetryListener;
    /**
     * 是否当content有数据时不在显示错误页和空页面 默认 false
     * 例：当首页有数据时 刷新后不论错误不在显示 错误和空页面防止 没有数据显示 呈现空或者错误页面
     */
    private boolean isShowOnly;
    /**
     * 是否显示过content
     */
    private boolean isShowedContent;
    private int mShowType;

    public MyLoadingLayout(@NonNull Context context) {
        super(context);
        initAttrs(context, null);
    }

    public MyLoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public MyLoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.YnLoadingLayout);
        mEmptyText = ta.getString(R.styleable.YnLoadingLayout_ynEmptyText);
        isShowOnly = ta.getBoolean(R.styleable.YnLoadingLayout_ynShowOnly, false);
        mEmptyResId = ta.getResourceId(R.styleable.YnLoadingLayout_ynEmptyResId, R.layout._loading_empty_layout);
        mErrorResId = ta.getResourceId(R.styleable.YnLoadingLayout_ynErrorResId, R.layout._loading_error_layout);
        mLoadingResId = ta.getResourceId(R.styleable.YnLoadingLayout_ynLoadingResId, R.layout._loading_loading_layout);
        mShowType = ta.getInt(R.styleable.YnLoadingLayout_ynShowType, CONTENT);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) {
            return;
        }
        if (getChildCount() > 1) {
            removeViews(1, getChildCount() - 1);
        }
        View view = getChildAt(0);
        setContentView(view);
        setYnShowType(mShowType);
    }

    private void setContentView(View view) {
        mContentId = view.getId();
        mLayouts.put(mContentId, view);
    }

    public void showLoading() {
        show(mLoadingResId);
    }

    public void showEmpty() {
        if (isShowOnly && isShowedContent) {
            return;
        }
        show(mEmptyResId);
    }

    public void showError() {
        if (isShowOnly && isShowedContent) {
            return;
        }
        show(mErrorResId);
    }

    public void showContent() {
        isShowedContent = true;
        show(mContentId);
    }

    public void setYnShowType(@ShowType int type) {
        if (type == 1) {
            showError();
        } else if (type == 2) {
            showLoading();
        } else if (type == 3) {
            showEmpty();
        } else {
            showContent();
        }
    }

    public MyLoadingLayout setEmpty(@LayoutRes int id) {
        if (mEmptyResId != id) {
            remove(mEmptyResId);
            mEmptyResId = id;
        }
        return this;
    }

    public MyLoadingLayout setLoading(@LayoutRes int id) {
        if (mLoadingResId != id) {
            remove(mLoadingResId);
            mLoadingResId = id;
        }
        return this;
    }

    public MyLoadingLayout setError(@LayoutRes int id) {
        if (mErrorResId != id) {
            remove(mErrorResId);
            mErrorResId = id;
        }
        return this;
    }

    private void remove(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            View vg = mLayouts.remove(layoutId);
            removeView(vg);
        }
    }

    OnClickListener mRetryButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mRetryListener != null) {
                mRetryListener.onClick(v);
            }
        }
    };

    public MyLoadingLayout setRetryListener(OnClickListener listener) {
        mRetryListener = listener;
        return this;
    }

    private void show(int layoutId) {
        for (View view : mLayouts.values()) {
            view.setVisibility(GONE);
        }
        layout(layoutId).setVisibility(VISIBLE);
    }

    private View layout(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            return mLayouts.get(layoutId);
        }
        View layout = mInflater.inflate(layoutId, this, false);
        layout.setVisibility(GONE);
        addView(layout);
        mLayouts.put(layoutId, layout);

        if (layoutId == mEmptyResId) {

            TextView view = layout.findViewById(R.id.loading_empty_text);
            if (view != null && !TextUtils.isEmpty(mEmptyText)) {
                view.setText(mEmptyText);
                //view.setTextColor(mTextColor);
                //view.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            }
        } else if (layoutId == mErrorResId) {
            View retryBtn = layout.findViewById(R.id.loading_retry_button);
            if (retryBtn != null) {
                retryBtn.setOnClickListener(mRetryButtonClickListener);
            }
        }
        return layout;
    }

    @BindingAdapter(value = {"onClickRetry"}, requireAll = false)
    public static void onClickRetry(MyLoadingLayout view, final OnClickListener clickCommand) {
        view.setRetryListener(clickCommand);
    }
}
