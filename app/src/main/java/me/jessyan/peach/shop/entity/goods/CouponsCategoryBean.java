package me.jessyan.peach.shop.entity.goods;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * @author: Created by HuiRan on 2017/12/21 18:59
 * @E-Mail: 15260828327@163.com
 * @description:
 */

public class CouponsCategoryBean {

    @SerializedName("data")
    private List<CategoryModel> list;

    public List<CategoryModel> getList() {
        return list;
    }

    public void setList(List<CategoryModel> list) {
        this.list = list;
    }

    public static class CategoryModel extends MultiItemBean {
        @SerializedName("image")
        private String img;
        private String title;
        @SerializedName("summary")
        private String description;
        private String keys;
        private String imgurl;
        private boolean isFill;

        public String getKeys() {
            return keys;
        }

        public void setKeys(String keys) {
            this.keys = keys;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public boolean isFill() {
            return isFill;
        }

        public void setFill(boolean fill) {
            isFill = fill;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
