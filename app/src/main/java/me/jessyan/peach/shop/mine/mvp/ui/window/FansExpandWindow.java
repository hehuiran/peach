package me.jessyan.peach.shop.mine.mvp.ui.window;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.mine.FansExpandBean;
import me.jessyan.peach.shop.mine.mvp.ui.adapter.FansExpandAdapter;
import me.jessyan.peach.shop.utils.ResourceUtils;

/**
 * author: Create by HuiRan on 2018/12/16 下午8:21
 * email: 15260828327@163.com
 * description:
 */
public class FansExpandWindow extends PopupWindow {

    private final View mInflate;
    private final FansExpandAdapter mAdapter;

    public FansExpandWindow(Context context) {
        mInflate = LayoutInflater.from(context).inflate(R.layout.pop_fans_expand, null);
        mInflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissWindow();
            }
        });
        RecyclerView recyclerView = mInflate.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);

        mAdapter = new FansExpandAdapter(generateList(context));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mOnExpandItemClickListener != null) {
                    FansExpandBean bean = mAdapter.getData().get(position);
                    mOnExpandItemClickListener.onMoreChannelItemClick(bean.getType(), bean.getName());
                }
                dismissWindow();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void show(View targetView) {
        if (getContentView() == null) {
            setContentView(mInflate);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            int redundant = ResourceUtils.getResourceDimensionPixelSize(R.dimen.action_bar_height) + BarUtils.getStatusBarHeight() + targetView.getHeight();
            setHeight(ScreenUtils.getScreenHeight() - redundant);
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

    private OnExpandItemClickListener mOnExpandItemClickListener;

    public void setOnExpandItemClickListener(OnExpandItemClickListener onExpandItemClickListener) {
        mOnExpandItemClickListener = onExpandItemClickListener;
    }

    public interface OnExpandItemClickListener {
        void onMoreChannelItemClick(String type, String name);
    }

    private List<FansExpandBean> generateList(Context context) {
        List<FansExpandBean> list = new ArrayList<>(7);
        String[] names = context.getResources().getStringArray(R.array.my_fans_category_tab_array);
        String[] types = context.getResources().getStringArray(R.array.my_fans_category_type_array);
        for (int i = 0; i < names.length; i++) {
            FansExpandBean bean = new FansExpandBean();
            bean.setName(names[i]);
            bean.setType(types[i]);
            list.add(bean);
        }
        return list;
    }
}
