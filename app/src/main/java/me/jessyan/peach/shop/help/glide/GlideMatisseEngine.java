package me.jessyan.peach.shop.help.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.zhihu.matisse.engine.ImageEngine;

import me.jessyan.peach.shop.help.ImageLoaderHelper;

/**
 * author: Create by HuiRan on 2018/12/29 上午12:03
 * email: 15260828327@163.com
 * description:
 */
public class GlideMatisseEngine implements ImageEngine {

    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        ImageLoader imageLoader = ImageLoaderHelper.getImageLoader();
        imageLoader.loadImage(context, ImageConfigImpl
                .builder()
                .url(uri)
                .imageView(imageView)
                .isCenterCrop(true)
                .width(resize)
                .height(resize)
                .placeholder(placeholder)
                .build());
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        ImageLoader imageLoader = ImageLoaderHelper.getImageLoader();
        imageLoader.loadImage(context, ImageConfigImpl
                .builder()
                .url(uri)
                .imageView(imageView)
                .isCenterCrop(true)
                .width(resize)
                .height(resize)
                .placeholder(placeholder)
                .build());
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        ImageLoader imageLoader = ImageLoaderHelper.getImageLoader();
        imageLoader.loadImage(context, ImageConfigImpl
                .builder()
                .url(uri)
                .imageView(imageView)
                .width(resizeX)
                .height(resizeY)
                .priority(Priority.HIGH)
                .isFitCenter(true)
                .build());
    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        ImageLoader imageLoader = ImageLoaderHelper.getImageLoader();
        imageLoader.loadImage(context, ImageConfigImpl
                .builder()
                .url(uri)
                .asGif()
                .imageView(imageView)
                .width(resizeX)
                .height(resizeY)
                .priority(Priority.HIGH)
                .build());
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }
}
