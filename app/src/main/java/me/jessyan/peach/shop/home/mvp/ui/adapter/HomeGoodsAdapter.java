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
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
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
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;
import me.jessyan.peach.shop.vlayout.VirtualMultiListItemAdapter;
import me.jessyan.peach.shop.widget.CouponsLayout;
import me.jessyan.peach.shop.widget.span.CenterImageSpan;

/**
 * author: Create by HuiRan on 2018/12/15 下午10:04
 * email: 15260828327@163.com
 * description:
 */
public class HomeGoodsAdapter extends VirtualMultiListItemAdapter<GoodsBean, VirtualItemViewHolder> {

    private final ImageLoader mImageLoader;
    private final int mSize;
    private final int mRounderRadius;

    public HomeGoodsAdapter() {
        super(new ArrayList<>());
        addItemType(RecyclerViewType.HOME_GOODS_TYPE, R.layout.item_home_main_goods);
        addItemType(RecyclerViewType.EMPTY_TYPE, R.layout.empty_layout);
        addItemType(RecyclerViewType.NET_ERROR_TYPE, R.layout.net_error_layout);
        mImageLoader = ArmsUtils.getImageLoaderInstance(Utils.getApp());
        mSize = ScreenUtils.getScreenWidth() / 2;
        mRounderRadius = SizeUtils.dp2px(7);
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {
        GoodsBean bean = mData.get(position);
        switch (bean.getItemType()) {
            case RecyclerViewType.HOME_GOODS_TYPE:
                fillGoods(holder, bean);
                break;
            case RecyclerViewType.NET_ERROR_TYPE:
                fillNetError(holder);
                break;
        }

    }

    private void fillGoods(VirtualItemViewHolder holder, GoodsBean bean) {
        ImageView ivImg = holder.getView(R.id.iv_img);
        mImageLoader.loadImage(ivImg.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivImg)
                        .url(GoodsUtils.getSpecifiedSizeImageUrl(bean.getImg(), mSize))
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

    private void fillNetError(VirtualItemViewHolder holder) {
        holder.addOnClickListener(R.id.tv_reload);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int relativePosition = position - getStartPosition();
                if (relativePosition < mData.size()) {
                    int itemType = mData.get(relativePosition).getItemType();
                    return itemType == RecyclerViewType.HOME_GOODS_TYPE ? 1 : 2;
                }
                return 0;
            }
        });
        return gridLayoutHelper;
    }
}
