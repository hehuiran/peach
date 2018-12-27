package me.jessyan.peach.shop.category.mvp.ui.adapter;

import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryGridBean;

/**
 * author: Create by HuiRan on 2018/12/23 下午5:32
 * email: 15260828327@163.com
 * description:
 */
public class CategoryChildAdapter extends BaseQuickAdapter<GoodsCategoryGridBean.DataBean, BaseViewHolder> {

    private final ImageLoader mImageLoader;
    private static final int SPAN_COUNT = 3;
    private final int space15, space10;

    public CategoryChildAdapter() {
        super(R.layout.item_home_main_channel);
        mImageLoader = ArmsUtils.getImageLoaderInstance(Utils.getApp());
        space15 = SizeUtils.dp2px(15);
        space10 = SizeUtils.dp2px(10);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsCategoryGridBean.DataBean item) {
        int position = helper.getAdapterPosition() - getHeaderLayoutCount();
        int size = mData.size();
        int value = size / SPAN_COUNT;
        int row = size % SPAN_COUNT == 0 ? value : value + 1;
        int index = position / SPAN_COUNT;
        int top = index < 1 ? space15 : space10;
        int bottom = index >= row - 1 ? space15 : 0;
        helper.setText(R.id.tv_channel, item.getTitle())
                .getView(R.id.ll_box)
                .setPadding(0, top, 0, bottom);

        ImageView ivChannel = helper.getView(R.id.iv_channel);
        mImageLoader.loadImage(ivChannel.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivChannel)
                        .url(item.getImage())
                        .build());
    }
}
