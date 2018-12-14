package me.jessyan.peach.shop.entity.mine;

import java.util.List;

/**
 * author: Created by HuiRan on 2018/4/19 14:45
 * E-Mail: 15260828327@163.com
 * description:
 */

public class DrawDetailsListBean {

    /**
     * totalAmount : null
     * data : [{"id":null,"userId":527,"rechargeNo":null,"amount":1,"type":null,"status":0,"sendTime":null,"remark":"系统正在处理","addTime":1523875595000,"isDeleted":null},{"id":null,"userId":527,"rechargeNo":null,"amount":1,"type":null,"status":2,"sendTime":null,"remark":"提现失败","addTime":1524044160000,"isDeleted":null}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : null
         * userId : 527
         * rechargeNo : null
         * amount : 1
         * type : null
         * status : 0
         * sendTime : null
         * remark : 系统正在处理
         * addTime : 1523875595000
         * isDeleted : null
         */

        private String id;
        private int userId;
        private String rechargeNo;
        private String amount;
        private String type;
        private int status;     // when 0 then '系统正在处理' when 1 then '提现成功' when 2 then '提现失败'
        private String sendTime;
        private String remark;
        private long addTime;
        private String isDeleted;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getRechargeNo() {
            return rechargeNo;
        }

        public void setRechargeNo(String rechargeNo) {
            this.rechargeNo = rechargeNo;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }
    }
}
