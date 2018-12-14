package me.jessyan.peach.shop.vlayout;

import android.support.annotation.LayoutRes;
import android.util.SparseIntArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * author: Created by HuiRan on 2018/4/13 18:42
 * E-Mail: 15260828327@163.com
 * description:
 */

public abstract class VirtualMultiItemAdapter<T extends MultiItemEntity, K extends VirtualItemViewHolder> extends VirtualItemAdapter<K> {

    private SparseIntArray layouts;
    private static final int DEFAULT_VIEW_TYPE = 0;
    private static final int TYPE_NOT_FOUND = 0;
    protected T mMultiItemEntity;

    public T getMultiItemEntity() {
        return mMultiItemEntity;
    }

    public void setMultiItemEntity(T multiItemEntity) {
        this.mMultiItemEntity = multiItemEntity;
        notifyDataSetChanged();
    }

    public VirtualMultiItemAdapter(T multiItemEntity) {
        super(0);
        this.mMultiItemEntity = multiItemEntity;
    }

    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createVirtualViewHolder(parent, getLayoutId(viewType));
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

    @Override
    public int getDefItemCount() {
        return mMultiItemEntity == null || mMultiItemEntity.getItemType() == 0 ? 0 : 1;
    }

    @Override
    protected int getDefItemViewType(int position) {
        if (mMultiItemEntity != null) {
            int itemType = mMultiItemEntity.getItemType();
            if (itemType != 0) {
                return itemType;
            }
        }
        return DEFAULT_VIEW_TYPE;
    }
}
