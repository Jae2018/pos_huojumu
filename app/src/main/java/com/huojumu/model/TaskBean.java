package com.huojumu.model;

/**
 * @author : Jie
 * Date: 2018/11/23
 * Description: qq：494669467，wx：s494669467
 */
public class TaskBean {
    /**
     * task : machinebind
     * "code": "0",
     * "msg":"ok"
     * data : {"eqpNo":"EQP-03-0038","machineCode":"66871e4c-cb2e-4b8b-b2fd-c9a52e8fa7e7","shopID":1}
     */

    private String task;
    private String code;
    private String msg;
    private DataBean data;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * eqpNo : EQP-03-0038
         * machineCode : 66871e4c-cb2e-4b8b-b2fd-c9a52e8fa7e7
         * shopID : 1
         */

        private String eqpNo;
        private String machineCode;
        private int shopID;
        private String userName;
        private String  token;
        private String orderCode;
        private String payTime;
        private String state;
        private int leftCupCnt;
        private int userId;
        private String loginName;
        private String mobile;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getLeftCupCnt() {
            return leftCupCnt;
        }

        public void setLeftCupCnt(int leftCupCnt) {
            this.leftCupCnt = leftCupCnt;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEqpNo() {
            return eqpNo;
        }

        public void setEqpNo(String eqpNo) {
            this.eqpNo = eqpNo;
        }

        public String getMachineCode() {
            return machineCode;
        }

        public void setMachineCode(String machineCode) {
            this.machineCode = machineCode;
        }

        public int getShopID() {
            return shopID;
        }

        public void setShopID(int shopID) {
            this.shopID = shopID;
        }
    }
}
