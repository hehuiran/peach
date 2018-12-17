package me.jessyan.peach.shop.launcher.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import me.jessyan.peach.shop.callback.OnSingleClickListener;

/**
 * author: Created by HuiRan on 2018/1/26 19:47
 * E-Mail: 15260828327@163.com
 * description:
 */

public class GuidePagerAdapter extends PagerAdapter {

    private List<Integer> mList;
    private final ImageLoader mImageLoader;

    public GuidePagerAdapter(List<Integer> list) {
        mList = list;
        mImageLoader = ArmsUtils.getImageLoaderInstance(Utils.getApp());
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        if (mOnItemClickListener != null) {
            imageView.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onClicked(View v) {
                    mOnItemClickListener.onItemClick();
                }
            });
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageLoader.loadImage(container.getContext(),
                ImageConfigImpl.builder()
                        .imageView(imageView)
                        .url(mList.get(position))
                        .build());
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick();
    }
}
