package com.huojumu.model;


public class H5TaskBean {


    /**
     * task : payAndPrint
     * data : {"member":{"joinTime":"2019-07-01 14:12:13","nickname":null,"totalConsumption":10,"memberId":39},"orderdetail":{"pros":[{"scaleId":3,"mats":[],"tastes":[],"price":10,"proId":346,"scaleNameEn":null,"proCount":1,"proName":"普洱蒟蒻","cups":[{"cupId":970,"proId":346,"proName":"普洱蒟蒻"}],"scaleName":"注塑杯（常温）","orderDeailId":821,"proNameEn":"Pu'er with Konjac"}],"ordNo":"DDMDCN12000100010003201907010001","payType":"010","orderid":"714b64f1bee54b422db963eb5af883","totalPrice":10,"createTime":"2019-07-01 14:16:53","ordSource":"1"}}
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
         * member : {"joinTime":"2019-07-01 14:12:13","nickname":null,"totalConsumption":10,"memberId":39}
         * orderdetail : {"pros":[{"scaleId":3,"mats":[],"tastes":[],"price":10,"proId":346,"scaleNameEn":null,"proCount":1,"proName":"普洱蒟蒻","cups":[{"cupId":970,"proId":346,"proName":"普洱蒟蒻"}],"scaleName":"注塑杯（常温）","orderDeailId":821,"proNameEn":"Pu'er with Konjac"}],"ordNo":"DDMDCN12000100010003201907010001","payType":"010","orderid":"714b64f1bee54b422db963eb5af883","totalPrice":10,"createTime":"2019-07-01 14:16:53","ordSource":"1"}
         */

        private MemberBean member;
        private OrderDetails.OrderdetailBean orderdetail;

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public OrderDetails.OrderdetailBean getOrderdetail() {
            return orderdetail;
        }

        public void setOrderdetail(OrderDetails.OrderdetailBean orderdetail) {
            this.orderdetail = orderdetail;
        }

        public static class MemberBean {
            /**
             * joinTime : 2019-07-01 14:12:13
             * nickname : null
             * totalConsumption : 10.0
             * memberId : 39
             */

            private String joinTime;
            private String nickname;
            private double totalConsumption;
            private int memberId;

            public String getJoinTime() {
                return joinTime;
            }

            public void setJoinTime(String joinTime) {
                this.joinTime = joinTime;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public double getTotalConsumption() {
                return totalConsumption;
            }

            public void setTotalConsumption(double totalConsumption) {
                this.totalConsumption = totalConsumption;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }
        }

    }
}
