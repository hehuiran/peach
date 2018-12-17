package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.help.glide.GlideTopRoundTransform;
import me.jessyan.peach.shop.utils.GoodsUtils;
import me.jessyan.peach.shop.utils.ResourceUtils;

/**
 * author: Create by HuiRan on 2018/12/15 下午6:38
 * email: 15260828327@163.com
 * description:
 */
public class HomeOrientationItemAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private final ImageLoader mImageLoader;
    private final int mSize;

    public HomeOrientationItemAdapter(@Nullable List<GoodsBean> data) {
        super(R.layout.item_home_main_orientation_item, data);
        mImageLoader = ArmsUtils.getImageLoaderInstance(Utils.getApp());
        mSize = SizeUtils.dp2px(100);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        ImageView ivImg = helper.getView(R.id.iv_img);
        mImageLoader.loadImage(ivImg.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivImg)
                        .url(GoodsUtils.getSpecifiedSizeImageUrl(item.getImg(), mSize))
                        .transformation(new GlideTopRoundTransform())
                        .build());

        TextView tvOriginal = helper.getView(R.id.tv_original_price);
        String originalPrice = String.format(ResourceUtils.getResourceString(R.string.rmb), item.getOriginalPrice());
        tvOriginal.setText(originalPrice);
        tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        String discountPrice = String.format(ResourceUtils.getResourceString(R.string.discount_price), item.getDiscountPrice());

        helper.setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_discount_price,discountPrice);
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.HOME_ORIENTATION_ITEM_TYPE;
    }
}
