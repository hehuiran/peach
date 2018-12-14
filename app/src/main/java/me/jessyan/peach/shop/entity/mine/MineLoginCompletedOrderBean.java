package me.jessyan.peach.shop.entity.mine;


import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * Created by Administrator on 2017/11/21.
 */

public class MineLoginCompletedOrderBean extends MultiItemBean {

    private List<OrderStatusBean.DataModel> data;

    public List<OrderStatusBean.DataModel> getData() {
        return data;
    }

    public void setData(List<OrderStatusBean.DataModel> data) {
        this.data = data;
    }
}
