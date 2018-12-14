package me.jessyan.peach.shop.entity;


import java.util.List;

/**
 * Created by yuwenchao on 2018/11/15.
 */

public class FindBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean extends MultiItemBean{
        /**
         * author : äº’åˆ©è”ç›Ÿ
         * head_img :
         * small_images : ["https://img.alicdn.com/bao/uploaded/i4/2145616690/O1CN011zI49ZnlWx0qsbr_!!0-item_pic.jpg","https://img.alicdn.com/i3/2145616690/O1CN011zI49NuQwkajf73_!!2145616690.jpg","https://img.alicdn.com/i4/2145616690/O1CN011zI49NvWXu8s70N_!!2145616690.jpg","https://img.alicdn.com/i2/2145616690/O1CN011zI49WTLJ2u3w6K_!!2145616690.jpg","https://img.alicdn.com/i4/2145616690/O1CN011zI49GznYueneaR_!!2145616690.jpg"]
         * id : 16
         * item_id : 572999514605
         * content : åº·å¤«æ¯›è¡£æœèµ·çƒä¿®å‰ªå™¨å……ç”µå¼åŽ»æ¯›çƒç¥žå™¨é™¤æ¯›åŽ»é™¤å‰ƒæ‰“è„±æ¯›æœºå™¨å®¶ç”¨ðŸ‘®
         * comment : ðŸ˜„ðŸ˜„ðŸ˜„åº·å¤«æ¯›è¡£æœèµ·çƒä¿®å‰ªå™¨å……ç”µå¼åŽ»æ¯›çƒç¥žå™¨é™¤æ¯›åŽ»é™¤å‰ƒæ‰“è„±æ¯›æœºå™¨å®¶ç”¨
         * add_time : 1542038400
         * share_num : 0
         * item_title : åº·å¤«æ¯›è¡£æœèµ·çƒä¿®å‰ªå™¨å……ç”µå¼åŽ»æ¯›çƒç¥žå™¨é™¤æ¯›åŽ»é™¤å‰ƒæ‰“è„±æ¯›æœºå™¨å®¶ç”¨
         * item_price : 24.9
         * item_endprice : 14.9
         * coupon_money : 10
         * couponstart_time : 1541520000
         * couponend_time : 1541692800
         * tk_money : 0.80
         */

        private String author;
        private String head_img;
        private int id;
        private String item_id;
        private String content;
        private String comment;
        private String add_time;
        private int share_num;
        private String item_title;
        private String item_price;
        private String item_endprice;
        private String coupon_money;
        private String couponstart_time;
        private String couponend_time;
        private String tk_money;
        private List<String> small_images;
        private int is_long_img;

        public int getIs_long_img() {
            return is_long_img;
        }

        public void setIs_long_img(int is_long_img) {
            this.is_long_img = is_long_img;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getShare_num() {
            return share_num;
        }

        public void setShare_num(int share_num) {
            this.share_num = share_num;
        }

        public String getItem_title() {
            return item_title;
        }

        public void setItem_title(String item_title) {
            this.item_title = item_title;
        }

        public String getItem_price() {
            return item_price;
        }

        public void setItem_price(String item_price) {
            this.item_price = item_price;
        }

        public String getItem_endprice() {
            return item_endprice;
        }

        public void setItem_endprice(String item_endprice) {
            this.item_endprice = item_endprice;
        }

        public String getCoupon_money() {
            return coupon_money;
        }

        public void setCoupon_money(String coupon_money) {
            this.coupon_money = coupon_money;
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

        public String getTk_money() {
            return tk_money;
        }

        public void setTk_money(String tk_money) {
            this.tk_money = tk_money;
        }

        public List<String> getSmall_images() {
            return small_images;
        }

        public void setSmall_images(List<String> small_images) {
            this.small_images = small_images;
        }


        @Override
        public int getItemType() {
            return 0;
//            return small_images!=null&&small_images.size()>1? FindAdapter.ITEM_TWO:FindAdapter.ITEM_ONE;
        }
    }
}
