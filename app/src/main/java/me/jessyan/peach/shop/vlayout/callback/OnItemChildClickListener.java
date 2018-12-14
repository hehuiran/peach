package me.jessyan.peach.shop.vlayout.callback;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * author: Created by HuiRan on 2018/2/9 14:35
 * E-Mail: 15260828327@163.com
 * description:
 */

public interface OnItemChildClickListener {
    /**
     * callback method to be invoked when an item in this view has been
     * click and held
     *
     * @param adapter  the adapter
     * @param view     The view within the ItemView that was clicked
     * @param position The position of the view int the adapter
     */
    void onItemChildClick(RecyclerView.Adapter adapter, View view, int position);
}
