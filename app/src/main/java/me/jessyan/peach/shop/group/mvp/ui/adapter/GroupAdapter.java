package me.jessyan.peach.shop.group.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.group.GroupBean;
import me.jessyan.peach.shop.help.ImageLoaderHelper;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.peach.shop.utils.StringUtils;
import me.jessyan.peach.shop.widget.span.CenterImageSpan;

/**
 * author: Create by HuiRan on 2019/1/1 下午2:23
 * email: 15260828327@163.com
 * description:
 */
public class GroupAdapter extends BaseQuickAdapter<GroupBean.DataBean, BaseViewHolder> {

    private final ImageLoader mImageLoader;

    public GroupAdapter() {
        super(R.layout.item_group);
        mImageLoader = ImageLoaderHelper.getImageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupBean.DataBean item) {
        ImageView ivImage = helper.getView(R.id.iv_img);
        mImageLoader.loadImage(ivImage.getContext(), ImageConfigImpl.builder()
                .url(item.getImgUrl())
                .imageView(ivImage)
                .build());

        String original = String.format(ResourceUtils.getResourceString(R.string.ju_hua_suan_price), StringUtils.keepTwoDecimal(item.getOriginalPrice()));
        String group = String.format(ResourceUtils.getResourceString(R.string.group_price), item.getGroupNum(), StringUtils.keepTwoDecimal(item.getGroupPrice()));

        helper.setText(R.id.tv_ju, original)
                .setText(R.id.tv_group_price, group);

        TextView tvTitle = helper.getView(R.id.tv_title);
        SpannableString titleSpannable = new SpannableString(" " + item.getTitle());
        Drawable drawable = ContextCompat.getDrawable(tvTitle.getContext(),
                item.getShopType() != null && item.getShopType().equals("1") ? R.mipmap.ic_tag_tmall : R.mipmap.ic_tag_taobao);
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
