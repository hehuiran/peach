package me.jessyan.peach.shop.entity.goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dxp on 2018/9/18.
 * Describe : banner及搜索、达人、等页面跳转商品详情时 传递进入的部分商品数据
 */

public class GiveGoodsDetailBean implements Parcelable{


    /**
     * title : 初夏纯棉短袖T恤男印花青年圆领修身打底体恤韩版简约百搭上衣服
     * itemId : 565171251507
     * couponStartTime : 1529251200
     * couponEndTime : 1529942399
     * couponmoney : 30.0000
     */

    private String title;
    private String itemId;
    private String couponStartTime;
    private String couponEndTime;
    private String couponmoney;


    public GiveGoodsDetailBean() {
    }

    protected GiveGoodsDetailBean(Parcel in) {
        title = in.readString();
        itemId = in.readString();
        couponStartTime = in.readString();
        couponEndTime = in.readString();
        couponmoney = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(itemId);
        dest.writeString(couponStartTime);
        dest.writeString(couponEndTime);
        dest.writeString(couponmoney);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GiveGoodsDetailBean> CREATOR = new Creator<GiveGoodsDetailBean>() {
        @Override
        public GiveGoodsDetailBean createFromParcel(Parcel in) {
            return new GiveGoodsDetailBean(in);
        }

        @Override
        public GiveGoodsDetailBean[] newArray(int size) {
            return new GiveGoodsDetailBean[size];
        }
    };

    @Override
    public String toString() {
        return "GiveGoodsDetailBean{" +
                "title='" + title + '\'' +
                ", itemId='" + itemId + '\'' +
                ", couponStartTime='" + couponStartTime + '\'' +
                ", couponEndTime='" + couponEndTime + '\'' +
                ", couponmoney='" + couponmoney + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCouponStartTime() {
        return couponStartTime;
    }

    public void setCouponStartTime(String couponStartTime) {
        this.couponStartTime = couponStartTime;
    }

    public String getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public String getCouponmoney() {
        return couponmoney;
    }

    public void setCouponmoney(String couponmoney) {
        this.couponmoney = couponmoney;
    }
}
