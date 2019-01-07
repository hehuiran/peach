package me.jessyan.peach.shop.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * author: Create by HuiRan on 2019/1/7 下午2:29
 * email: 15260828327@163.com
 * description:
 */
public class ExtraBean implements Parcelable {
    @SerializedName("type_index")
    private String typeIndex;
    @SerializedName("onetype")
    private String oneType;
    @SerializedName("twotype")
    private String twoType;

    public ExtraBean() {

    }

    protected ExtraBean(Parcel in) {
        typeIndex = in.readString();
        oneType = in.readString();
        twoType = in.readString();
    }

    public static final Creator<ExtraBean> CREATOR = new Creator<ExtraBean>() {
        @Override
        public ExtraBean createFromParcel(Parcel in) {
            return new ExtraBean(in);
        }

        @Override
        public ExtraBean[] newArray(int size) {
            return new ExtraBean[size];
        }
    };

    public String getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(String typeIndex) {
        this.typeIndex = typeIndex;
    }

    public String getOneType() {
        return oneType;
    }

    public void setOneType(String oneType) {
        this.oneType = oneType;
    }

    public String getTwoType() {
        return twoType;
    }

    public void setTwoType(String twoType) {
        this.twoType = twoType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(typeIndex);
        dest.writeString(oneType);
        dest.writeString(twoType);
    }
}
