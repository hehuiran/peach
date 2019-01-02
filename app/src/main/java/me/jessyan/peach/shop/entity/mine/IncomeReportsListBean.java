package me.jessyan.peach.shop.entity.mine;


import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * author: Created by HuiRan on 2017/11/29 18:28
 * E-Mail: 15260828327@163.com
 * description:
 */

public class IncomeReportsListBean {

    private String totalAmount;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "IncomeReportsListBean{" +
                "totalAmount='" + totalAmount + '\'' +
                ", data=" + data +
                '}';
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends MultiItemBean {
        private long addTime;
        private String amount;
        private String remark;

        @Override
        public String toString() {
            return "DataBean{" +
                    "addTime=" + addTime +
                    ", amount='" + amount + '\'' +
                    ", remark='" + remark + '\'' +
                    '}';
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
