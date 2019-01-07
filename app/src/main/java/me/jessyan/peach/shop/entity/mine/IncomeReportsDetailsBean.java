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
        @SerializedName("orderCount")
        private String todayCount;
        @SerializedName("jr")
        private String todayCommission;
        @SerializedName("zrCount")
        private String yesterdayCount;
        @SerializedName("zr")
        private String yesterdayCommission;
        @SerializedName("zg")
        private String buyMoney;
        @SerializedName("fws")
        private String serviceProviderMoney;

        public String getTodayCount() {
            return todayCount;
        }

        public void setTodayCount(String todayCount) {
            this.todayCount = todayCount;
        }

        public String getTodayCommission() {
            return todayCommission;
        }

        public void setTodayCommission(String todayCommission) {
            this.todayCommission = todayCommission;
        }

        public String getYesterdayCount() {
            return yesterdayCount;
        }

        public void setYesterdayCount(String yesterdayCount) {
            this.yesterdayCount = yesterdayCount;
        }

        public String getYesterdayCommission() {
            return yesterdayCommission;
        }

        public void setYesterdayCommission(String yesterdayCommission) {
            this.yesterdayCommission = yesterdayCommission;
        }

        public String getBuyMoney() {
            return buyMoney;
        }

        public void setBuyMoney(String buyMoney) {
            this.buyMoney = buyMoney;
        }

        public String getServiceProviderMoney() {
            return serviceProviderMoney;
        }

        public void setServiceProviderMoney(String serviceProviderMoney) {
            this.serviceProviderMoney = serviceProviderMoney;
        }

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
