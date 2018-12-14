package me.jessyan.peach.shop.entity.mine;

import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * author: Created by HuiRan on 2018/4/13 19:36
 * E-Mail: 15260828327@163.com
 * description:
 */

public class AllianceManagementTeamMineBean extends MultiItemBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userSum : 2
         * twoLevelCount : 0
         * todaySum : 0
         * headData : ["http://thirdwx.qlogo.cn/mmopen/vi_32/qCmiazaBZS4SGFic2W6FttHIXqavymWqlliag4DcJo8k4HzWBb3lj8ja22Kib3dsd9cCUgTUicwiabDekfwcbC41cUrw/132","http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKIibwdCFCCobtNq4oengZCydibShYJZN5MlEA2TTGBmYzPn7Saw47e1wIRibL3YZ7auP9ErNANYcUicw/132"]
         * twoData : 0
         * oneData : 2
         * oneLevelCount: : 2
         * YesterdaySum : 0
         */

        private String userSum;
        private String todaySum;
        private String twoData;
        private String oneData;
        private String YesterdaySum;
        private List<String> headData;

        @Override
        public String toString() {
            return "DataBean{" +
                    "userSum='" + userSum + '\'' +
                    ", todaySum='" + todaySum + '\'' +
                    ", twoData='" + twoData + '\'' +
                    ", oneData='" + oneData + '\'' +
                    ", YesterdaySum='" + YesterdaySum + '\'' +
                    ", headData=" + headData +
                    '}';
        }


        public String getUserSum() {
            return userSum;
        }

        public void setUserSum(String userSum) {
            this.userSum = userSum;
        }


        public String getTodaySum() {
            return todaySum;
        }

        public void setTodaySum(String todaySum) {
            this.todaySum = todaySum;
        }

        public String getTwoData() {
            return twoData;
        }

        public void setTwoData(String twoData) {
            this.twoData = twoData;
        }

        public String getOneData() {
            return oneData;
        }

        public void setOneData(String oneData) {
            this.oneData = oneData;
        }



        public String getYesterdaySum() {
            return YesterdaySum;
        }

        public void setYesterdaySum(String YesterdaySum) {
            this.YesterdaySum = YesterdaySum;
        }

        public List<String> getHeadData() {
            return headData;
        }

        public void setHeadData(List<String> headData) {
            this.headData = headData;
        }


        /**
         * userSum : 2
         * todaySum : 2
         * YesterdaySum : 2
         * oneData : 2
         * twoData : 0
         * headData : ["https://hzcangyu.com/152376287346820180415112751.jpg","https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK9tdHkZDDpId5UxXvsJwiaWcvJH5mpqOTxc0Xunwa1X9URaRXPzoxPfa3FkSlWndCqqpdbIJBZOpA/132"]
         */

        /*private String userSum;
        private String todaySum;
        private String YesterdaySum;
        private String oneData;
        private String twoData;
        private List<String> headData;
        private String oneLevelCount;
        private String twoLevelCount;

        @Override
        public String toString() {
            return "DataBean{" +
                    "userSum='" + userSum + '\'' +
                    ", todaySum='" + todaySum + '\'' +
                    ", YesterdaySum='" + YesterdaySum + '\'' +
                    ", oneData='" + oneData + '\'' +
                    ", twoData='" + twoData + '\'' +
                    ", headData=" + headData +
                    ", oneLevelCount='" + oneLevelCount + '\'' +
                    ", twoLevelCount='" + twoLevelCount + '\'' +
                    '}';
        }

        public String getUserSum() {
            return userSum;
        }

        public void setUserSum(String userSum) {
            this.userSum = userSum;
        }

        public String getTodaySum() {
            return todaySum;
        }

        public void setTodaySum(String todaySum) {
            this.todaySum = todaySum;
        }

        public String getYesterdaySum() {
            return YesterdaySum;
        }

        public void setYesterdaySum(String YesterdaySum) {
            this.YesterdaySum = YesterdaySum;
        }

        public String getOneData() {
            return oneData;
        }

        public void setOneData(String oneData) {
            this.oneData = oneData;
        }

        public String getTwoData() {
            return twoData;
        }

        public void setTwoData(String twoData) {
            this.twoData = twoData;
        }

        public List<String> getHeadData() {
            return headData;
        }

        public void setHeadData(List<String> headData) {
            this.headData = headData;
        }


        public String getOneLevelCount() {
            if(TextUtils.isEmpty(oneLevelCount)){
                oneLevelCount = "0";
            }
            return oneLevelCount;
        }

        public void setOneLevelCount(String oneLevelCount) {
            this.oneLevelCount = oneLevelCount;
        }

        public String getTwoLevelCount() {
            if(TextUtils.isEmpty(twoLevelCount)){
                twoLevelCount = "0";
            }
            return twoLevelCount;
        }

        public void setTwoLevelCount(String twoLevelCount) {
            this.twoLevelCount = twoLevelCount;
        }*/
    }
    /*private String total;
    private List<String> imgList;
    private List<String> countList;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public List<String> getCountList() {
        return countList;
    }

    public void setCountList(List<String> countList) {
        this.countList = countList;
    }*/


}
