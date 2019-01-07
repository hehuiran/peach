package me.jessyan.peach.shop.widget.web;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


/**
 * WebChromeClientImpl
 * H5打开相机相册的回调监听
 * 进度条的回调监听
 */

public class WebChromeClientImpl extends WebChromeClient {
    /**
     * H5打开相机相册的回调监听
     */
    private OpenFileChooserListener listener;
    /**
     * 进度条的回调监听
     */
    private OnWebChromeListener onWebChromeListener;

    /**
     * 进度发生改变
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (onWebChromeListener != null) {
            onWebChromeListener.onProgressChanged(view, newProgress);
        }
    }

    /**
     * 接收到标题
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (onWebChromeListener != null) {
            onWebChromeListener.onReceivedTitle(view, title);
        }
    }


    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (listener != null) {
            listener.openFileChooser(uploadMsg, null);
        }
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        if (listener != null) {
            listener.openFileChooser(uploadMsg, null);
        }
    }

    // For Android  > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        if (listener != null) {
            listener.openFileChooser(uploadMsg, null);
        }
    }

    // For Android  >= 5.0
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if (listener != null) {
            listener.openFileChooser(null, filePathCallback);
        }
        return true;
    }

    public interface OpenFileChooserListener {
        void openFileChooser(ValueCallback<Uri> uploadMsg, ValueCallback<Uri[]> filePathCallback);
    }

    public void setOpenFileChooserListener(OpenFileChooserListener listener) {
        this.listener = listener;
    }

    public interface OnWebChromeListener {
        void onReceivedTitle(WebView view, String title);

        void onProgressChanged(WebView view, int newProgress);
    }

    public void setOnWebChromeListener(OnWebChromeListener onWebChromeListener) {
        this.onWebChromeListener = onWebChromeListener;
    }

}
