package me.jessyan.peach.shop.widget.web;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.SizeUtils;

import me.jessyan.peach.shop.R;


/**
 * ================================================================
 * 版权: 爱车小屋所有（C） 2017
 * <p>
 * 作者：刘付文 （61128910@qq.com）
 * <p>
 * 时间: 2017-02-13 16:05
 * <p>
 * 描述：带进度条的WebView和一些初始化操作
 * ================================================================
 */
public class ProgressWebView extends LinearLayout {
    private ProgressBar progressbar;
    private WebView mWebView;

    public ProgressWebView(Context context) {
        this(context, null);
    }
    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addProgressbar(context);// 添加进度条
    }
    /**
     * 添加进度条
     */
    private void addProgressbar(Context context) {
        removeAllViews();
        setOrientation(VERTICAL);
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, SizeUtils.dp2px(2)));
        Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);
        progressbar.setProgressDrawable(drawable);
        addView(progressbar);
        mWebView = new WebView(context);
        mWebView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mWebView);
    }

    public WebView getWebView() {
        return mWebView;
    }

    public ProgressBar getProgressbar() {
        return progressbar;
    }
}