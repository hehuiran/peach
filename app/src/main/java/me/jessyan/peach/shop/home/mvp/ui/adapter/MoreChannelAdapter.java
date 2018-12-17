package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryTitleBean;

/**
 * author: Create by HuiRan on 2018/12/16 下午8:27
 * email: 15260828327@163.com
 * description:
 */
public class MoreChannelAdapter extends BaseQuickAdapter<GoodsCategoryTitleBean.DataBean, BaseViewHolder> {

    private final ImageLoader mImageLoader;
    private final int space;

    public MoreChannelAdapter(@Nullable List<GoodsCategoryTitleBean.DataBean> data) {
        super(R.layout.item_home_main_channel, data);
        mImageLoader = ArmsUtils.getImageLoaderInstance(Utils.getApp());
        space = SizeUtils.dp2px(12);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsCategoryTitleBean.DataBean item) {
        helper.setText(R.id.tv_channel, item.getTypename())
                .getView(R.id.ll_box)
                .setPadding(0, space, 0, space);

        ImageView ivChannel = helper.getView(R.id.iv_channel);
        mImageLoader.loadImage(ivChannel.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivChannel)
                        .url(item.getImage())
                        .build());
    }
}
