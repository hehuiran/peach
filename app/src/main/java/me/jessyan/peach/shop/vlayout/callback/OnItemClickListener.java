package me.jessyan.peach.shop.vlayout.callback;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * author: Created by HuiRan on 2018/2/9 14:32
 * E-Mail: 15260828327@163.com
 * description:
 */

public interface OnItemClickListener {

    /**
     * Callback method to be invoked when an item in this RecyclerView has
     * been clicked.
     *
     * @param adapter  the adapter
     * @param view     The itemView within the RecyclerView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     */
    void onItemClick(RecyclerView.Adapter adapter, View view, int position);
}
