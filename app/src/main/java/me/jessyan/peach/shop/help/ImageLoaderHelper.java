package me.jessyan.peach.shop.help;

import com.blankj.utilcode.util.Utils;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

/**
 * author: Create by HuiRan on 2018/12/24 下午10:32
 * email: 15260828327@163.com
 * description:
 */
public final class ImageLoaderHelper {

    private ImageLoaderHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static ImageLoader getImageLoader(){
        return ArmsUtils.getImageLoaderInstance(Utils.getApp());
    }
}
