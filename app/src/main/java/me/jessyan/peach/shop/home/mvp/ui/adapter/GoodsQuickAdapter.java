package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.help.glide.GlideTopRoundTransform;
import me.jessyan.peach.shop.utils.GoodsUtils;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.peach.shop.utils.StringUtils;
import me.jessyan.peach.shop.widget.CouponsLayout;
import me.jessyan.peach.shop.widget.span.CenterImageSpan;

/**
 * author: Create by HuiRan on 2018/12/22 下午3:55
 * email: 15260828327@163.com
 * description:
 */
public class GoodsQuickAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private boolean isLinear;
    private final ImageLoader mImageLoader;
    private final int mGridSize, mLinearSize;
    private final int mRounderRadius;

    public GoodsQuickAdapter() {
        super(new ArrayList<>());
        mImageLoader = ArmsUtils.getImageLoaderInstance(Utils.getApp());
        mGridSize = ScreenUtils.getScreenWidth() / 2;
        mLinearSize = SizeUtils.dp2px(90);
        mRounderRadius = SizeUtils.dp2px(7);
        setMultiTypeDelegate(new MultiTypeDelegate<GoodsBean>() {
            @Override
            protected int getItemType(GoodsBean bean) {
                return isLinear ? RecyclerViewType.GOODS_LINEAR : RecyclerViewType.GOODS_GRID;
            }
        });

        getMultiTypeDelegate()
                .registerItemType(RecyclerViewType.GOODS_LINEAR, R.layout.item_linear_goods)
                .registerItemType(RecyclerViewType.GOODS_GRID, R.layout.item_home_main_goods);
    }

    public void setLinear(boolean linear) {
        isLinear = linear;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        switch (helper.getItemViewType()) {
            case RecyclerViewType.GOODS_LINEAR:
                fillLinear(helper, item);
                break;
            case RecyclerViewType.GOODS_GRID:
                fillGrid(helper, item);
                break;
        }
    }

    private void fillLinear(BaseViewHolder holder, GoodsBean bean) {
        ImageView ivImg = holder.getView(R.id.iv_img);
        mImageLoader.loadImage(ivImg.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivImg)
                        .url(GoodsUtils.getSpecifiedSizeImageUrl(bean.getImg(), mLinearSize))
                        .build());


        String commissionMoney = String.format(ResourceUtils.getResourceString(R.string.estimate_the_commission),
                StringUtils.keepTwoDecimal(bean.getTkMoney()));

        String originalPrice = String.format(ResourceUtils.getResourceString(R.string.original_price_format),
                StringUtils.keepTwoDecimal(bean.getOriginalPrice()));

        String discountPrice = String.format(ResourceUtils.getResourceString(R.string.rmb),
                StringUtils.keepTwoDecimal(bean.getDiscountPrice()));

        String soldCount = String.format(ResourceUtils.getResourceString(R.string.sold_count),
                StringUtils.keepTwoDecimal(bean.getSoldCount()));

        SpannableString discountSpan = new SpannableString(discountPrice);
        discountSpan.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        holder.setText(R.id.tv_commission, commissionMoney)
                .setText(R.id.tv_original_price, originalPrice)
                .setText(R.id.tv_discount_price, discountSpan)
                .setText(R.id.tv_sold_count, soldCount);

        CouponsLayout couponsLayout = holder.getView(R.id.coupons_layout);
        couponsLayout.setMoney(bean.getCouponmoney());

        TextView tvTitle = holder.getView(R.id.tv_title);
        SpannableString titleSpannable = new SpannableString(bean.getTitle());
        Drawable drawable = ContextCompat.getDrawable(tvTitle.getContext(),
                bean.getShoptype() != null && bean.getShoptype().equals("B") ? R.mipmap.ic_tag_tmall : R.mipmap.ic_tag_taobao);
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        CenterImageSpan imageSpan = new CenterImageSpan(drawable);
        titleSpannable.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvTitle.setText(titleSpannable);
    }

    private void fillGrid(BaseViewHolder holder, GoodsBean bean) {
        ImageView ivImg = holder.getView(R.id.iv_img);
        mImageLoader.loadImage(ivImg.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivImg)
                        .url(GoodsUtils.getSpecifiedSizeImageUrl(bean.getImg(), mGridSize))
                        .transformation(new GlideTopRoundTransform(mRounderRadius))
                        .build());


        String commissionMoney = String.format(ResourceUtils.getResourceString(R.string.estimate_the_commission),
                StringUtils.keepTwoDecimal(bean.getTkMoney()));

        String originalPrice = String.format(ResourceUtils.getResourceString(R.string.original_price_format),
                StringUtils.keepTwoDecimal(bean.getOriginalPrice()));

        String discountPrice = String.format(ResourceUtils.getResourceString(R.string.rmb),
                StringUtils.keepTwoDecimal(bean.getDiscountPrice()));

        String soldCount = String.format(ResourceUtils.getResourceString(R.string.sold_count),
                StringUtils.keepTwoDecimal(bean.getSoldCount()));

        holder.setText(R.id.tv_commission, commissionMoney)
                .setText(R.id.tv_original_price, originalPrice)
                .setText(R.id.tv_discount_price, discountPrice)
                .setText(R.id.tv_sold_count, soldCount);

        CouponsLayout couponsLayout = holder.getView(R.id.coupons_layout);
        couponsLayout.setMoney(bean.getCouponmoney());

        TextView tvTitle = holder.getView(R.id.tv_title);
        SpannableString titleSpannable = new SpannableString(bean.getTitle());
        Drawable drawable = ContextCompat.getDrawable(tvTitle.getContext(),
                bean.getShoptype() != null && bean.getShoptype().equals("B") ? R.mipmap.ic_tag_tmall : R.mipmap.ic_tag_taobao);
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        CenterImageSpan imageSpan = new CenterImageSpan(drawable);
        titleSpannable.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvTitle.setText(titleSpannable);
    }
}
