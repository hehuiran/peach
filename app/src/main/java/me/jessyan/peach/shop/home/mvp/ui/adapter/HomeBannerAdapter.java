package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.support.annotation.LayoutRes;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.help.BannerImageLoader;
import me.jessyan.peach.shop.vlayout.VirtualItemAdapter;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;

/**
 * author: Create by HuiRan on 2018/12/13 下午11:35
 * email: 15260828327@163.com
 * description:
 */
public class HomeBannerAdapter extends VirtualItemAdapter<VirtualItemViewHolder> {

    private List<CouponsBannerBean.BannerBean> mData;

    public HomeBannerAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    public void setData(List<CouponsBannerBean.BannerBean> data) {
        mData = data;
        notifyItemChanged(0);
    }

    @Override
    public int getDefItemCount() {
        return mData == null ? 0 : 1;
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {
        Banner banner = holder.getView(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new BannerImageLoader<CouponsBannerBean.BannerBean,String>() {
                    @Override
                    protected String getPath(CouponsBannerBean.BannerBean bean) {
                        return bean.getDataBean().getImg();
                    }
                })
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {

                    }
                })
//                .setBannerAnimation(ForegroundToBackgroundAlphaTransformer.class)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setImages(mData)
                .start();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.BANNER_TYPE;
    }
}
