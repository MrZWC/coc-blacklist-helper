package com.zwc.baselibrary.binding.viewadapter.recyclerview;

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.idonans.core.util.DimenUtil;


/**
 * This class is from the v7 samples of the Android SDK. It's not by me!
 * <p/>
 * See the license above for details.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;


    private Drawable mDivider;
    private Context context;
    private int mOrientation;
    private float height;
    private int paddingLength = 0;
    private int paddingLeft = 0;
    private int paddingRight = 0;
    private boolean isShowBottomDivider = false;

    /**
     * @param resId       color资源id
     * @param height      分割线高度 //单位dp
     * @param orientation 分割线方向
     *                    created at 2016/9/22 12:23
     * @author zwc
     **/
    public DividerItemDecoration(Context context,  int orientation, int resId, float height) {
        if (context == null) {
            return;
        }
        mDivider = new ColorDrawable(context.getResources().getColor(resId));
        this.context = context;
        this.height = height;
        setOrientation(orientation);
    }

    /**
     * @param resId         color资源id
     * @param height        分割线高度 //单位dp
     * @param orientation   分割线方向
     * @param paddingLength padding 距离
     *                      created at 2016/9/22 12:23
     * @author zwc
     **/
    public DividerItemDecoration(Context context, int orientation, int resId, float height, int paddingLength) {
        //mDivider = context.getResources().getDrawable(resId);
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = new ColorDrawable(context.getResources().getColor(resId));
        //  mDivider = a.getDrawable(0);
        this.context = context;
        this.height = height;
        this.paddingLength = paddingLength;
        a.recycle();
        setOrientation(orientation);
    }

    /**
     * @param orientation  方向
     * @param resId        颜色id
     * @param height       间隔线高度
     * @param paddingLeft  间隔线距离左边宽度
     * @param paddingRight 间隔线距离右边宽度
     * @作者 zuowichen
     * @创建时间 2018/7/5 15:07
     **/
    public DividerItemDecoration(Context context, int orientation, int resId, float height, int paddingLeft, int paddingRight) {
        //mDivider = context.getResources().getDrawable(resId);
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = new ColorDrawable(context.getResources().getColor(resId));
        //  mDivider = a.getDrawable(0);
        this.context = context;
        this.height = height;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent, state);
        } else {
            drawHorizontal(c, parent, state);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
        try {
            final int left = parent.getPaddingLeft() + DimenUtil.dp2px(paddingLength)
                    + DimenUtil.dp2px(paddingLeft);
            final int right = parent.getWidth() - parent.getPaddingRight()
                    - DimenUtil.dp2px(paddingLength)
                    - DimenUtil.dp2px(paddingRight);
            final int childCount = parent.getChildCount();
            final int lastPosition = state.getItemCount() - 1;
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                if (child == null) {
                    return;
                }
                final int childRealPosition = parent.getChildAdapterPosition(child);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                // final int bottom = top + mDivider.getIntrinsicHeight();
                final int bottom = top + DimenUtil.dp2px(height);
                if (isShowBottomDivider || childRealPosition < lastPosition) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        } catch (Exception e) {

        }

    }

    public void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
        try {
            final int top = parent.getPaddingTop() + DimenUtil.dp2px(paddingLength) + DimenUtil.dp2px(paddingLeft);
            final int bottom = parent.getHeight() - parent.getPaddingBottom()
                    - DimenUtil.dp2px(paddingLength)
                    - DimenUtil.dp2px(paddingRight);

            final int childCount = parent.getChildCount();
            final int lastPosition = state.getItemCount() - 1;
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                if (child == null) {
                    return;
                }
                final int childRealPosition = parent.getChildAdapterPosition(child);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + DimenUtil.dp2px(height);
                if (childRealPosition < lastPosition) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        } catch (Exception e) {

        }

    }

    //如果是最后一行不绘制
    private boolean isLastRaw(RecyclerView parent, int pos, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (!isShowBottomDivider && layoutManager instanceof LinearLayoutManager) {
            if (pos >= childCount - 1)// 如果是最后一行，则不需要绘制底部
                return true;
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition,
                               RecyclerView parent) {
        int childCount = parent.getAdapter().getItemCount();
        if (isLastRaw(parent, itemPosition, childCount)) {
            return;
        }
        if (mOrientation == VERTICAL_LIST) {
            // outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            outRect.set(0, 0, 0, DimenUtil.dp2px(height));
        } else {
            outRect.set(0, 0, DimenUtil.dp2px(height), 0);
        }
    }


    public void setShowBottomDivider(boolean showBottomDivider) {
        isShowBottomDivider = showBottomDivider;
    }
}
