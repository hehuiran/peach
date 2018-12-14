package me.jessyan.peach.shop.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;

import me.jessyan.peach.shop.R;

/**
 * Created by Administrator on 2017/9/11.
 */

public class RecyclerLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.loadmore_footer;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.more_loading;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.more_fail;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.more_end;
    }

}
