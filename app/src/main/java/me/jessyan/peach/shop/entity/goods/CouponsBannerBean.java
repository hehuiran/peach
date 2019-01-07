package me.jessyan.peach.shop.entity.goods;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.peach.shop.entity.ExtraBean;
import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * author: Created by HuiRan on 2017/12/18 16:20
 * E-Mail: 15260828327@163.com
 * description:
 */

public class CouponsBannerBean extends MultiItemBean {

    @SerializedName("data")
    private List<BannerBean> bannerList;

    public List<BannerBean> getBannerList() {
        if (bannerList == null) {
            bannerList = new ArrayList<>();
        }
        return bannerList;
    }

    public void setBannerList(List<BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    public static class BannerBean {

        private String type;            //action=1时有用；   商品详情0;商品分类1;拉起分享2；  无  99
        @SerializedName("ret")
        private DataBean mDataBean;

        private boolean backToMain = false;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public DataBean getDataBean() {
            return mDataBean;
        }

        public void setDataBean(DataBean dataBean) {
            this.mDataBean = dataBean;
        }

        public boolean isBackToMain() {
            return backToMain;
        }

        public void setBackToMain(boolean backToMain) {
            this.backToMain = backToMain;
        }

        @Override
        public String toString() {
            return "BannerBean{" +
                    "type='" + type + '\'' +
                    ", mDataBean=" + mDataBean +
                    ", backToMain=" + backToMain +
                    '}';
        }

        public static class DataBean {

            //新旧接口公共字段
            private String img;
            @SerializedName("twotype")
            private String twoType;
            @SerializedName("onetype")
            private String oneType;
            private String imageUrl;
            private String title;
            private String content;


            //旧字段
            private String itemId;
            @SerializedName("couponmoney")
            private String couponMoney;
            private String couponStartTime;
            private String couponEndTime;

            //新字段
            private String action;      //系统页面  1 ；自定义链接  3 ；外链  2
            private List<GiveGoodsDetailBean> items;
            private ExtraBean extra;


            public DataBean() {
            }

            @Override
            public String toString() {
                return "DataBean{" +
                        "img='" + img + '\'' +
                        ", twoType='" + twoType + '\'' +
                        ", oneType='" + oneType + '\'' +
                        ", imageUrl='" + imageUrl + '\'' +
                        ", title='" + title + '\'' +
                        ", itemId='" + itemId + '\'' +
                        ", couponMoney='" + couponMoney + '\'' +
                        ", couponStartTime='" + couponStartTime + '\'' +
                        ", couponEndTime='" + couponEndTime + '\'' +
                        ", action='" + action + '\'' +
                        ", items=" + items +
                        ", extra=" + extra +
                        '}';
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public String getTwoType() {
                return twoType;
            }

            public void setTwoType(String twoType) {
                this.twoType = twoType;
            }

            public String getOneType() {
                return oneType;
            }

            public void setOneType(String oneType) {
                this.oneType = oneType;
            }

            public String getCouponMoney() {
                return couponMoney;
            }

            public void setCouponMoney(String couponMoney) {
                this.couponMoney = couponMoney;
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

            public String getItemId() {
                return itemId;
            }

            public void setItemId(String itemId) {
                this.itemId = itemId;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
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

            public List<GiveGoodsDetailBean> getItems() {
                return items;
            }

            public void setItems(List<GiveGoodsDetailBean> items) {
                this.items = items;
            }

            public ExtraBean getExtra() {
                return extra;
            }

            public void setExtra(ExtraBean extra) {
                this.extra = extra;
            }
        }
    }

}
