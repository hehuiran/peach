package com.jess.arms.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 *
 */

public class DialogHelper {
    private View mContentView = null;

    private SparseArray<WeakReference<View>> mViews;

    public DialogHelper(Context context, int resId) {
        this();
        mContentView = LayoutInflater.from(context).inflate(resId, null);
    }

    public DialogHelper() {
        mViews = new SparseArray<>();
    }

    public void setContentView(View view) {
        mContentView = view;
    }

    //设置文本
    public void setText(int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(text);
        }
    }

    //设置点击事件
    public void setOnClick(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    //减少findVIewById的次数
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = null;
        if (viewWeakReference != null) {
            view = viewWeakReference.get();
        }
        //        View view = mViews.get(viewId).get();
        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<View>(view));
            }
        }
        return (T) view;
    }


    //获取视图
    public View getContentView() {
        return mContentView;
    }
}
