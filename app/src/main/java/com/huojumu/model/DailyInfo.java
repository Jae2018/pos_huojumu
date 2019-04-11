package com.huojumu.model;

import java.util.List;

public class DailyInfo {

    /**
     * orders : {"total":5,"rows":[{"orderType":null,"orgionTotalPrice":90,"ordNo":"DDMDCN11000100010001201902200029","orderTime":"2019-02-21","payType":"900","orderId":"190220060107","totalPrice":72,"refundReason":null,"ordSource":"3","state":"2","discountsType":"1"},{"orderType":null,"orgionTotalPrice":18,"ordNo":"DDMDCN11000100010001201902210030","orderTime":"2019-02-21","payType":"900","orderId":"190220213344","totalPrice":18,"refundReason":null,"ordSource":"3","state":"2","discountsType":"1"},{"orderType":null,"orgionTotalPrice":15,"ordNo":"DDMDCN11000100010001201902210031","orderTime":"2019-02-21","payType":"900","orderId":"190220214052","totalPrice":15,"refundReason":null,"ordSource":"3","state":"2","discountsType":"1"},{"orderType":null,"orgionTotalPrice":36,"ordNo":"DDMDCN11000100010001201902210032","orderTime":"2019-02-21","payType":null,"orderId":"190220220511","totalPrice":18,"refundReason":null,"ordSource":"3","state":"2","discountsType":"1"},{"orderType":null,"orgionTotalPrice":15,"ordNo":"DDMDCN11000100010001201902210033","orderTime":"2019-02-21","payType":"900","orderId":"190220225601","totalPrice":15,"refundReason":null,"ordSource":"3","state":"2","discountsType":"1"}],"pageNum":1}
     * pushMoneyData : {"pushMoney":0}
     * saleData : {"total":138,"virtual":18,"real":120}
     */

    private OrdersBean orders;
    private PushMoneyDataBean pushMoneyData;
    private SaleDataBean saleData;
    private String lastEndTime;
    private String lastStartTime;
    private long timestamp;

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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public OrdersBean getOrders() {
        return orders;
    }

    public void setOrders(OrdersBean orders) {
        this.orders = orders;
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

    public static class OrdersBean {
        /**
         * total : 5
         * rows : [{"orderType":null,"orgionTotalPrice":90,"ordNo":"DDMDCN11000100010001201902200029","orderTime":"2019-02-21","payType":"900","orderId":"190220060107","totalPrice":72,"refundReason":null,"ordSource":"3","state":"2","discountsType":"1"},{"orderType":null,"orgionTotalPrice":18,"ordNo":"DDMDCN11000100010001201902210030","orderTime":"2019-02-21","payType":"900","orderId":"190220213344","totalPrice":18,"refundReason":null,"ordSource":"3","state":"2","discountsType":"1"},{"orderType":null,"orgionTotalPrice":15,"ordNo":"DDMDCN11000100010001201902210031","orderTime":"2019-02-21","payType":"900","orderId":"190220214052","totalPrice":15,"refundReason":null,"ordSource":"3","state":"2","discountsType":"1"},{"orderType":null,"orgionTotalPrice":36,"ordNo":"DDMDCN11000100010001201902210032","orderTime":"2019-02-21","payType":null,"orderId":"190220220511","totalPrice":18,"refundReason":null,"ordSource":"3","state":"2","discountsType":"1"},{"orderType":null,"orgionTotalPrice":15,"ordNo":"DDMDCN11000100010001201902210033","orderTime":"2019-02-21","payType":"900","orderId":"190220225601","totalPrice":15,"refundReason":null,"ordSource":"3","state":"2","discountsType":"1"}]
         * pageNum : 1
         */

        private int total;
        private int pageNum;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * orderType : null
             * orgionTotalPrice : 90
             * ordNo : DDMDCN11000100010001201902200029
             * orderTime : 2019-02-21
             * payType : 900
             * orderId : 190220060107
             * totalPrice : 72
             * refundReason : null
             * ordSource : 3
             * state : 2
             * discountsType : 1
             */

            private String orderType;
            private double orgionTotalPrice;
            private String ordNo;
            private String orderTime;
            private String payType;
            private String orderId;
            private double totalPrice;
            private String refundReason;
            private String ordSource;
            private String state;
            private String discountsType;

            public String getOrderType() {
                return orderType;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public double getOrgionTotalPrice() {
                return orgionTotalPrice;
            }

            public void setOrgionTotalPrice(double orgionTotalPrice) {
                this.orgionTotalPrice = orgionTotalPrice;
            }

            public String getOrdNo() {
                return ordNo;
            }

            public void setOrdNo(String ordNo) {
                this.ordNo = ordNo;
            }

            public String getOrderTime() {
                return orderTime;
            }

            public void setOrderTime(String orderTime) {
                this.orderTime = orderTime;
            }

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getRefundReason() {
                return refundReason;
            }

            public void setRefundReason(String refundReason) {
                this.refundReason = refundReason;
            }

            public String getOrdSource() {
                return ordSource;
            }

            public void setOrdSource(String ordSource) {
                this.ordSource = ordSource;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getDiscountsType() {
                return discountsType;
            }

            public void setDiscountsType(String discountsType) {
                this.discountsType = discountsType;
            }
        }
    }

    public static class PushMoneyDataBean {
        /**
         * pushMoney : 0
         */

        private double pushMoney;

        public double getPushMoney() {
            return pushMoney;
        }

        public void setPushMoney(double pushMoney) {
            this.pushMoney = pushMoney;
        }
    }

    public static class SaleDataBean {
        /**
         * total : 138
         * virtual : 18
         * real : 120
         */

        private double total;
        private double virtual;
        private double real;

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

        public double getReal() {
            return real;
        }

        public void setReal(double real) {
            this.real = real;
        }
    }
}
