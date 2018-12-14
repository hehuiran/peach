package me.jessyan.peach.shop.entity.mine;

import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * author: Created by HuiRan on 2018/4/13 19:36
 * E-Mail: 15260828327@163.com
 * description:
 */

public class AllianceManagementTeamOrderBean extends MultiItemBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderSum : 3
         * todayOrder : 2
         * YesterdaySum : 0
         * hisMonthSum : 2
         * topMonthSum : 1
         */

        private String orderSum;
        private String todayOrder;
        private String YesterdaySum;
        private String hisMonthSum;
        private String topMonthSum;

        public String getOrderSum() {
            return orderSum;
        }

        public void setOrderSum(String orderSum) {
            this.orderSum = orderSum;
        }

        public String getTodayOrder() {
            return todayOrder;
        }

        public void setTodayOrder(String todayOrder) {
            this.todayOrder = todayOrder;
        }

        public String getYesterdaySum() {
            return YesterdaySum;
        }

        public void setYesterdaySum(String YesterdaySum) {
            this.YesterdaySum = YesterdaySum;
        }

        public String getHisMonthSum() {
            return hisMonthSum;
        }

        public void setHisMonthSum(String hisMonthSum) {
            this.hisMonthSum = hisMonthSum;
        }

        public String getTopMonthSum() {
            return topMonthSum;
        }

        public void setTopMonthSum(String topMonthSum) {
            this.topMonthSum = topMonthSum;
        }
    }
}
