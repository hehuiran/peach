package me.jessyan.peach.shop.entity.goods;

import java.util.List;

/**
 * author: Created by HuiRan on 2018/5/8 18:45
 * E-Mail: 15260828327@163.com
 * description:
 */

public class ProductDetailsIntervalBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * img : https://hzcangyu.com/head.png
         * title : 爱新觉罗努尔哈赤38秒前买了此商品
         */

        private String img;
        private String title;

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
    }
}
