package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.help.glide.GlideRoundTransform;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.peach.shop.vlayout.VirtualItemAdapter;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;

/**
 * author: Create by HuiRan on 2018/12/15 下午2:55
 * email: 15260828327@163.com
 * description:
 */
public class HomeAdvertisingAdapter extends VirtualItemAdapter<VirtualItemViewHolder> {

    private CouponsBannerBean.BannerBean bannerBean;
    private final ImageLoader mImageLoader;
    private boolean isPaddingBottom;

    public HomeAdvertisingAdapter(boolean isPaddingBottom) {
        super(R.layout.item_home_main_advertising);
        this.isPaddingBottom = isPaddingBottom;
        mImageLoader = ArmsUtils.getImageLoaderInstance(Utils.getApp());
    }

    public void setData(CouponsBannerBean.BannerBean bannerBean, int position) {
        this.bannerBean = bannerBean;
        notifyItemChanged(position);
    }

    public CouponsBannerBean.BannerBean getBannerBean() {
        return bannerBean;
    }

    @Override
    public int getDefItemCount() {
        return bannerBean == null || bannerBean.getDataBean() == null ? 0 : 1;
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {

        View box = holder.getView(R.id.cl_box);
        int space = SizeUtils.dp2px(10);
        box.setPadding(space, space, space, isPaddingBottom ? space : 0);
        box.setBackgroundColor(isPaddingBottom ? Color.TRANSPARENT :
                ResourceUtils.getResourceColor(R.color.color_ff1673));

        String img = bannerBean.getDataBean().getImg();
        ImageView ivAd = holder.getView(R.id.iv_ad);
        mImageLoader.loadImage(ivAd.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivAd)
                        .url(img)
                        .transformation(new GlideRoundTransform())
                        .build());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
