package me.jessyan.peach.shop.mine.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.mine.FansExpandBean;

/**
 * author: Create by HuiRan on 2018/12/31 下午9:33
 * email: 15260828327@163.com
 * description:
 */
public class FansExpandAdapter extends BaseQuickAdapter<FansExpandBean, BaseViewHolder> {
    public FansExpandAdapter(@Nullable List<FansExpandBean> data) {
        super(R.layout.item_fans_expand, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FansExpandBean item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
