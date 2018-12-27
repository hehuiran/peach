package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryGridBean;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;
import me.jessyan.peach.shop.vlayout.VirtualListItemAdapter;

/**
 * author: Create by HuiRan on 2018/12/15 下午1:54
 * email: 15260828327@163.com
 * description:
 */
public class HomeCategoryChannelAdapter extends VirtualListItemAdapter<GoodsCategoryGridBean.DataBean, VirtualItemViewHolder> {

    private final ImageLoader mImageLoader;
    private static final int SPAN_COUNT = 5;
    private final int space15,space10;

    public HomeCategoryChannelAdapter() {
        super(R.layout.item_home_main_channel);
        mImageLoader = ArmsUtils.getImageLoaderInstance(Utils.getApp());
        space15 = SizeUtils.dp2px(15);
        space10 = SizeUtils.dp2px(10);
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {
        GoodsCategoryGridBean.DataBean channelBean = mData.get(position);
        int size = mData.size();
        int value = size / SPAN_COUNT;
        int row = size % SPAN_COUNT == 0 ? value : value + 1;
        int index = position / SPAN_COUNT;
        int top = index < 1 ? space15 : space10;
        int bottom = index >= row - 1 ? space15 : 0;
        holder.setText(R.id.tv_channel, channelBean.getTitle())
                .getView(R.id.ll_box)
                .setPadding(0, top, 0, bottom);

        ImageView ivChannel = holder.getView(R.id.iv_channel);
        mImageLoader.loadImage(ivChannel.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivChannel)
                        .url(channelBean.getImage())
                        .build());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(SPAN_COUNT);
        gridLayoutHelper.setAutoExpand(false);
        return gridLayoutHelper;
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.HOME_CHANNEL_TYPE;
    }
}
