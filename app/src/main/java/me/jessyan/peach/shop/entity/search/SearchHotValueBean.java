package me.jessyan.peach.shop.entity.search;

import com.google.gson.annotations.SerializedName;

/**
 * author: Create by HuiRan on 2018/12/22 下午1:33
 * email: 15260828327@163.com
 * description:
 */
public class SearchHotValueBean {
    private String value;
    @SerializedName("itemid")
    private String itemId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
