package me.jessyan.peach.shop.vlayout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * author: Created by HuiRan on 2018/3/1 15:03
 * E-Mail: 15260828327@163.com
 * description:
 */

public abstract class VirtualListItemAdapter<T, K extends VirtualItemViewHolder> extends VirtualItemAdapter<K> {

    protected List<T> mData;

    public VirtualListItemAdapter(int layoutResId) {
        this(layoutResId, null);
    }

    public VirtualListItemAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId);
        this.mData = data == null ? new ArrayList<T>() : data;
    }

    /**
     * Get the data of list
     *
     * @return 列表数据
     */
    @NonNull
    public List<T> getData() {
        return mData;
    }

    /**
     * setting up a new instance to data;
     *
     * @param data
     */
    public void setNewData(@Nullable List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        try{
            notifyDataSetChanged();
        } catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    /**
     * add new data to the end of mData
     *
     * @param newData the new data collection
     */
    public void addData(@NonNull Collection<? extends T> newData) {
        mData.addAll(newData);
        notifyItemRangeInserted(mData.size() - newData.size(), newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    /**
     * compatible getLoadMoreViewCount and getEmptyViewCount may change
     *
     * @param size Need compatible data size
     */
    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = mData == null ? 0 : mData.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getDefItemCount() {
        return mData.size();
    }
}
