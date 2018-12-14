package me.jessyan.peach.shop.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dxp on 2018/8/28.
 * Describe :
 */

public class TalentBean {

    private List<Data> topdata;
    private List<Data> newdata;
    private List<Data> alldata;

    public List<Data> getTopdata() {
        return topdata;
    }

    public void setTopdata(List<Data> topdata) {
        this.topdata = topdata;
    }

    public List<Data> getNewdata() {
        return newdata;
    }

    public void setNewdata(List<Data> newdata) {
        this.newdata = newdata;
    }

    public List<Data> getAlldata() {
        return alldata;
    }

    public void setAlldata(List<Data> alldata) {
        this.alldata = alldata;
    }

    public class Data implements Parcelable{
        /**
         * id : 617
         * name : 瀛︾敓鍏氬繀鍏ヨ澶囩偣浜綘鐨勫紑瀛︿华寮忔劅
         * shorttitle : 鏉ワ紝鎷胯蛋寮�瀛﹀ぇ绀煎寘
         * image : https://img.alicdn.com/imgextra/i3/2000255661/TB2WayGwuuSBuNjSsziXXbq8pXa_!!2000255661.jpg
         * app_image : http://img.haodanku.com/FmpluvI5rIeWAnDd_7lTSO7z89gM
         * label : #瀛︾敓鍏氭湁濂界墿#
         * tk_item_id : 541345727454,563387101625,571238741166,574220699617,558794949391,565846822659,575051080218
         * article_banner : http://img.haodanku.com/FtrbhlP053lWo5pSUz-1CIuVngy9
         * highquality : 1
         * compose_image : http://img.haodanku.com/Fscf3NLD0pswcR5vduWOL-o4XL00
         * readtimes : 6027
         * talent_name : 鏈烘櫤銇皬鍛嗙尗
         * talentcat : 1
         * talent_id : 2
         * itemnum : 7
         */

        private String id;
        private String name;
        private String shorttitle;
        private String image;
        private String app_image;
        private String label;
        private String tk_item_id;
        private String article_banner;
        private String highquality;
        private String compose_image;
        private String readtimes;
        private String talent_name;
        private String talentcat;
        private String talent_id;
        private String itemnum;
        private String head_img;

        protected Data(Parcel in) {
            id = in.readString();
            name = in.readString();
            shorttitle = in.readString();
            image = in.readString();
            app_image = in.readString();
            label = in.readString();
            tk_item_id = in.readString();
            article_banner = in.readString();
            highquality = in.readString();
            compose_image = in.readString();
            readtimes = in.readString();
            talent_name = in.readString();
            talentcat = in.readString();
            talent_id = in.readString();
            itemnum = in.readString();
            head_img = in.readString();
        }


        public final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShorttitle() {
            return shorttitle;
        }

        public void setShorttitle(String shorttitle) {
            this.shorttitle = shorttitle;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getApp_image() {
            return app_image;
        }

        public void setApp_image(String app_image) {
            this.app_image = app_image;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getTk_item_id() {
            return tk_item_id;
        }

        public void setTk_item_id(String tk_item_id) {
            this.tk_item_id = tk_item_id;
        }

        public String getArticle_banner() {
            return article_banner;
        }

        public void setArticle_banner(String article_banner) {
            this.article_banner = article_banner;
        }

        public String getHighquality() {
            return highquality;
        }

        public void setHighquality(String highquality) {
            this.highquality = highquality;
        }

        public String getCompose_image() {
            return compose_image;
        }

        public void setCompose_image(String compose_image) {
            this.compose_image = compose_image;
        }

        public String getReadtimes() {
            return readtimes;
        }

        public void setReadtimes(String readtimes) {
            this.readtimes = readtimes;
        }

        public String getTalent_name() {
            return talent_name;
        }

        public void setTalent_name(String talent_name) {
            this.talent_name = talent_name;
        }

        public String getTalentcat() {
            return talentcat;
        }

        public void setTalentcat(String talentcat) {
            this.talentcat = talentcat;
        }

        public String getTalent_id() {
            return talent_id;
        }

        public void setTalent_id(String talent_id) {
            this.talent_id = talent_id;
        }

        public String getItemnum() {
            return itemnum;
        }

        public void setItemnum(String itemnum) {
            this.itemnum = itemnum;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(shorttitle);
            dest.writeString(image);
            dest.writeString(app_image);
            dest.writeString(label);
            dest.writeString(tk_item_id);
            dest.writeString(article_banner);
            dest.writeString(highquality);
            dest.writeString(compose_image);
            dest.writeString(readtimes);
            dest.writeString(talent_name);
            dest.writeString(talentcat);
            dest.writeString(talent_id);
            dest.writeString(itemnum);
            dest.writeString(head_img);
        }
    }
}
