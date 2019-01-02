package me.jessyan.peach.shop.widget.nine;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import me.jessyan.peach.shop.R;

/**
 * author: Create by HuiRan on 2018/12/25 下午11:55
 * email: 15260828327@163.com
 * description:
 */
public abstract class NineGridViewAdapter<T> {

    private List<T> imageInfo;

    public NineGridViewAdapter(List<T> imageInfo) {
        this.imageInfo = imageInfo;
    }

    /**
     * 如果要实现图片点击的逻辑，重写此方法即可
     *
     * @param context      上下文
     * @param nineGridView 九宫格控件
     * @param index        当前点击图片的的索引
     * @param imageInfo    图片地址的数据集合
     */
    protected void onImageItemClick(Context context, View view, NineGridView nineGridView, int index, List<T> imageInfo) {
    }

    /**
     * 生成ImageView容器的方式，默认使用NineGridImageViewWrapper类，即点击图片后，图片会有蒙板效果
     * 如果需要自定义图片展示效果，重写此方法即可
     *
     * @param context 上下文
     * @return 生成的 ImageView
     */
    protected ImageView generateImageView(Context context) {
        NineGridViewWrapper imageView = new NineGridViewWrapper(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(context.getString(R.string.transition_name_image));
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.ic_default_color);
        return imageView;
    }

    public List<T> getImageInfo() {
        return imageInfo;
    }

    public void setImageInfoList(List<T> imageInfo) {
        this.imageInfo = imageInfo;
    }
}
