package me.jessyan.peach.shop.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.blankj.utilcode.util.Utils;

/**
 * author: Created by HuiRan on 2018/5/24 10:09
 * E-Mail: 15260828327@163.com
 * description:
 */

public final class ResourceUtils {

    private ResourceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     *
     * @param colorId the resources color id
     * @return {@link android.graphics.Color}
     */
    public static int getResourceColor(@ColorRes int colorId) {
        return getResources().getColor(colorId);
    }

    public static int getResourceDimensionPixelSize(@DimenRes int dimensionId){
        return getResources().getDimensionPixelSize(dimensionId);
    }

    public static float getResourceDimension(@DimenRes int dimensionId){
        return getResources().getDimension(dimensionId);
    }

    public static String getResourceString(@StringRes int stringId){
        return getResources().getString(stringId);
    }

    public static Drawable getResourceDrawable(@DrawableRes int mipmapId){
        return getResources().getDrawable(mipmapId);
    }

    private static Resources getResources(){
        return Utils.getApp().getResources();
    }
}
