package com.zwc.baselibrary.binding.viewadapter.text;

import android.graphics.Typeface;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

/**
 * ClassName ViewAdapter
 * User: zuoweichen
 * Date: 2021/5/8 9:52
 * Description: 描述
 */
public class ViewAdapter {
    @BindingAdapter("invalidate")
    public static void invalidate(TextView view, boolean tp) {
        view.postInvalidate();
    }

    @BindingAdapter({"action", "delayMillis"})
    public static void postDelayed(TextView view, Runnable action, long delayMillis) {
        view.postDelayed(action, delayMillis);
    }

    @BindingAdapter("removeCallbacks")
    public static void removeCallbacks(TextView view, Runnable action) {
        view.removeCallbacks(action);
    }

    @BindingAdapter("setBold")
    public static void setBold(TextView view, boolean isBold) {
        view.setTypeface(null, isBold ? Typeface.BOLD : Typeface.NORMAL);
    }
}

