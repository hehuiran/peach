package me.jessyan.peach.shop.entity.mine;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * author: Created by HuiRan on 2017/11/29 15:30
 * E-Mail: 15260828327@163.com
 * description:
 */

public class OrderSubBean {

    private List<DataBean> data;

    @Override
    public String toString() {
        return "OrderSubBean{" +
                "data=" + data +
                '}';
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Comparable<DataBean> {
        @SerializedName("commodity_price")
        private String amount;
        private String img;
        @SerializedName("commission")
        private String integral;
        @SerializedName("commodity_name")
        private String title;
        @SerializedName("orderiId")
        private String rechargeNo;
        private String orderType;
        @SerializedName("purchase_time")
        private String addTime;
        private int type;

        @Override
        public String toString() {
            return "DataBean{" +
                    "amount='" + amount + '\'' +
                    ", img='" + img + '\'' +
                    ", integral=" + integral +
                    ", title='" + title + '\'' +
                    ", rechargeNo='" + rechargeNo + '\'' +
                    ", orderType='" + orderType + '\'' +
                    ", addTime='" + addTime + '\'' +
                    ", type=" + type +
                    '}';
        }

        public String getRechargeNo() {
            return rechargeNo;
        }

        public void setRechargeNo(String rechargeNo) {
            this.rechargeNo = rechargeNo;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public int compareTo(@NonNull DataBean o) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            long l = TimeUtils.string2Millis(this.getAddTime(), format);
            long ol = TimeUtils.string2Millis(o.getAddTime(), format);
            if (l == ol) {
                return 0;
            } else if (l > ol) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
