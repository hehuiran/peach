package me.jessyan.peach.shop.entity.goods;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import me.jessyan.peach.shop.entity.ExtraBean;

/**
 * author: Created by HuiRan on 2018/4/25 18:34
 * E-Mail: 15260828327@163.com
 * description:
 */

public class GoodsCategoryGridBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {


        public DataBean() {
        }

        /**
         * id : 1
         * twotypeid : 1
         * title : 连衣裙
         * hid : 1
         * image : null
         */


        private int id;
        private int twotypeid;
        private String title;
        private int hid;
        private String image;

        private String action;
        private int linktype;
        private String url;
        private List<GiveGoodsDetailBean> items;
        private ExtraBean extra;


        protected DataBean(Parcel in) {
            id = in.readInt();
            twotypeid = in.readInt();
            title = in.readString();
            hid = in.readInt();
            image = in.readString();
            action = in.readString();
            linktype = in.readInt();
            url = in.readString();
            items = in.createTypedArrayList(GiveGoodsDetailBean.CREATOR);
            extra = in.readParcelable(ExtraBean.class.getClassLoader());
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTwotypeid() {
            return twotypeid;
        }

        public void setTwotypeid(int twotypeid) {
            this.twotypeid = twotypeid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getHid() {
            return hid;
        }

        public void setHid(int hid) {
            this.hid = hid;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public int getLinktype() {
            return linktype;
        }

        public void setLinktype(int linktype) {
            this.linktype = linktype;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<GiveGoodsDetailBean> getItems() {
            return items;
        }

        public void setItems(List<GiveGoodsDetailBean> items) {
            this.items = items;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(twotypeid);
            dest.writeString(title);
            dest.writeInt(hid);
            dest.writeString(image);
            dest.writeString(action);
            dest.writeInt(linktype);
            dest.writeString(url);
            dest.writeTypedList(items);
            dest.writeParcelable(extra, flags);
        }
    }
}
