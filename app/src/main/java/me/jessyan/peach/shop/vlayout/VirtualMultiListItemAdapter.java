package me.jessyan.peach.shop.vlayout;

import android.support.annotation.LayoutRes;
import android.util.SparseIntArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * author: Created by HuiRan on 2018/2/26 17:00
 * E-Mail: 15260828327@163.com
 * description:
 */

public abstract class VirtualMultiListItemAdapter<T extends MultiItemEntity, K extends VirtualItemViewHolder> extends VirtualListItemAdapter<T, K> {

    protected final String TAG = this.getClass().getSimpleName();
    /**
     * layouts indexed with their types
     */
    private SparseIntArray layouts;

    private static final int DEFAULT_VIEW_TYPE = 0;
    private static final int TYPE_NOT_FOUND = 0;

    public VirtualMultiListItemAdapter(List<T> data) {
        super(0, data);
    }

    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createVirtualViewHolder(parent, getLayoutId(viewType));
    }

    @Override
    protected int getDefItemViewType(int position) {
        T item = mData.get(position);
        if (item != null) {
            return item.getItemType();
        }
        return DEFAULT_VIEW_TYPE;
    }

    private int getLayoutId(int viewType) {
        return layouts.get(viewType, TYPE_NOT_FOUND);
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseIntArray();
        }
        layouts.put(type, layoutResId);
    }
}
