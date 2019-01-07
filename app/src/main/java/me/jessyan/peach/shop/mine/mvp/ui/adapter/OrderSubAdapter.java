package me.jessyan.peach.shop.mine.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.mine.OrderSubBean;
import me.jessyan.peach.shop.help.ImageLoaderHelper;
import me.jessyan.peach.shop.help.glide.GlideRoundTransform;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.peach.shop.utils.StringUtils;
import me.jessyan.peach.shop.widget.span.CenterImageSpan;

/**
 * author: Create by HuiRan on 2018/12/31 下午5:44
 * email: 15260828327@163.com
 * description:
 */
public class OrderSubAdapter extends BaseQuickAdapter<OrderSubBean.DataBean, BaseViewHolder> {

    private final ImageLoader mImageLoader;

    public OrderSubAdapter() {
        super(R.layout.item_order_sub);
        mImageLoader = ImageLoaderHelper.getImageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderSubBean.DataBean item) {

        ImageView ivImg = helper.getView(R.id.iv_img);

        mImageLoader.loadImage(mContext,
                ImageConfigImpl.builder()
                        .imageView(ivImg)
                        .url(item.getImg())
                        .transformation(new GlideRoundTransform())
                        .build());

        SpannableString titleSpannable = new SpannableString(" " + item.getTitle());
        Drawable drawable = ContextCompat.getDrawable(mContext,
                !item.getOrderType().equals("淘宝") ? R.mipmap.ic_tag_tmall : R.mipmap.ic_tag_taobao);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            CenterImageSpan imageSpan = new CenterImageSpan(drawable);
            titleSpannable.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }

        int type = item.getType();
        String income = ResourceUtils.getResourceString(type == 0 ? R.string.has_save : R.string.estimated_income);

        String status = ResourceUtils.getResourceString(type == 0 ?
                R.string.has_settled :
                type == 1 ? R.string.has_failure :
                        R.string.has_payment);

        int statusColor = ResourceUtils.getResourceColor(type == 0 ?
                R.color.color_ff1673 :
                type == 1 ? R.color.color_808080 :
                        R.color.black);

        String orderAmount = String.format(ResourceUtils.getResourceString(R.string.order_amount), StringUtils.keepTwoDecimal(item.getAmount()));
        String incomeMoney = String.format(ResourceUtils.getResourceString(R.string.rmb), StringUtils.keepTwoDecimal(item.getIntegral()));
        String addTime = item.getAddTime();
        helper.setText(R.id.tv_time, addTime.split(" ")[0])
                .setText(R.id.tv_order_number, item.getRechargeNo())
                .setText(R.id.tv_income, income)
                .setText(R.id.tv_order_money, orderAmount)
                .setText(R.id.tv_income_money, incomeMoney)
                .setText(R.id.tv_status, status)
                .setTextColor(R.id.tv_status, statusColor)
                .setText(R.id.tv_title, titleSpannable);
    }
}
