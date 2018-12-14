package me.jessyan.peach.shop.entity.goods;

import com.google.gson.annotations.SerializedName;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * @author: Created by HuiRan on 2018/3/8 14:06
 * @E-Mail: 15260828327@163.com
 * @description:
 */

public class FreeShippingOnePlusNBean extends MultiItemBean {

    @SerializedName("goodsimg")
    private String img;
    private String title;
    @SerializedName("introduce")
    private String description;
    @SerializedName("introduceprice")
    private String price;
    @SerializedName("goodsurl")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
