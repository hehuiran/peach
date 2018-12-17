package me.jessyan.peach.shop.home.mvp.ui.adapter;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.vlayout.VirtualItemAdapter;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;
import me.jessyan.peach.shop.widget.StickyLayout;

/**
 * author: Create by HuiRan on 2018/12/16 下午12:52
 * email: 15260828327@163.com
 * description:
 */
public class HomeCategoryStickyAdapter extends VirtualItemAdapter<VirtualItemViewHolder> {

    private boolean shown;
    private int absolutePosition;

    public HomeCategoryStickyAdapter() {
        super(R.layout.item_home_category_sticky);
    }

    public void setShown(boolean shown, int position) {
        this.shown = shown;
        notifyItemChanged(position);
    }

    public int getAbsolutePosition() {
        return absolutePosition;
    }

    @Override
    public int getDefItemCount() {
        return shown ? 1 : 0;
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {
        this.absolutePosition = absolutePosition;
        StickyLayout stickyLayout = holder.getView(R.id.sticky_layout);
        if (mOnStickyTabChangeListener != null) {
            stickyLayout.setOnStickyTabChangeListener(mOnStickyTabChangeListener);
        }
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new StickyLayoutHelper();
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.STICKY_TYPE;
    }

    private StickyLayout.OnStickyTabChangeListener mOnStickyTabChangeListener;

    public void setOnStickyTabChangeListener(StickyLayout.OnStickyTabChangeListener onStickyTabChangeListener) {
        mOnStickyTabChangeListener = onStickyTabChangeListener;
    }
}
