package me.jessyan.peach.shop.mine.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.mine.MyFansSubBean;
import me.jessyan.peach.shop.help.ImageLoaderHelper;
import me.jessyan.peach.shop.help.UserGradeHelper;
import me.jessyan.peach.shop.utils.ResourceUtils;

/**
 * author: Create by HuiRan on 2018/12/31 下午7:49
 * email: 15260828327@163.com
 * description:
 */
public class MyFansSubAdapter extends BaseQuickAdapter<MyFansSubBean.DataBean, BaseViewHolder> {

    private final ImageLoader mImageLoader;

    public MyFansSubAdapter() {
        super(R.layout.item_my_fans_sub);
        mImageLoader = ImageLoaderHelper.getImageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFansSubBean.DataBean item) {
        ImageView ivAvatar = helper.getView(R.id.iv_avatar);
        mImageLoader.loadImage(ivAvatar.getContext(), ImageConfigImpl.builder()
                .imageView(ivAvatar)
                .url(item.getIcon())
                .isCircle(true)
                .build());

        String time = String.format(ResourceUtils.getResourceString(R.string.register_time), item.getTime().split(" ")[0]);
        helper.setText(R.id.tv_nickname, item.getName())
                .setText(R.id.tv_mobile, item.getMobile())
                .setText(R.id.tv_level, UserGradeHelper.getUserGradeNameByLevel(item.getUsergrade()))
                .setText(R.id.tv_time, time);
    }
}
