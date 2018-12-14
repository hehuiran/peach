package me.jessyan.peach.shop.utils;

import android.text.TextUtils;

/**
 * author: Create by HuiRan on 2018/12/9 下午12:53
 * email: 15260828327@163.com
 * description:
 */
public final class GoodsUtils {

    private GoodsUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String getSpecifiedSizeImageUrl(String originalImageUrl, int size) {
        String sizeString = getSizeString(size);
        return TextUtils.isEmpty(originalImageUrl)
                || originalImageUrl.contains("haodanku")
                || originalImageUrl.endsWith(sizeString) ? originalImageUrl : originalImageUrl.concat(sizeString);

    }

    public static String getSizeString(int size) {
        String sizeString = String.valueOf(size);
        return "_".concat(sizeString).concat("x").concat(sizeString);
    }
}
