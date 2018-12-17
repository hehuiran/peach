package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.text.TextUtils;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.vlayout.VirtualItemAdapter;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;

/**
 * author: Create by HuiRan on 2018/12/17 下午8:04
 * email: 15260828327@163.com
 * description:
 */
public class GoodsDetailDecorationAdapter extends VirtualItemAdapter<VirtualItemViewHolder> {

    private String title;

    public GoodsDetailDecorationAdapter() {
        super(R.layout.item_goods_detail_decoration);
    }

    public void setTitle(String title, int position) {
        this.title = title;
        notifyItemChanged(position);
    }

    @Override
    public int getDefItemCount() {
        return TextUtils.isEmpty(title) ? 0 : 1;
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {
        holder.setText(R.id.tv_title, title);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.GOODS_DETAIL_DECORATION_TYPE;
    }
}
