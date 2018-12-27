package me.jessyan.peach.shop.category.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryTitleBean;
import me.jessyan.peach.shop.utils.ResourceUtils;

/**
 * author: Create by HuiRan on 2018/12/23 下午5:14
 * email: 15260828327@163.com
 * description:
 */
public class CategoryParentAdapter extends BaseQuickAdapter<GoodsCategoryTitleBean.DataBean, BaseViewHolder> {

    public CategoryParentAdapter() {
        super(R.layout.item_category_parent);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsCategoryTitleBean.DataBean item) {
        helper.itemView.setBackgroundColor(ResourceUtils.getResourceColor(item.isChecked() ?
                R.color.white : R.color.color_f5f5f5));

        helper.setGone(R.id.v_decoration, item.isChecked())
                .setText(R.id.tv_title, item.getTypename())
                .setTextColor(R.id.tv_title, ResourceUtils.getResourceColor(item.isChecked() ? R.color.black : R.color.color_202020));
    }
}
