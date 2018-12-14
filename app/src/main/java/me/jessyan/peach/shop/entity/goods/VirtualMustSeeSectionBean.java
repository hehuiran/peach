package me.jessyan.peach.shop.entity.goods;


import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * author: Created by HuiRan on 2018/3/30 11:37
 * E-Mail: 15260828327@163.com
 * description:
 */

public class VirtualMustSeeSectionBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends MultiItemBean {
        /**
         * id : null
         * title : 每日神券
         * goodsimg : https://hzcangyu.com/15.png
         * introduce : 阿玛尼口红
         * type : 5
         * introduceprice : 到手价仅需300元
         * time : null
         * goodstype : null
         * countdown : null
         */

        private String id;
        private String title;
        private String goodsimg;
        private String introduce;
        private int type;           //5：每日神券 6：秒杀中心 7：今日必抢 8：品牌上新 9：全场9.9 10：边看边买
        private String introduceprice;
        private String time;
        private String goodstype;
        private String countdown;
        private long endTime;
        private String fqcat;
        private String goodsurl;
        private String shopname;

        public String toString(){
            return  "id:"+id+" ; title:"+title+" ; goodsimg:"+goodsimg+" ; introduce:"+introduce
                    +" ; type:"+type+" ; introduceprice:"+introduceprice+" ; time:"+time+" ; goodstype:"+goodstype
                    +" ; countdown:"+countdown+" ; endTime:"+endTime+" ; fqcat:"+fqcat+" ; goodsurl:"+goodsurl+" ; shopname:"+shopname;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getGoodsurl() {
            return goodsurl;
        }

        public void setGoodsurl(String goodsurl) {
            this.goodsurl = goodsurl;
        }

        public String getFqcat() {
            return fqcat;
        }

        public void setFqcat(String fqcat) {
            this.fqcat = fqcat;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGoodsimg() {
            return goodsimg;
        }

        public void setGoodsimg(String goodsimg) {
            this.goodsimg = goodsimg;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getIntroduceprice() {
            return introduceprice;
        }

        public void setIntroduceprice(String introduceprice) {
            this.introduceprice = introduceprice;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getGoodstype() {
            return goodstype;
        }

        public void setGoodstype(String goodstype) {
            this.goodstype = goodstype;
        }

        public String getCountdown() {
            return countdown;
        }

        public void setCountdown(String countdown) {
            this.countdown = countdown;
        }
    }
}
