package me.jessyan.peach.shop.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import me.jessyan.peach.shop.entity.goods.GiveGoodsDetailBean;

/**
 * Created by dxp on 2018/9/19.
 * Describe : 应用底部导航栏数据
 */

public class NavigationBarBean {

    /**
     * navigation : 首页
     * image : https://hzcangyu.com/homeimg/2.png
     * top : 5
     * action : 1
     * linktype : 2
     * url :
     * items : []
     * extra : {"ces":"11","asda":"222"}
     */

    private String navigation;
    private String image;
    private int top;
    private String url;
    private String action;
    private int linktype;
    private List<GiveGoodsDetailBean> items;
    private JSONObject extra;

    @Override
    public String toString() {
        return "DataBean{" +
                "navigation='" + navigation + '\'' +
                ", image='" + image + '\'' +
                ", top=" + top +
                ", action='" + action + '\'' +
                ", linktype=" + linktype +
                ", url='" + url + '\'' +
                ", items=" + items +
                ", extra=" + extra +
                '}';
    }

    public String getNavigation() {
        return navigation;
    }

    public void setNavigation(String navigation) {
        this.navigation = navigation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getLinktype() {
        return linktype;
    }

    public void setLinktype(int linktype) {
        this.linktype = linktype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<GiveGoodsDetailBean> getItems() {
        return items;
    }

    public void setItems(List<GiveGoodsDetailBean> items) {
        this.items = items;
    }

    public JSONObject getExtra() {
        return extra;
    }

    public void setExtra(JSONObject extra) {
        this.extra = extra;
    }


}
