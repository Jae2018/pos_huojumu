package com.huojumu.model;

public class PayEvent {
    /**
     * task : pay
     * data : {"orderCode":"1111111111","payTime":"2017-01-01 00:00:00","state":"1","leftCupCnt":23}
     */

    private String task;
    private DataBean data;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderCode : 1111111111
         * payTime : 2017-01-01 00:00:00
         * state : 1
         * leftCupCnt : 23
         */

        private String orderCode;
        private String payTime;
        private String state;
        private int leftCupCnt;

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
    }
}
