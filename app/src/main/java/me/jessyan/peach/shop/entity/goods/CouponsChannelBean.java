package me.jessyan.peach.shop.entity.goods;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.jessyan.peach.shop.callback.MultiItemSpanEntity;

/**
 * @author: Created by HuiRan on 2017/12/18 17:42
 * @E-Mail: 15260828327@163.com
 * @description:
 */

public class CouponsChannelBean extends AbstractExpandableItem<CouponsChannelSubBean> implements MultiItemSpanEntity {

    @SerializedName("data")
    private List<ChannelModel> list;
    private int itemType;
    private int spanSize;

    public List<ChannelModel> getList() {
        return list;
    }

    public void setList(List<ChannelModel> list) {
        this.list = list;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    @Override
    public int getSpanSize() {
        return spanSize;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public static class ChannelModel {
        private int img;
        private String title;
        private int type;
        private int status;
        @SerializedName("data")
        private List<CouponsChannelSubBean> subList;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<CouponsChannelSubBean> getSubList() {
            return subList;
        }

        public void setSubList(List<CouponsChannelSubBean> subList) {
            this.subList = subList;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
