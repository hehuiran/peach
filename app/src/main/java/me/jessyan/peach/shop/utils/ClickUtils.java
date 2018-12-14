package me.jessyan.peach.shop.utils;

import android.support.annotation.NonNull;
import android.view.View;

import me.jessyan.peach.shop.R;


/**
 * author: Created by HuiRan on 2017/12/12 15:57
 * E-Mail: 15260828327@163.com
 * description:
 */

public final class ClickUtils {

    private ClickUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    // 两次点击按钮之间的点击间隔不能少于600毫秒
    private static final int MIN_CLICK_DELAY_TIME = 600;

    public static boolean isNormalClick(@NonNull View view) {
        Object tag = view.getTag(R.id.click_tag);
        long lastClickTime = ((tag != null) ? (long) tag : 0);
        long curClickTime = System.currentTimeMillis();
        if (curClickTime - lastClickTime > MIN_CLICK_DELAY_TIME) {//过滤掉600毫秒内的连续点击
            view.setTag(R.id.click_tag, curClickTime);
            return true;
        }
        return false;
    }
}
