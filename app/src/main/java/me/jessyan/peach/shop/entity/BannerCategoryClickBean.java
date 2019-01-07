package me.jessyan.peach.shop.entity;

import me.jessyan.peach.shop.entity.goods.GiveGoodsDetailBean;

/**
 * author: Create by HuiRan on 2019/1/7 下午2:44
 * email: 15260828327@163.com
 * description:
 */
public class BannerCategoryClickBean {

    private GiveGoodsDetailBean giveGoodsDetailBean;
    private String title;
    private ExtraBean extraBean;
    private String action;
    private String url;
    private String type;

    public GiveGoodsDetailBean getGiveGoodsDetailBean() {
        return giveGoodsDetailBean;
    }

    public void setGiveGoodsDetailBean(GiveGoodsDetailBean giveGoodsDetailBean) {
        this.giveGoodsDetailBean = giveGoodsDetailBean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExtraBean getExtraBean() {
        return extraBean;
    }

    public void setExtraBean(ExtraBean extraBean) {
        this.extraBean = extraBean;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
