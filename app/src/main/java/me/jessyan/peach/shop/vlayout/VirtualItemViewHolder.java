package me.jessyan.peach.shop.vlayout;

import android.support.annotation.IdRes;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

import me.jessyan.peach.shop.callback.OnSingleClickListener;

/**
 * author: Created by HuiRan on 2018/2/9 14:43
 * E-Mail: 15260828327@163.com
 * description:
 */

public class VirtualItemViewHolder extends BaseViewHolder {

    private VirtualItemAdapter adapter;

    public VirtualItemViewHolder(View view) {
        super(view);
    }

    public void setAdapter(VirtualItemAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * add childView id
     *
     * @param viewId add the child view id   can support childview click
     * @return if you use adapter bind listener
     * @link {(adapter.setOnItemChildClickListener(listener))}
     * <p>
     * or if you can use  recyclerView.addOnItemTouch(listerer)  wo also support this menthod
     */
    @SuppressWarnings("unchecked")
    public VirtualItemViewHolder addOnClickListener(@IdRes final int viewId) {
        final View view = getView(viewId);
        if (view != null) {
            if (!view.isClickable()) {
                view.setClickable(true);
            }
            view.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onClicked(View v) {
                    if (adapter.getOnItemChildClickListener() != null) {
                        adapter.getOnItemChildClickListener().onItemChildClick(adapter, v, getLayoutPosition());
                    }
                }
            });
        }

        return this;
    }

    @SuppressWarnings("unchecked")
    public VirtualItemViewHolder addOnClickListener(View view) {
        if (view != null) {
            if (!view.isClickable()) {
                view.setClickable(true);
            }
            view.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onClicked(View v) {
                    if (adapter.getOnItemChildClickListener() != null) {
                        adapter.getOnItemChildClickListener().onItemChildClick(adapter, v, getLayoutPosition());
                    }
                }
            });
        }

        return this;
    }

    /**
     * add long click view id
     *
     * @param viewId
     * @return if you use adapter bind listener
     * @link {(adapter.setOnItemChildLongClickListener(listener))}
     * <p>
     * or if you can use  recyclerView.addOnItemTouch(listerer)  wo also support this menthod
     */
    @SuppressWarnings("unchecked")
    public VirtualItemViewHolder addOnLongClickListener(@IdRes final int viewId) {
        final View view = getView(viewId);
        if (view != null) {
            if (!view.isLongClickable()) {
                view.setLongClickable(true);
            }
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return adapter.getOnItemChildLongClickListener() != null &&
                            adapter.getOnItemChildLongClickListener().onItemChildLongClick(adapter, v, getLayoutPosition());
                }
            });
        }
        return this;
    }
}
