package me.jessyan.peach.shop.mine.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.mine.CommonProblemBean;

/**
 * author: Create by HuiRan on 2019/1/2 上午1:22
 * email: 15260828327@163.com
 * description:
 */
public class CommonProblemAdapter extends BaseQuickAdapter<CommonProblemBean, BaseViewHolder> {

    public CommonProblemAdapter(@Nullable List<CommonProblemBean> data) {
        super(R.layout.item_common_problem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonProblemBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        Drawable drawable = ContextCompat.getDrawable(mContext, item.getIcon());
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            tvTitle.setCompoundDrawables(null, drawable, null, null);
        }
        tvTitle.setText(item.getTitle());

        helper.setText(R.id.tv_top_problem, item.getTopProblem())
                .setText(R.id.tv_bottom_problem, item.getBottomProblem());
    }
}
