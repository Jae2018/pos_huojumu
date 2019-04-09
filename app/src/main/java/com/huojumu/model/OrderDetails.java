package com.huojumu.model;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/11/29
 * Description:
 */
public class OrderDetails {

    /**
     * operator : {"joinTime":"2019-02-24 14:01:23","nickname":"孙收银","userId":18}
     * orderdetail : {"pros":[{"price":14,"proId":215,"proCount":1,"proName":"燕麦牛乳茶","cups":[{"cupId":236,"proId":215,"proName":"燕麦牛乳茶"}],"orderDeailId":167}],"ordNo":"DDMDCN51000100010004201904090051","payType":"900","orderid":"278ab2b9ca4e410cbc0e4491152165c2","totalPrice":17,"createTime":"2019-04-09 17:52:10","ordSource":"3"}
     */

    private OperatorBean operator;
    private OrderdetailBean orderdetail;

    public OperatorBean getOperator() {
        return operator;
    }

    public void setOperator(OperatorBean operator) {
        this.operator = operator;
    }

    public OrderdetailBean getOrderdetail() {
        return orderdetail;
    }

    public void setOrderdetail(OrderdetailBean orderdetail) {
        this.orderdetail = orderdetail;
    }

    public static class OperatorBean {
        /**
         * joinTime : 2019-02-24 14:01:23
         * nickname : 孙收银
         * userId : 18
         */

        private String joinTime;
        private String nickname;
        private int userId;

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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    public static class OrderdetailBean {
        /**
         * pros : [{"price":14,"proId":215,"proCount":1,"proName":"燕麦牛乳茶","cups":[{"cupId":236,"proId":215,"proName":"燕麦牛乳茶"}],"orderDeailId":167}]
         * ordNo : DDMDCN51000100010004201904090051
         * payType : 900
         * orderid : 278ab2b9ca4e410cbc0e4491152165c2
         * totalPrice : 17.0
         * createTime : 2019-04-09 17:52:10
         * ordSource : 3
         */

        private String ordNo;
        private String payType;
        private String orderid;
        private double totalPrice;
        private String createTime;
        private String ordSource;
        private List<ProsBean> pros;

        public String getOrdNo() {
            return ordNo;
        }

        public void setOrdNo(String ordNo) {
            this.ordNo = ordNo;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOrdSource() {
            return ordSource;
        }

        public void setOrdSource(String ordSource) {
            this.ordSource = ordSource;
        }

        public List<ProsBean> getPros() {
            return pros;
        }

        public void setPros(List<ProsBean> pros) {
            this.pros = pros;
        }

        public static class ProsBean {
            /**
             * price : 14.0
             * proId : 215
             * proCount : 1
             * proName : 燕麦牛乳茶
             * cups : [{"cupId":236,"proId":215,"proName":"燕麦牛乳茶"}]
             * orderDeailId : 167
             */

            private double price;
            private int proId;
            private int proCount;
            private String proName;
            private int orderDeailId;
            private List<CupsBean> cups;

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getProId() {
                return proId;
            }

            public void setProId(int proId) {
                this.proId = proId;
            }

            public int getProCount() {
                return proCount;
            }

            public void setProCount(int proCount) {
                this.proCount = proCount;
            }

            public String getProName() {
                return proName;
            }

            public void setProName(String proName) {
                this.proName = proName;
            }

            public int getOrderDeailId() {
                return orderDeailId;
            }

            public void setOrderDeailId(int orderDeailId) {
                this.orderDeailId = orderDeailId;
            }

            public List<CupsBean> getCups() {
                return cups;
            }

            public void setCups(List<CupsBean> cups) {
                this.cups = cups;
            }

            public static class CupsBean {
                /**
                 * cupId : 236
                 * proId : 215
                 * proName : 燕麦牛乳茶
                 */

                private int cupId;
                private int proId;
                private String proName;

                public int getCupId() {
                    return cupId;
                }

                public void setCupId(int cupId) {
                    this.cupId = cupId;
                }

                public int getProId() {
                    return proId;
                }

                public void setProId(int proId) {
                    this.proId = proId;
                }

                public String getProName() {
                    return proName;
                }

                public void setProName(String proName) {
                    this.proName = proName;
                }
            }
        }
    }
}
