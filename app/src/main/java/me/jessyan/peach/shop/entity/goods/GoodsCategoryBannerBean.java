package me.jessyan.peach.shop.entity.goods;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author: Created by HuiRan on 2018/5/2 14:05
 * E-Mail: 15260828327@163.com
 * description:
 */

public class GoodsCategoryBannerBean {

    @SerializedName("data")
    private List<BannerModel> mBannerModelList;

    public List<BannerModel> getBannerModelList() {
        return mBannerModelList;
    }

    public void setBannerModelList(List<BannerModel> bannerModelList) {
        mBannerModelList = bannerModelList;
    }

    public static class BannerModel {

        @SerializedName("goodsimg")
        private String goodsImg;
        @SerializedName("goodsurl")
        private String goodsUrl;
        private int action;

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public String getGoodsImg() {
            return goodsImg;
        }

        public void setGoodsImg(String goodsImg) {
            this.goodsImg = goodsImg;
        }

        public String getGoodsUrl() {
            return goodsUrl;
        }

        public void setGoodsUrl(String goodsUrl) {
            this.goodsUrl = goodsUrl;
        }

    }
}
