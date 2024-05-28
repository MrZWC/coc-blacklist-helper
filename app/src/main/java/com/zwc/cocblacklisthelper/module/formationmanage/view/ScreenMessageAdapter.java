package com.zwc.cocblacklisthelper.module.formationmanage.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwc.cocblacklisthelper.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import io.github.idonans.lang.util.ViewUtil;

public class ScreenMessageAdapter extends RecyclerView.Adapter<ScreenMessageAdapter.VH> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    ///
    private List<BaseScreen> mList = new ArrayList<>();

    public ScreenMessageAdapter(Context context, List<BaseScreen> list) {
        this.mContext = context;
        this.mList = list;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(mLayoutInflater.inflate(R.layout.item_screen_message_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        BaseScreen baseScreen = mList.get(position);
        if (baseScreen.selected) {
            holder.selectedImage.setVisibility(View.VISIBLE);
            holder.title.setTextColor(0xff12b5b0);
        } else {
            holder.selectedImage.setVisibility(View.INVISIBLE);
            holder.title.setTextColor(ContextCompat.getColor(mContext, R.color.TextTitleColor));
        }
        holder.title.setText(baseScreen.title);
        ViewUtil.onClick(holder.itemView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(baseScreen);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView selectedImage;

        public VH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            selectedImage = itemView.findViewById(R.id.selected_image);

        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(BaseScreen screen);
    }
}
