package me.jessyan.peach.shop.entity.home;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * author: Created by HuiRan on 2017/12/21 19:07
 * E-Mail: 15260828327@163.com
 * description: 商品数据
 */

public class CouponsCommodityBean extends MultiItemBean {

    @SerializedName("data_timestamp")
    private String dataTimestamp;
    @SerializedName("data")
    private List<GoodsBean> list;

    public List<GoodsBean> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setList(List<GoodsBean> list) {
        this.list = list;
    }

    public String getDataTimestamp() {
        return dataTimestamp;
    }

    public void setDataTimestamp(String dataTimestamp) {
        this.dataTimestamp = dataTimestamp;
    }

    @Override
    public String toString() {
        return "CouponsCommodityBean{" +
                "list=" + list +
                '}';
    }

    @Override
    public CouponsCommodityBean clone() {
        try {
            CouponsCommodityBean resultsModel = (CouponsCommodityBean) super.clone();
            List<GoodsBean> list = new ArrayList<>();
            for (GoodsBean bean : resultsModel.list) {
                list.add(bean.clone());
            }
            resultsModel.setList(list);

            return resultsModel;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
