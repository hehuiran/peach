package me.jessyan.peach.shop.entity.mine;


import java.util.ArrayList;
import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * author: Created by HuiRan on 2018/4/19 10:53
 * E-Mail: 15260828327@163.com
 * description:
 */

public class IncomeReportsTransformBean {
    private List<MultiItemBean> mData;
    private boolean hasData;

    public IncomeReportsTransformBean(List<MultiItemBean> data, boolean hasData) {
        this.mData = data;
        this.hasData = hasData;
    }

    public List<MultiItemBean> getData() {
        return mData == null ? new ArrayList<MultiItemBean>() : mData;
    }

    public boolean isHasData() {
        return hasData;
    }
}
