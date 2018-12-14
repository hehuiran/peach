package me.jessyan.peach.shop.entity.goods;

import java.util.ArrayList;

/**
 * author: Created by HuiRan on 2018/7/13 10:00
 * E-Mail: 15260828327@163.com
 * description:
 */
public class GoodsCategoryTitleMainBean extends GoodsCategoryTitleBean.DataBean {

    private ArrayList<GoodsCategoryTitleBean.DataBean> goodsTypeList;

    public ArrayList<GoodsCategoryTitleBean.DataBean> getGoodsTypeList() {
        return goodsTypeList;
    }

    public void setGoodsTypeList(ArrayList<GoodsCategoryTitleBean.DataBean> goodsTypeList) {
        this.goodsTypeList = goodsTypeList;
    }
}
