package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.BaseLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.home.HomeSectionBean;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;
import me.jessyan.peach.shop.vlayout.VirtualListItemAdapter;

/**
 * author: Create by HuiRan on 2018/12/15 下午3:22
 * email: 15260828327@163.com
 * description:
 */
public class HomeSectionAdapter extends VirtualListItemAdapter<HomeSectionBean, VirtualItemViewHolder> {

    private final ImageLoader mImageLoader;
    private int[] textColors = new int[4];
    private int[][] gradientColor = new int[4][2];

    public HomeSectionAdapter() {
        super(R.layout.item_home_main_section);
        mImageLoader = ArmsUtils.getImageLoaderInstance(Utils.getApp());
        textColors[0] = ResourceUtils.getResourceColor(R.color.color_ff006c);
        textColors[1] = ResourceUtils.getResourceColor(R.color.color_9416ff);
        textColors[2] = ResourceUtils.getResourceColor(R.color.color_ff9000);
        textColors[3] = ResourceUtils.getResourceColor(R.color.color_0006ff);

        gradientColor[0][0] = ResourceUtils.getResourceColor(R.color.color_ff1673);
        gradientColor[0][1] = ResourceUtils.getResourceColor(R.color.color_ff50c3);

        gradientColor[1][0] = ResourceUtils.getResourceColor(R.color.color_c001ff);
        gradientColor[1][1] = ResourceUtils.getResourceColor(R.color.color_ea5fff);

        gradientColor[2][0] = ResourceUtils.getResourceColor(R.color.color_ffb400);
        gradientColor[2][1] = ResourceUtils.getResourceColor(R.color.color_FF7139);

        gradientColor[3][0] = ResourceUtils.getResourceColor(R.color.color_6b49ff);
        gradientColor[3][1] = ResourceUtils.getResourceColor(R.color.color_5d96ff);
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {
        HomeSectionBean bean = mData.get(position);

        holder.setText(R.id.tv_title, bean.getTitle())
                .setTextColor(R.id.tv_title, textColors[position])
                .setText(R.id.tv_des, bean.getDes())
                .setGone(R.id.line_h, position % 2 == 0)
                .setGone(R.id.line_v, position > 1);

        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                gradientColor[position]);
        gradientDrawable.setCornerRadius(SizeUtils.dp2px(10));
        holder.getView(R.id.tv_des).setBackground(gradientDrawable);

        ImageView ivImg = holder.getView(R.id.iv_img);
        mImageLoader.loadImage(ivImg.getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivImg)
                        .url(bean.getImg())
                        .build());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        int space = SizeUtils.dp2px(10);
        gridLayoutHelper.setPadding(space, space, space, space);
        gridLayoutHelper.setLayoutViewHelper(new BaseLayoutHelper.DefaultLayoutViewHelper(new BaseLayoutHelper.LayoutViewBindListener() {
            @Override
            public void onBind(View layoutView, BaseLayoutHelper baseLayoutHelper) {
                layoutView.setBackgroundResource(R.drawable.layer_margin_10_white_radius_10);
            }
        }, new BaseLayoutHelper.LayoutViewUnBindListener() {
            @Override
            public void onUnbind(View layoutView, BaseLayoutHelper baseLayoutHelper) {
                layoutView.setBackground(null);
            }
        }));
        return gridLayoutHelper;
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.HOME_SECTION_TYPE;
    }
}
