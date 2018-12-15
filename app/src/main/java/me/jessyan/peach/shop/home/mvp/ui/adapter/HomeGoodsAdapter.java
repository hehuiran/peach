package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.goods.CouponsCommodityBean;
import me.jessyan.peach.shop.utils.GoodsUtils;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.peach.shop.utils.StringUtils;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;
import me.jessyan.peach.shop.vlayout.VirtualListItemAdapter;
import me.jessyan.peach.shop.widget.CouponsLayout;
import me.jessyan.peach.shop.widget.span.CenterImageSpan;

/**
 * author: Create by HuiRan on 2018/12/15 下午10:04
 * email: 15260828327@163.com
 * description:
 */
public class HomeGoodsAdapter extends VirtualListItemAdapter<CouponsCommodityBean.CommodityModel, VirtualItemViewHolder> {

    private final ImageLoader mImageLoader;
    private final int mSize;

    public HomeGoodsAdapter() {
        super(R.layout.item_home_main_goods);
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(Utils.getApp()).imageLoader();
        mSize = ScreenUtils.getScreenWidth() / 2;
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {
        CouponsCommodityBean.CommodityModel bean = mData.get(position);

        ImageView ivImg = holder.getView(R.id.iv_img);
        mImageLoader.loadImage(ivImg.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivImg)
                        .url(GoodsUtils.getSpecifiedSizeImageUrl(bean.getImg(), mSize))
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

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new GridLayoutHelper(2);
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.HOME_GOODS_TYPE;
    }
}
