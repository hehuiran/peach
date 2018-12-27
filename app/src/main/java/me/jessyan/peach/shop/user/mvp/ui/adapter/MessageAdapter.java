package me.jessyan.peach.shop.user.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;

import java.util.ArrayList;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.MessageBean;
import me.jessyan.peach.shop.help.ImageLoaderHelper;
import me.jessyan.peach.shop.help.glide.GlideRoundTransform;
import me.jessyan.peach.shop.utils.ResourceUtils;

/**
 * author: Create by HuiRan on 2018/12/24 下午10:20
 * email: 15260828327@163.com
 * description:
 */
public class MessageAdapter extends BaseItemDraggableAdapter<MessageBean.DataBean, BaseViewHolder> {

    private final ImageLoader mImageLoader;

    public MessageAdapter() {
        super(new ArrayList<>());
        mImageLoader = ImageLoaderHelper.getImageLoader();
        setMultiTypeDelegate(new MultiTypeDelegate<MessageBean.DataBean>() {
            @Override
            protected int getItemType(MessageBean.DataBean dataBean) {
                return dataBean.getItemType();
            }
        });

        getMultiTypeDelegate()
                .registerItemType(RecyclerViewType.ACTIVITY_TYPE, R.layout.item_message_activity)
                .registerItemType(RecyclerViewType.SYSTEM_TYPE, R.layout.item_message_system)
                .registerItemType(RecyclerViewType.ALLIANCE_TYPE, R.layout.item_message_alliance)
                .registerItemType(RecyclerViewType.INCOME_TYPE, R.layout.item_message_income);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean.DataBean item) {
        switch (helper.getItemViewType()) {
            case RecyclerViewType.ACTIVITY_TYPE:
                fillActivity(helper, item);
                break;
            case RecyclerViewType.SYSTEM_TYPE:
                fillActivity(helper,item);
                break;
            case RecyclerViewType.ALLIANCE_TYPE:
                fillAlliance(helper,item);
                break;
            case RecyclerViewType.INCOME_TYPE:
                fillAlliance(helper, item);
                break;
        }
    }

    private void fillAlliance(BaseViewHolder helper, MessageBean.DataBean item) {
        helper.setText(R.id.tv_time, TimeUtils.getFriendlyTimeSpanByNow(item.getAddTime()));

        TextView tvContent = helper.getView(R.id.tv_content);
        String content = item.getSummary();
        int index = content.lastIndexOf("获得奖励");
        if (index != -1) {
            SpannableString spannable = new SpannableString(content);
            spannable.setSpan(new ForegroundColorSpan(ResourceUtils.getResourceColor(R.color.themeColor)), index + 4, content.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            tvContent.setText(spannable);
        } else {
            tvContent.setText(content);
        }

    }

    private void fillActivity(BaseViewHolder helper, MessageBean.DataBean item) {
        helper.setText(R.id.tv_time, TimeUtils.getFriendlyTimeSpanByNow(item.getAddTime()))
                .setText(R.id.tv_content, item.getSummary())
                .setGone(R.id.tv_content, !TextUtils.isEmpty(item.getSummary()))
                .setGone(R.id.tv_look_more, !TextUtils.isEmpty(item.getLinkurl()));

        ImageView ivImg = helper.getView(R.id.iv_img);
        if (TextUtils.isEmpty(item.getImg())) {
            ivImg.setVisibility(View.GONE);
        } else {
            ivImg.setVisibility(View.VISIBLE);
            mImageLoader.loadImage(ivImg.getContext(), ImageConfigImpl.builder()
                    .imageView(ivImg)
                    .url(item.getImg())
                    .transformation(new GlideRoundTransform(SizeUtils.dp2px(3)))
                    .build());
        }
    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.onItemSwiped(viewHolder, getViewHolderPosition(viewHolder));
        }
    }
}
