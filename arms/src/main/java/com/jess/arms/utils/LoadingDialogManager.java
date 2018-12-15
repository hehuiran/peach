package com.jess.arms.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jess.arms.dialog.BaseDialog;
import com.jess.arms.widget.PointLoadingView;

import java.util.HashMap;

import timber.log.Timber;

/**
 * author: Create by HuiRan on 2018/12/11 下午11:42
 * email: 15260828327@163.com
 * description:
 */
public final class LoadingDialogManager {

    private static final String TAG = "LoadingDialogManager";
    private static HashMap<Context, BaseDialog> sMap = new HashMap<>();

    public static void showLoading(@NonNull Context context) {
        if (sMap.containsKey(context)) {
            Timber.tag(TAG).w("context is exists...");
            return;
        }
        BaseDialog dialog = new BaseDialog.Builder(context)
                .setContentView(new PointLoadingView(context))
                .setCanceledOnTouchOutside(false)
                .setWindowAlpha(0f)
                .show();
        sMap.put(context, dialog);
    }

    public static void hideLoading(@NonNull Context context) {
        if (sMap.containsKey(context)) {
            BaseDialog dialog = sMap.get(context);
            if (dialog != null) {
                dialog.dismiss();
            }
            sMap.remove(context);
        }
    }
}
