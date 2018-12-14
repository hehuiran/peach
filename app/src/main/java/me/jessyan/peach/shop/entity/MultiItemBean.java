package me.jessyan.peach.shop.entity;


import me.jessyan.peach.shop.callback.MultiItemSpanEntity;

/**
 * Created by 86475 on 2017/11/4.
 */

public class MultiItemBean implements MultiItemSpanEntity {

    private int itemType;
    private int spanSize;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }


    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public int getSpanSize() {
        return spanSize;
    }
}
