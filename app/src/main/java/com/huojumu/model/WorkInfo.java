package com.huojumu.model;

public class WorkInfo {


    /**
     * lastEndTime : 2019-06-10 14:10:44
     * lastStartTime : 2019-06-10 14:02:01
     * orderCount : 4
     * pushMoneyData : {"totalMoney":73,"pushMoney":0,"ruleId":null}
     * saleData : {"mau":0,"wx":28,"total":73,"virtual":58,"alp":30,"coup":0,"real":15}
     */

    private String lastEndTime;
    private String lastStartTime;
    private int orderCount;
    private PushMoneyDataBean pushMoneyData;
    private SaleDataBean saleData;

    public String getLastEndTime() {
        return lastEndTime;
    }

    public void setLastEndTime(String lastEndTime) {
        this.lastEndTime = lastEndTime;
    }

    public String getLastStartTime() {
        return lastStartTime;
    }

    public void setLastStartTime(String lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public PushMoneyDataBean getPushMoneyData() {
        return pushMoneyData;
    }

    public void setPushMoneyData(PushMoneyDataBean pushMoneyData) {
        this.pushMoneyData = pushMoneyData;
    }

    public SaleDataBean getSaleData() {
        return saleData;
    }

    public void setSaleData(SaleDataBean saleData) {
        this.saleData = saleData;
    }

    public static class PushMoneyDataBean {
        /**
         * totalMoney : 73
         * pushMoney : 0
         * ruleId : null
         */

        private double totalMoney;
        private double pushMoney;
        private Object ruleId;

        public double getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(double totalMoney) {
            this.totalMoney = totalMoney;
        }

        public double getPushMoney() {
            return pushMoney;
        }

        public void setPushMoney(double pushMoney) {
            this.pushMoney = pushMoney;
        }

        public Object getRuleId() {
            return ruleId;
        }

        public void setRuleId(Object ruleId) {
            this.ruleId = ruleId;
        }
    }

    public static class SaleDataBean {
        /**
         * mau : 0
         * wx : 28
         * total : 73
         * virtual : 58
         * alp : 30
         * coup : 0
         * real : 15
         */

        private double mau;
        private double wx;
        private double total;
        private double virtual;
        private double alp;
        private double coup;
        private double real;

        public double getMau() {
            return mau;
        }

        public void setMau(double mau) {
            this.mau = mau;
        }

        public double getWx() {
            return wx;
        }

        public void setWx(double wx) {
            this.wx = wx;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getVirtual() {
            return virtual;
        }

        public void setVirtual(double virtual) {
            this.virtual = virtual;
        }

        public double getAlp() {
            return alp;
        }

        public void setAlp(double alp) {
            this.alp = alp;
        }

        public double getCoup() {
            return coup;
        }

        public void setCoup(double coup) {
            this.coup = coup;
        }

        public double getReal() {
            return real;
        }

        public void setReal(double real) {
            this.real = real;
        }
    }
}
