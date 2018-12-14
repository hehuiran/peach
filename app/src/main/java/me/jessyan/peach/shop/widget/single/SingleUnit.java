package me.jessyan.peach.shop.widget.single;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * author: Created by HuiRan on 2017/12/19 18:25
 * E-Mail: 15260828327@163.com
 * description:
 */

public class SingleUnit implements Cloneable, Parcelable {

    private PointF mPointF;
    private float value;
    private long timeMillis;

    public SingleUnit(float value, long timeMillis) {
        this.value = value;
        this.timeMillis = timeMillis;
    }

    protected SingleUnit(Parcel in) {
        mPointF = in.readParcelable(PointF.class.getClassLoader());
        value = in.readFloat();
        timeMillis = in.readLong();
    }

    public static final Creator<SingleUnit> CREATOR = new Creator<SingleUnit>() {
        @Override
        public SingleUnit createFromParcel(Parcel in) {
            return new SingleUnit(in);
        }

        @Override
        public SingleUnit[] newArray(int size) {
            return new SingleUnit[size];
        }
    };

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public PointF getPointF() {
        return mPointF;
    }

    public void setPointF(PointF pointF) {
        mPointF = pointF;
    }

    @Override
    protected SingleUnit clone() {
        try {
            return (SingleUnit) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mPointF, flags);
        dest.writeFloat(value);
        dest.writeLong(timeMillis);
    }
}
