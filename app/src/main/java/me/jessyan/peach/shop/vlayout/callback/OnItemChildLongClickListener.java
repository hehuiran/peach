package me.jessyan.peach.shop.vlayout.callback;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * author: Created by HuiRan on 2018/2/9 14:36
 * E-Mail: 15260828327@163.com
 * description:
 */

public interface OnItemChildLongClickListener {
    /**
     * callback method to be invoked when an item in this view has been
     * click and held
     *
     * @param view     The childView within the itemView that was clicked and held.
     * @param position The position of the view int the adapter
     * @return true if the callback consumed the long click ,false otherwise
     */
    boolean onItemChildLongClick(RecyclerView.Adapter adapter, View view, int position);
}
