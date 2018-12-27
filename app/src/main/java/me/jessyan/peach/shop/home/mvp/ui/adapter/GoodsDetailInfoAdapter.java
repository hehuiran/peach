package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.home.GoodsDetailInfoBean;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.peach.shop.utils.StringUtils;
import me.jessyan.peach.shop.vlayout.VirtualItemAdapter;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;
import me.jessyan.peach.shop.widget.span.CenterImageSpan;

/**
 * author: Create by HuiRan on 2018/12/17 下午5:42
 * email: 15260828327@163.com
 * description:
 */
public class GoodsDetailInfoAdapter extends VirtualItemAdapter<VirtualItemViewHolder> {

    private GoodsDetailInfoBean mBean;
    private int absolutePosition;

    public GoodsDetailInfoAdapter() {
        super(R.layout.item_goods_detail_info);
    }

    public void setData(GoodsDetailInfoBean goodsDetailInfoBean, int position) {
        mBean = goodsDetailInfoBean;
        notifyItemChanged(position);
    }

    public int getAbsolutePosition() {
        return absolutePosition;
    }

    public GoodsDetailInfoBean getData() {
        return mBean;
    }

    @Override
    public int getDefItemCount() {
        return mBean == null ? 0 : 1;
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {
        this.absolutePosition = absolutePosition;
        TextView tvCollection = holder.getView(R.id.tv_collection);
        tvCollection.setSelected(mBean.isCollection());

        String originalPrice = String.format(ResourceUtils.getResourceString(R.string.coupon_before_the_price),
                StringUtils.keepTwoDecimal(mBean.getOriginalPrice()));

        String soldCount = String.format(ResourceUtils.getResourceString(R.string.sold_count),
                StringUtils.keepTwoDecimal(mBean.getSoldCount()));

        holder.setText(R.id.tv_original_price, originalPrice)
                .setText(R.id.tv_discount_price, StringUtils.keepTwoDecimal(mBean.getDiscountPrice()))
                .setText(R.id.tv_sold_count, soldCount);

        TextView tvTitle = holder.getView(R.id.tv_title);
        SpannableString titleSpannable = new SpannableString(mBean.getTitle());
        Drawable drawable = ContextCompat.getDrawable(tvTitle.getContext(),
                mBean.getShopType() != null && mBean.getShopType().equals("B") ? R.mipmap.ic_tag_tmall : R.mipmap.ic_tag_taobao);
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        CenterImageSpan imageSpan = new CenterImageSpan(drawable);
        titleSpannable.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvTitle.setText(titleSpannable);

        holder.addOnClickListener(R.id.tv_collection);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.GOODS_DETAIL_INFO_TYPE;
    }
}
