package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.BaseLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.goods.CouponsChannelSubBean;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;
import me.jessyan.peach.shop.vlayout.VirtualListItemAdapter;

/**
 * author: Create by HuiRan on 2018/12/15 下午1:54
 * email: 15260828327@163.com
 * description:
 */
public class HomeChannelAdapter extends VirtualListItemAdapter<CouponsChannelSubBean, VirtualItemViewHolder> {

    private final ImageLoader mImageLoader;
    private final int space;

    public HomeChannelAdapter() {
        super(R.layout.item_home_main_channel);
        mImageLoader = ArmsUtils.getImageLoaderInstance(Utils.getApp());
        space = SizeUtils.dp2px(10);
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {
        CouponsChannelSubBean channelBean = mData.get(position);
        int top = position < 5 ? space : 0;
        int bottom = space;
        holder.setText(R.id.tv_channel, channelBean.getTitle())
                .getView(R.id.ll_box)
                .setPadding(0, top, 0, bottom);

        ImageView ivChannel = holder.getView(R.id.iv_channel);
        mImageLoader.loadImage(ivChannel.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivChannel)
                        .url(channelBean.getImg())
                        .build());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(5);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setPadding(space, space, space, 0);
        gridLayoutHelper.setLayoutViewHelper(new BaseLayoutHelper.DefaultLayoutViewHelper(new BaseLayoutHelper.LayoutViewBindListener() {
            @Override
            public void onBind(View layoutView, BaseLayoutHelper baseLayoutHelper) {
                layoutView.setBackgroundResource(R.drawable.layer_margin_10_white_radius_5);
            }
        }, new BaseLayoutHelper.LayoutViewUnBindListener() {
            @Override
            public void onUnbind(View layoutView, BaseLayoutHelper baseLayoutHelper) {
                layoutView.setBackground(null);
            }
        }));
        return gridLayoutHelper;
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.HOME_CHANNEL_TYPE;
    }
}
