package com.zwc.baselibrary.binding.viewadapter.image;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes", "skipMemoryCache"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes, boolean skipMemoryCache) {
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions();
            if (placeholderRes != 0) {
                options = options.placeholder(placeholderRes);
            }
            if (skipMemoryCache) {
                options = options.skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE);
            }
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(placeholderRes)
                    .into(imageView);
        }
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }
}

