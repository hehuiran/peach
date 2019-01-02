package me.jessyan.peach.shop.entity.mine;


import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * Created by Administrator on 2017/11/21.
 */

public class MineLoginCompletedOrderBean extends MultiItemBean {

    private List<OrderSubBean.DataBean> data;

    public List<OrderSubBean.DataBean> getData() {
        return data;
    }

    public void setData(List<OrderSubBean.DataBean> data) {
        this.data = data;
    }
}
