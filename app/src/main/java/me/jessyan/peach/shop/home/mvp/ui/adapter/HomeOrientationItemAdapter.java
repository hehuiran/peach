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
import me.jessyan.peach.shop.entity.goods.OrientationGoodsBean;
import me.jessyan.peach.shop.utils.GoodsUtils;
import me.jessyan.peach.shop.utils.ResourceUtils;

/**
 * author: Create by HuiRan on 2018/12/15 下午6:38
 * email: 15260828327@163.com
 * description:
 */
public class HomeOrientationItemAdapter extends BaseQuickAdapter<OrientationGoodsBean.Data, BaseViewHolder> {

    private final ImageLoader mImageLoader;
    private final int mSize;

    public HomeOrientationItemAdapter(@Nullable List<OrientationGoodsBean.Data> data) {
        super(R.layout.item_home_main_orientation_item, data);
        mImageLoader = ArmsUtils.obtainAppComponentFromContext(Utils.getApp()).imageLoader();
        mSize = SizeUtils.dp2px(100);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrientationGoodsBean.Data item) {
        ImageView ivImg = helper.getView(R.id.iv_img);
        mImageLoader.loadImage(ivImg.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivImg)
                        .url(GoodsUtils.getSpecifiedSizeImageUrl(item.getItem_pic(), mSize))
                        .build());

        TextView tvOriginal = helper.getView(R.id.tv_original_price);
        String originalPrice = String.format(ResourceUtils.getResourceString(R.string.rmb), item.getItem_price());
        tvOriginal.setText(originalPrice);
        tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        String discountPrice = String.format(ResourceUtils.getResourceString(R.string.discount_price), item.getItem_endprice());

        helper.setText(R.id.tv_title,item.getItem_title())
                .setText(R.id.tv_discount_price,discountPrice);
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.HOME_ORIENTATION_ITEM_TYPE;
    }
}
