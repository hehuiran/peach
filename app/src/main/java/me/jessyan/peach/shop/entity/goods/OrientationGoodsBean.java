package me.jessyan.peach.shop.entity.goods;


import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;
import me.jessyan.peach.shop.utils.StringUtils;

/**
 * Created by dxp on 2018/8/24.
 * Describe : 首页横向滚动商品
 */

public class OrientationGoodsBean {

    //"returnModel":{"endtime":"\"null\"","rightcolor":"FF5CAD","leftcolor":"705AFF","imgs":"https://hzcangyu.com/title_jinribiqiang@2x.png”}

    private String endtime;
    private String rightcolor;
    private String leftcolor;
    private String imgs;
    private List<Data> data;

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getRightcolor() {
        return rightcolor;
    }

    public void setRightcolor(String rightcolor) {
        this.rightcolor = rightcolor;
    }

    public String getLeftcolor() {
        return leftcolor;
    }

    public void setLeftcolor(String leftcolor) {
        this.leftcolor = leftcolor;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data extends MultiItemBean {
        /**
         * item_id : 571646438840
         * item_title : 18夏季文艺女童装纯棉红色中短袖连衣裙中大儿童宽松沙滩过膝长裙
         * shop_type : c
         * small_images : https://img.alicdn.com/i3/42264608/TB23SbgxER1BeNjy0FmXXb0wVXa_!!42264608.jpg|https://img.alicdn.com/i4/42264608/TB2QhiRxpOWBuNjy0FiXXXFxVXa_!!42264608.jpg|https://img.alicdn.com/i3/42264608/TB2QblKpcuYBuNkSmRyXXcA3pXa_!!42264608.jpg|https://img.alicdn.com/i3/42264608/TB2gPStxpGWBuNjy0FbXXb4sXXa_!!42264608.jpg|
         * coupon_money : 3
         * coupon_id : 0f22d63193df44c2b7f2631fa0dbdc64
         * item_endprice : 76.0
         * item_pic : https://img.alicdn.com/bao/uploaded/i2/42264608/TB2vyWZxuSSBuNjy0FlXXbBpVXa_!!42264608.jpg
         * item_price : 79
         * item_shorttitle : "null"
         * couponstart_time : 1533484800
         * couponend_time : 1535644800
         * seller_id : 42264608
         * seller_nick : 湖边小店女孩衣柜
         * item_sale : 23
         * coupon_remain_coun : 9924
         * couponnum : 10000
         */

        private String item_id;
        private String item_title;
        private String shop_type;
        private String small_images;
        private String coupon_money;
        private String coupon_id;
        private String item_endprice;
        private String item_pic;
        private String item_price;
        private String item_shorttitle;
        private String couponstart_time;
        private String couponend_time;
        private String seller_id;
        private String seller_nick;
        private String item_sale;
        private String coupon_remain_coun;
        private String couponnum;
        private String tk_money;

        public String getTk_money() {
            return tk_money;
        }

        public void setTk_money(String tk_money) {
            this.tk_money = tk_money;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_title() {
            return item_title;
        }

        public void setItem_title(String item_title) {
            this.item_title = item_title;
        }

        public String getShop_type() {
            return shop_type;
        }

        public void setShop_type(String shop_type) {
            this.shop_type = shop_type;
        }

        public String getSmall_images() {
            return small_images;
        }

        public void setSmall_images(String small_images) {
            this.small_images = small_images;
        }

        public String getCoupon_money() {
            return coupon_money;
        }

        public void setCoupon_money(String coupon_money) {
            this.coupon_money = coupon_money;
        }

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getItem_endprice() {
            return item_endprice;
        }

        public void setItem_endprice(String item_endprice) {
            this.item_endprice = item_endprice;
        }

        public String getItem_pic() {
            return item_pic;
        }

        public void setItem_pic(String item_pic) {
            this.item_pic = item_pic;
        }

        public String getItem_price() {
            return item_price;
        }

        public void setItem_price(String item_price) {
            this.item_price = item_price;
        }

        public String getItem_shorttitle() {
            return item_shorttitle;
        }

        public void setItem_shorttitle(String item_shorttitle) {
            this.item_shorttitle = item_shorttitle;
        }

        public String getCouponstart_time() {
            return couponstart_time;
        }

        public void setCouponstart_time(String couponstart_time) {
            this.couponstart_time = couponstart_time;
        }

        public String getCouponend_time() {
            return couponend_time;
        }

        public void setCouponend_time(String couponend_time) {
            this.couponend_time = couponend_time;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getSeller_nick() {
            return seller_nick;
        }

        public void setSeller_nick(String seller_nick) {
            this.seller_nick = seller_nick;
        }

        public String getItem_sale() {
            return StringUtils.getUnitString(item_sale);
        }

        public void setItem_sale(String item_sale) {
            this.item_sale = item_sale;
        }

        public String getCoupon_remain_coun() {
            return coupon_remain_coun;
        }

        public void setCoupon_remain_coun(String coupon_remain_coun) {
            this.coupon_remain_coun = coupon_remain_coun;
        }

        public String getCouponnum() {
            return couponnum;
        }

        public void setCouponnum(String couponnum) {
            this.couponnum = couponnum;
        }
    }

}
