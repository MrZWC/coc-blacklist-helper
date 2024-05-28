package com.zwc.cocblacklisthelper.module.formationmanage.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;


import com.zwc.cocblacklisthelper.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.github.idonans.core.util.DimenUtil;

public class ScreenMessagePupWindow {
    private Context mContext;
    private PopupWindow mPopWindow;
    private RecyclerView recyclerView;
    private List<BaseScreen> datas = new ArrayList<>();
    private ScreenMessageAdapter mAdapter;

    public ScreenMessagePupWindow(Context activity) {
        this.mContext = activity;
    }

    public void showPopupWindow(View parentView, List<BaseScreen> datas, int xoff) {
        this.datas = datas;
        if (mPopWindow == null) {
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.pupwindow_screen_message_layout, null);
            mPopWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            initView(contentView);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        show(parentView, xoff);
    }

    private void show(View parentView, int xoff) {
        mPopWindow.showAsDropDown(parentView, xoff, DimenUtil.dp2px(6));
    }

    public void dismiss() {
        if (mPopWindow != null) {
            mPopWindow.dismiss();
        }
    }

    public void notifyData() {
        if (mAdapter != null && mPopWindow.isShowing()) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initView(View contentView) {
        recyclerView = contentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ScreenMessageAdapter(mContext, datas);
        mAdapter.setOnItemClickListener(new ScreenMessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseScreen screen) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(screen);
                }
                dismiss();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(BaseScreen screen);
    }
}
