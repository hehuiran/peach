package me.jessyan.peach.shop.entity.goods;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.jessyan.peach.shop.entity.ExtraBean;
import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * @author: Created by HuiRan on 2017/12/22 10:30
 * @E-Mail: 15260828327@163.com
 * @description:
 */

public class CouponsChannelSubBean extends MultiItemBean {

    @SerializedName("image")
    private String img;
    private String title;
    private String url;

    private String action;
    private int linktype;
    private List<GiveGoodsDetailBean> items;
    private ExtraBean extra;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<GiveGoodsDetailBean> getItems() {
        return items;
    }

    public void setItems(List<GiveGoodsDetailBean> items) {
        this.items = items;
    }


    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }


}
