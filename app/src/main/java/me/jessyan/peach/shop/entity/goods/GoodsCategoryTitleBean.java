package me.jessyan.peach.shop.entity.goods;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.jessyan.peach.shop.entity.ExtraBean;

/**
 * author: Created by HuiRan on 2018/4/28 17:39
 * E-Mail: 15260828327@163.com
 * description:
 */

public class GoodsCategoryTitleBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 1
         * typeid : 1
         * typename : 女装
         */

        private int id;
        private int typeid;
        private String typename;
        private int goodstype;
        @SerializedName("img")
        private String image;

        //是否被选中 - 分类模块适用
        private boolean isChecked = false;

        private String url;
        private String action;
        private int linktype;
        private List<GiveGoodsDetailBean> items;

        private ExtraBean extra;

        public DataBean() {

        }

        protected DataBean(Parcel in) {
            id = in.readInt();
            typeid = in.readInt();
            typename = in.readString();
            goodstype = in.readInt();
            image = in.readString();
            isChecked = in.readByte() != 0;
            url = in.readString();
            action = in.readString();
            linktype = in.readInt();
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getGoodstype() {
            return goodstype;
        }

        public void setGoodstype(int goodstype) {
            this.goodstype = goodstype;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTypeid() {
            return typeid;
        }

        public void setTypeid(int typeid) {
            this.typeid = typeid;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(typeid);
            dest.writeString(typename);
            dest.writeInt(goodstype);
            dest.writeString(image);
            dest.writeByte((byte) (isChecked ? 1 : 0));
            dest.writeString(url);
            dest.writeString(action);
            dest.writeInt(linktype);
            dest.writeTypedList(items);
            dest.writeParcelable(extra, flags);
        }
    }
}
