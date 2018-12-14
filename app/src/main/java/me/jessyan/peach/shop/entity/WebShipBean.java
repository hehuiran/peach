package me.jessyan.peach.shop.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yuwenchao on 2018/11/12.
 */

public class WebShipBean implements MultiItemEntity {

    /**
     * is_coupon : true
     * end_price : 85.0
     * coupon_money : 10.0
     * tbk_pwd : ï¿¥HguMbjC7aOPï¿¥
     * title : Fï¼ŽNYæ³•å¦®2018ç§‹è£…æ–°æ¬¾ä¸Šè¡£å¥³é˜²é£Žå¤¹å…‹ç™¾æ­é€šå‹¤æ½®çŸ­æ¬¾é£Žè¡£5036206
     * final_price : 95
     * commission_rate : 4.59
     * coupon_click_url : https://uland.taobao.com/coupon/edetail?e=yMAzwmUg%2B%2BEGQASttHIRqVZ3fsMDZl%2BnoyQ2XNykG%2FDq3cdsl6bpN8fzPmauVe3EVhPmPOHoLzizfFiZtfISX5Q5wfGz%2Fu%2BNzNJOxFrAxVljF%2Bp%2BXmZbqyZ6Y%2FpkHtT5QS0Flu%2FfbSovkBQlP112cJ5ECHpSy25Ge6L%2Bf9DtnlX22z3NHFCjqDcK2v3ZVS%2Fe&traceId=0b156b3115420246202735105e&union_lens=lensId:0b015dd6_13f3_16707d4690a_2e79
     * small_images : ["https://img.alicdn.com/i3/737675773/O1CN011sW4wTpl5urbUFN_!!737675773.jpg","https://img.alicdn.com/i4/737675773/O1CN011sW4wTGIZLAQfh2_!!737675773.jpg","https://img.alicdn.com/i2/737675773/O1CN011sW4wSdbAfQCmxc_!!737675773.jpg","https://img.alicdn.com/i1/737675773/O1CN011sW4wSddFTMg7bz_!!737675773.jpg"]
     */

    private double end_price;
    private double coupon_money;
    private String tbk_pwd;
    private String title;
    private String final_price;
    private String commission_rate;
    private String coupon_click_url;
    private List<String> small_images;
    private boolean is_coupon;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isIs_coupon() {
        return is_coupon;
    }

    public void setIs_coupon(boolean is_coupon) {
        this.is_coupon = is_coupon;
    }

    public double getEnd_price() {
        return end_price;
    }

    public void setEnd_price(double end_price) {
        this.end_price = end_price;
    }

    public double getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(double coupon_money) {
        this.coupon_money = coupon_money;
    }

    public String getTbk_pwd() {
        return tbk_pwd;
    }

    public void setTbk_pwd(String tbk_pwd) {
        this.tbk_pwd = tbk_pwd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFinal_price() {
        return final_price;
    }

    public void setFinal_price(String final_price) {
        this.final_price = final_price;
    }

    public String getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(String commission_rate) {
        this.commission_rate = commission_rate;
    }

    public String getCoupon_click_url() {
        return coupon_click_url;
    }

    public void setCoupon_click_url(String coupon_click_url) {
        this.coupon_click_url = coupon_click_url;
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
    }

    @Override
    public String toString() {
        return "WebShipBean{" +
                "end_price=" + end_price +
                ", coupon_money=" + coupon_money +
                ", tbk_pwd='" + tbk_pwd + '\'' +
                ", title='" + title + '\'' +
                ", final_price='" + final_price + '\'' +
                ", commission_rate='" + commission_rate + '\'' +
                ", coupon_click_url='" + coupon_click_url + '\'' +
                ", small_images=" + small_images +
                ", is_coupon=" + is_coupon +
                ", msg='" + msg + '\'' +
                '}';
    }
}
