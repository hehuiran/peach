package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.vlayout.VirtualItemAdapter;
import me.jessyan.peach.shop.vlayout.VirtualItemViewHolder;

/**
 * author: Create by HuiRan on 2018/12/15 下午5:28
 * email: 15260828327@163.com
 * description:
 */
public class HomeOrientationAdapter extends VirtualItemAdapter<VirtualItemViewHolder> {

    private List<GoodsBean> mData;
    private RecyclerView.RecycledViewPool pool;

    public HomeOrientationAdapter(RecyclerView.RecycledViewPool pool) {
        super(R.layout.item_home_main_orientation);
        this.pool = pool;
    }

    public void setData(List<GoodsBean> data, int position) {
        mData = data;
        notifyItemChanged(position);
    }

    @Override
    public int getDefItemCount() {
        return mData == null || mData.isEmpty() ? 0 : 1;
    }

    @Override
    protected void convert(VirtualItemViewHolder holder, int position, int absolutePosition) {
        RecyclerView recyclerView = holder.getView(R.id.recycler_view);

        recyclerView.setFocusableInTouchMode(false);
        recyclerView.setRecycledViewPool(pool);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        HomeOrientationItemAdapter itemAdapter = new HomeOrientationItemAdapter(mData);
        if (mOnItemClickListener != null) {
            itemAdapter.setOnItemClickListener(mOnItemClickListener);
        }
        recyclerView.setAdapter(itemAdapter);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @Override
    protected int getDefItemViewType(int position) {
        return RecyclerViewType.HOME_ORIENTATION_TYPE;
    }

    private BaseQuickAdapter.OnItemClickListener mOnItemClickListener;

    public void setQuickAdapterOnItemClickListener(BaseQuickAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
