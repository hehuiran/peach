package me.jessyan.peach.shop.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dxp on 2018/8/3.
 * Describe : banner - 广告页数据
 */

public class BannerBean {


    /**
     * img1 : https://hulivoide.oss-cn-beijing.aliyuncs.com/PLUS_GIF.gif
     * img2 : https://hulivoide.oss-cn-beijing.aliyuncs.com/X_PLUS_GIF.gif
     * url :
     * liketype : "null"
     * imgtype : 0
     */

    private List<Data> data;

    public List<Data> getData() {
        if(data == null){
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{
        private String img1;
        private String img2;
        private String url;
        private String liketype;            //0=商品详情 ；1=商品分类； 2=web活动； 3=app活动；4=七夕活动
        private String imgtype;             //0=图片地址； 1=视频地址
        private String title;

        @Override
        public String toString() {
            return "-> img1="+img1+", img2="+img2+", url="+url+", liketype="+liketype+", imgtype="+imgtype+", title="+title;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public String getImg2() {
            return img2;
        }

        public void setImg2(String img2) {
            this.img2 = img2;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLiketype() {
            return liketype;
        }

        public void setLiketype(String liketype) {
            this.liketype = liketype;
        }

        public String getImgtype() {
            return imgtype;
        }

        public void setImgtype(String imgtype) {
            this.imgtype = imgtype;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


}
