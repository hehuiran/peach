package me.jessyan.peach.shop.entity.mine;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author: Created by HuiRan on 2018/4/16 14:07
 * E-Mail: 15260828327@163.com
 * description:
 */

public class IncomeReportsDetailsBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        @SerializedName("income")
        private String total;
        @SerializedName("available_amount")
        private String balance;
        @SerializedName("thisMonthSum")
        private String currentForecast;
        @SerializedName("topMonthSum")
        private String lastForecast;
        @SerializedName("thisSettlement")
        private String currentSettlement;
        @SerializedName("topSettlement")
        private String lastSettlement;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getCurrentForecast() {
            return currentForecast;
        }

        public void setCurrentForecast(String currentForecast) {
            this.currentForecast = currentForecast;
        }

        public String getLastForecast() {
            return lastForecast;
        }

        public void setLastForecast(String lastForecast) {
            this.lastForecast = lastForecast;
        }

        public String getCurrentSettlement() {
            return currentSettlement;
        }

        public void setCurrentSettlement(String currentSettlement) {
            this.currentSettlement = currentSettlement;
        }

        public String getLastSettlement() {
            return lastSettlement;
        }

        public void setLastSettlement(String lastSettlement) {
            this.lastSettlement = lastSettlement;
        }
    }

}
