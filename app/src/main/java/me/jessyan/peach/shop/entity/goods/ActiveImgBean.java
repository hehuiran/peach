package me.jessyan.peach.shop.entity.goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dxp on 2018/8/6.
 * Describe : 首页活动图片
 */

public class ActiveImgBean {

    private List<Data> data;

    public List<Data> getData() {
        if(data==null){
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{

        private String type;

        private Img ret;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Img getRet() {
            return ret;
        }

        public void setRet(Img ret) {
            this.ret = ret;
        }

        public class Img{
            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }




    }

}
