package me.jessyan.peach.shop.help;

import android.content.Context;
import android.widget.ImageView;

import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.youth.banner.loader.ImageLoader;

/**
 * author: Create by HuiRan on 2018/12/13 下午11:41
 * email: 15260828327@163.com
 * description:
 */
public abstract class BannerImageLoader<T, V> extends ImageLoader {

    private com.jess.arms.http.imageloader.ImageLoader mImageLoader;

    @SuppressWarnings("unchecked")
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (mImageLoader == null) {
            mImageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader();
        }
        mImageLoader.loadImage(context, ImageConfigImpl.builder()
                .imageView(imageView)
                .url(getPath((T) path))
                .build());
    }

    protected abstract V getPath(T t);
}
