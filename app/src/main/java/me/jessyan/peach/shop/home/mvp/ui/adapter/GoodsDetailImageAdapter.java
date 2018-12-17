package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.home.GoodsDetailImageBean;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;
import me.jessyan.peach.shop.vlayout.VirtualListItemAdapter;

/**
 * author: Create by HuiRan on 2018/12/17 下午8:16
 * email: 15260828327@163.com
 * description:
 */
public class GoodsDetailImageAdapter extends VirtualListItemAdapter<GoodsDetailImageBean, VirtualItemViewHolder> {

    private final ImageLoader mImageLoader;

    public GoodsDetailImageAdapter() {
        super(R.layout.item_goods_detail_image);
        mImageLoader = ArmsUtils.getImageLoaderInstance(Utils.getApp());
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {
        GoodsDetailImageBean bean = mData.get(position);

        ImageView ivImg = holder.getView(R.id.iv_img);
        ViewGroup.LayoutParams layoutParams = ivImg.getLayoutParams();
        layoutParams.height = bean.getHeight();
        ivImg.setLayoutParams(layoutParams);

        mImageLoader.loadImage(ivImg.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivImg)
                        .url(bean.getImgUrl())
                        .build());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.GOODS_DETAIL_IMAGE_TYPE;
    }
}
