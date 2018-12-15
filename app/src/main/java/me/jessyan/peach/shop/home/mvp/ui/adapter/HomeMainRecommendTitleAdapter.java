package me.jessyan.peach.shop.home.mvp.ui.adapter;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.vlayout.VirtualItemAdapter;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;

/**
 * author: Create by HuiRan on 2018/12/15 下午9:52
 * email: 15260828327@163.com
 * description:
 */
public class HomeMainRecommendTitleAdapter extends VirtualItemAdapter<VirtualItemViewHolder> {

    private boolean isShow;

    public HomeMainRecommendTitleAdapter() {
        super(R.layout.item_home_main_goods_title);
    }

    public void setShown(boolean show, int position) {
        isShow = show;
        notifyItemChanged(position);
    }

    @Override
    public int getDefItemCount() {
        return isShow ? 1 : 0;
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.HOME_GOODS_TITLE_TYPE;
    }
}
