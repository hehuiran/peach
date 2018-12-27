package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.search.SearchRecordBean;

/**
 * author: Create by HuiRan on 2018/12/22 下午1:41
 * email: 15260828327@163.com
 * description:
 */
public class SearchRecordAdapter extends TagAdapter<SearchRecordBean> {
    public SearchRecordAdapter(List<SearchRecordBean> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, SearchRecordBean searchRecordBean) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.search_tv, parent, false);
        textView.setText(searchRecordBean.getValue());
        return textView;
    }
}
