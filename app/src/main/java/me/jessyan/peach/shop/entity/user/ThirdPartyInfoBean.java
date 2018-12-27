package me.jessyan.peach.shop.entity.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author: Created by HuiRan on 2018/4/20 18:42
 * E-Mail: 15260828327@163.com
 * description:
 */

public class ThirdPartyInfoBean implements Parcelable {

    private String nickName;
    private String iconUrl;
    private int platform;
    private int gender;
    private String openId;
    private String deviceId;

    public ThirdPartyInfoBean(){

    }

    protected ThirdPartyInfoBean(Parcel in) {
        nickName = in.readString();
        iconUrl = in.readString();
        platform = in.readInt();
        gender = in.readInt();
        openId = in.readString();
        deviceId = in.readString();
    }

    public static final Creator<ThirdPartyInfoBean> CREATOR = new Creator<ThirdPartyInfoBean>() {
        @Override
        public ThirdPartyInfoBean createFromParcel(Parcel in) {
            return new ThirdPartyInfoBean(in);
        }

        @Override
        public ThirdPartyInfoBean[] newArray(int size) {
            return new ThirdPartyInfoBean[size];
        }
    };

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickName);
        dest.writeString(iconUrl);
        dest.writeInt(platform);
        dest.writeInt(gender);
        dest.writeString(openId);
        dest.writeString(deviceId);
    }
}
