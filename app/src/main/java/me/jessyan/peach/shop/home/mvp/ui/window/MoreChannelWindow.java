package me.jessyan.peach.shop.home.mvp.ui.window;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryTitleBean;
import me.jessyan.peach.shop.home.mvp.ui.adapter.MoreChannelAdapter;

/**
 * author: Create by HuiRan on 2018/12/16 下午8:21
 * email: 15260828327@163.com
 * description:
 */
public class MoreChannelWindow extends PopupWindow {

    private final View mInflate;

    public MoreChannelWindow(Context context, List<GoodsCategoryTitleBean.DataBean> data) {
        mInflate = LayoutInflater.from(context).inflate(R.layout.pop_more_channel, null);
        mInflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissWindow();
            }
        });
        RecyclerView recyclerView = mInflate.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerView.setHasFixedSize(true);

        MoreChannelAdapter adapter = new MoreChannelAdapter(data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mOnMoreChannelItemClickListener != null) {
                    mOnMoreChannelItemClickListener.onMoreChannelItemClick(position + 1);
                }
                dismissWindow();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void show(View targetView) {
        if (getContentView() == null) {
            setContentView(mInflate);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ScreenUtils.getScreenHeight() - targetView.getHeight());
            setFocusable(true);
            setOutsideTouchable(true);
            setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setAnimationStyle(R.style.dialog_from_alpha_anim);
        }
        showAsDropDown(targetView);
    }

    public void dismissWindow() {
        if (isShowing()) {
            dismiss();
        }
    }

    private OnMoreChannelItemClickListener mOnMoreChannelItemClickListener;

    public void setOnMoreChannelItemClickListener(OnMoreChannelItemClickListener onMoreChannelItemClickListener) {
        mOnMoreChannelItemClickListener = onMoreChannelItemClickListener;
    }

    public interface OnMoreChannelItemClickListener {
        void onMoreChannelItemClick(int position);
    }
}
