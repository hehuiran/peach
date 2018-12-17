package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.home.GoodsDetailSellerBean;
import me.jessyan.peach.shop.help.glide.GlideRoundTransform;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.peach.shop.vlayout.VirtualItemAdapter;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;

/**
 * author: Create by HuiRan on 2018/12/17 下午5:42
 * email: 15260828327@163.com
 * description:
 */
public class GoodsDetailSellerAdapter extends VirtualItemAdapter<VirtualItemViewHolder> {

    private GoodsDetailSellerBean mBean;
    private final ImageLoader mImageLoader;

    public GoodsDetailSellerAdapter() {
        super(R.layout.item_goods_detail_seller);
        mImageLoader = ArmsUtils.getImageLoaderInstance(Utils.getApp());
    }

    public void setData(GoodsDetailSellerBean bean, int position) {
        mBean = bean;
        notifyItemChanged(position);
    }

    @Override
    public int getDefItemCount() {
        return mBean == null ? 0 : 1;
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {

        List<GoodsDetailSellerBean.EvaluatesBean> evaluates = mBean.getEvaluates();
        GoodsDetailSellerBean.EvaluatesBean evaluatesBean1 = evaluates.get(0);
        GoodsDetailSellerBean.EvaluatesBean evaluatesBean2 = evaluates.get(1);
        GoodsDetailSellerBean.EvaluatesBean evaluatesBean3 = evaluates.get(2);
        holder.setText(R.id.tv_shop_name, mBean.getShopName())
                .setText(R.id.tv_goods_des, evaluatesBean1.getTitle().concat(ResourceUtils.getResourceString(R.string.colon)))
                .setText(R.id.tv_goods_score, evaluatesBean1.getScore())
                .setText(R.id.tv_goods_lever, evaluatesBean1.getLevelText())
                .setTextColor(R.id.tv_goods_lever, Color.parseColor(evaluatesBean1.getLevelTextColor()))
                .setTextColor(R.id.tv_goods_score, Color.parseColor(evaluatesBean1.getLevelTextColor()))
                .setText(R.id.tv_seller_des, evaluatesBean2.getTitle().concat(ResourceUtils.getResourceString(R.string.colon)))
                .setText(R.id.tv_seller_score, evaluatesBean2.getScore())
                .setText(R.id.tv_seller_lever, evaluatesBean2.getLevelText())
                .setTextColor(R.id.tv_seller_lever, Color.parseColor(evaluatesBean2.getLevelTextColor()))
                .setTextColor(R.id.tv_seller_score, Color.parseColor(evaluatesBean2.getLevelTextColor()))
                .setText(R.id.tv_ship_des, evaluatesBean3.getTitle().concat(ResourceUtils.getResourceString(R.string.colon)))
                .setText(R.id.tv_ship_score, evaluatesBean3.getScore())
                .setText(R.id.tv_ship_lever, evaluatesBean3.getLevelText())
                .setTextColor(R.id.tv_ship_lever, Color.parseColor(evaluatesBean3.getLevelTextColor()))
                .setTextColor(R.id.tv_ship_score, Color.parseColor(evaluatesBean3.getLevelTextColor()));

        holder.getView(R.id.tv_goods_lever).setBackground(generateGradientDrawable(evaluatesBean1.getLevelBackgroundColor()));
        holder.getView(R.id.tv_seller_lever).setBackground(generateGradientDrawable(evaluatesBean2.getLevelBackgroundColor()));
        holder.getView(R.id.tv_ship_lever).setBackground(generateGradientDrawable(evaluatesBean3.getLevelBackgroundColor()));

        ImageView ivShop = holder.getView(R.id.iv_shop);
        mImageLoader.loadImage(ivShop.getContext(), ImageConfigImpl.builder()
                .imageView(ivShop)
                .url(mBean.getShopIcon())
                .transformation(new GlideRoundTransform(SizeUtils.dp2px(3)))
                .build());

        String shopType = mBean.getShopType();
        ImageView ivShopType = holder.getView(R.id.iv_shop_type);
        mImageLoader.loadImage(ivShopType.getContext(), ImageConfigImpl.builder()
                .imageView(ivShopType)
                .url(shopType.equals("C") ? mBean.getCreditLevelIcon() : R.mipmap.ic_goods_detail_seller_tmall)
                .build());
    }

    private GradientDrawable generateGradientDrawable(String colorValue) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(SizeUtils.sp2px(2));
        drawable.setColor(Color.parseColor(colorValue));
        return drawable;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.GOODS_DETAIL_SELLER_TYPE;
    }
}
