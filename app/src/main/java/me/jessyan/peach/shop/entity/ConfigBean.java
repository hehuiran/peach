package me.jessyan.peach.shop.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * author: Created by HuiRan on 2017/12/29 11:24
 * E-Mail: 15260828327@163.com
 * description:
 */

public class ConfigBean {

    /**
     * welcomeImg : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514291172899&di=0d7e62f700f7f5587021f0f71c2ec88a&imgtype=0&src=http%3A%2F%2Fimg2.ooopic.com%2F14%2F53%2F65%2F85bOOOPIC3c_202.jpg
     * goodsTypeData : [{"code":0,"name":"全部"},{"code":1,"name":"女装"},{"code":2,"name":"男装"},{"code":3,"name":"内衣"},{"code":4,"name":"美妆"},{"code":5,"name":"配饰"},{"code":6,"name":"鞋品"},{"code":7,"name":"箱包"},{"code":8,"name":"儿童"},{"code":9,"name":"母婴"},{"code":10,"name":"居家"},{"code":11,"name":"美食"},{"code":12,"name":"数码"},{"code":13,"name":"家电"},{"code":14,"name":"其他"}]
     */

    private String welcomeImg;
    private List<GoodsTypeDataBean> goodsTypeData;
    private List<String> searchArray;
    private String searchUrl;
    /*private String messageCount;

    public String getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(String messageCount) {
        this.messageCount = messageCount;
    }*/

    public List<String> getSearchArray() {
        return searchArray;
    }

    public void setSearchArray(List<String> searchArray) {
        this.searchArray = searchArray;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public String getWelcomeImg() {
        return welcomeImg;
    }

    public void setWelcomeImg(String welcomeImg) {
        this.welcomeImg = welcomeImg;
    }

    public List<GoodsTypeDataBean> getGoodsTypeData() {
        return goodsTypeData;
    }

    public void setGoodsTypeData(List<GoodsTypeDataBean> goodsTypeData) {
        this.goodsTypeData = goodsTypeData;
    }

    public static class GoodsTypeDataBean implements Parcelable {
        /**
         * code : 0
         * name : 全部
         */

        private int code;
        private String name;
        private boolean selected;

        public GoodsTypeDataBean() {
        }


        protected GoodsTypeDataBean(Parcel in) {
            code = in.readInt();
            name = in.readString();
            selected = in.readByte() != 0;
        }

        public static final Creator<GoodsTypeDataBean> CREATOR = new Creator<GoodsTypeDataBean>() {
            @Override
            public GoodsTypeDataBean createFromParcel(Parcel in) {
                return new GoodsTypeDataBean(in);
            }

            @Override
            public GoodsTypeDataBean[] newArray(int size) {
                return new GoodsTypeDataBean[size];
            }
        };

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(code);
            dest.writeString(name);
            dest.writeByte((byte) (selected ? 1 : 0));
        }
    }
}
