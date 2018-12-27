package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.search.SearchHotValueBean;

/**
 * author: Create by HuiRan on 2018/12/22 下午1:41
 * email: 15260828327@163.com
 * description:
 */
public class SearchHotAdapter extends TagAdapter<SearchHotValueBean> {
    public SearchHotAdapter(List<SearchHotValueBean> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, SearchHotValueBean bean) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.search_tv, parent, false);
        textView.setText(bean.getValue());
        return textView;
    }
}
