package me.jessyan.peach.shop.entity.mine;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * Created by Administrator on 2017/11/21.
 */

public class MineLoginCompletedBannerBean {

    @SerializedName("data")
    private List<BannerModel> bannerList;

    public List<BannerModel> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerModel> bannerList) {
        this.bannerList = bannerList;
    }

    public static class BannerModel extends MultiItemBean {
        @SerializedName("image")
        private String img;
        private String url;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
