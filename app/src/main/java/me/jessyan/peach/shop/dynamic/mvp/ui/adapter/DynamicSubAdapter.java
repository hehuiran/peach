package me.jessyan.peach.shop.dynamic.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;

import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.DynamicBean;
import me.jessyan.peach.shop.help.ImageLoaderHelper;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.peach.shop.utils.StringUtils;
import me.jessyan.peach.shop.widget.nine.NineGridView;
import me.jessyan.peach.shop.widget.nine.NineGridViewAdapter;

/**
 * author: Create by HuiRan on 2018/12/26 上午12:19
 * email: 15260828327@163.com
 * description:
 */
public class DynamicSubAdapter extends BaseQuickAdapter<DynamicBean.ListBean, BaseViewHolder> {

    private final ImageLoader mImageLoader;
    private final NineGridViewImageLoader mNineGridViewImageLoader;

    public DynamicSubAdapter() {
        super(R.layout.item_dynamic_sub);
        mImageLoader = ImageLoaderHelper.getImageLoader();
        mNineGridViewImageLoader = new NineGridViewImageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicBean.ListBean item) {
        ImageView ivAvatar = helper.getView(R.id.iv_avatar);
        mImageLoader.loadImage(ivAvatar.getContext(), ImageConfigImpl.builder()
                .imageView(ivAvatar)
                .url(item.getHeadImg())
                .build());

        String discount = String.format(ResourceUtils.getResourceString(R.string.rmb), StringUtils.keepTwoDecimal(item.getDiscountPrice()));
        String commission = String.format(ResourceUtils.getResourceString(R.string.estimate_the_commission), StringUtils.keepTwoDecimal(item.getTkMoney()));
        long millis = StringUtils.parseLong(item.getAddTime()) * 1000L;
        helper.setText(R.id.tv_nickname, item.getAuthor())
                .setText(R.id.tv_time, TimeUtils.getFriendlyTimeSpanByNow(millis))
                .setText(R.id.tv_share, String.valueOf(item.getShareNum()))
                .setText(R.id.tv_content, item.getContent())
                .setText(R.id.tv_discount_price, discount)
                .setText(R.id.tv_commission, commission)
                .setText(R.id.tv_comment, item.getComment());

        List<String> smallImages = item.getSmallImages();
        NineGridView nineGridView = helper.getView(R.id.nine_grid_view);
        if (smallImages != null && !smallImages.isEmpty()) {
            nineGridView.setVisibility(View.VISIBLE);
            nineGridView.setImageLoader(mNineGridViewImageLoader);
            NineGridViewAdapter<String> adapter = new NineGridViewAdapter<String>(smallImages) {
                @Override
                protected void onImageItemClick(Context context, View view, NineGridView nineGridView, int index, List<String> imageInfo) {
                    super.onImageItemClick(context, view, nineGridView, index, imageInfo);
                    if (mOnImageClickListener != null) {
                        mOnImageClickListener.onImageClick(view, imageInfo.get(index));
                    }
                }
            };
            nineGridView.setAdapter(adapter);
        } else {
            nineGridView.setVisibility(View.GONE);
        }
    }

    private static class NineGridViewImageLoader implements NineGridView.ImageLoader {

        private ImageLoader mImageLoader;

        @Override
        public void onDisplayImage(Context context, ImageView imageView, Object data) {
            if (mImageLoader == null) {
                mImageLoader = ImageLoaderHelper.getImageLoader();
            }

            mImageLoader.loadImage(context, ImageConfigImpl.builder()
                    .imageView(imageView)
                    .url(((String) data))
                    .build());
        }
    }

    private OnImageClickListener mOnImageClickListener;

    public interface OnImageClickListener {
        void onImageClick(View view, String url);
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }
}
