package com.huojumu.model;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/11/29
 * Description:
 */
public class OrderDetails {
    /**
     * member : {"joinTime":"2018-07-12 13:37:10","nickname":"李莉","totalConsumption":0,"memberId":89}
     * orderdetail : {"pros":[{"price":100,"proId":127,"proCount":5,"proName":"珍珠奶茶","cups":[{"cupId":28,"proId":127,"proName":"珍珠奶茶"},{"cupId":29,"proId":127,"proName":"珍珠奶茶"},{"cupId":30,"proId":127,"proName":"珍珠奶茶"},{"cupId":31,"proId":127,"proName":"珍珠奶茶"},{"cupId":32,"proId":127,"proName":"珍珠奶茶"}],"orderDeailId":14}],"ordNo":"DDMDCN12000100010001201807170004","orderid":"ededf08181ce4ea2b02a8a700ea152cb","totalPrice":100,"createTime":"2018-07-17 14:20:52","ordSource":"2"}
     */

    private MemberBean member;
    private OrderdetailBean orderdetail;

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public OrderdetailBean getOrderdetail() {
        return orderdetail;
    }

    public void setOrderdetail(OrderdetailBean orderdetail) {
        this.orderdetail = orderdetail;
    }

    public static class MemberBean {
        /**
         * joinTime : 2018-07-12 13:37:10
         * nickname : 李莉
         * totalConsumption : 0
         * memberId : 89
         */

        private String joinTime;
        private String nickname;
        private int totalConsumption;
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

        public int getTotalConsumption() {
            return totalConsumption;
        }

        public void setTotalConsumption(int totalConsumption) {
            this.totalConsumption = totalConsumption;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }
    }

    public static class OrderdetailBean {
        /**
         * pros : [{"price":100,"proId":127,"proCount":5,"proName":"珍珠奶茶","cups":[{"cupId":28,"proId":127,"proName":"珍珠奶茶"},{"cupId":29,"proId":127,"proName":"珍珠奶茶"},{"cupId":30,"proId":127,"proName":"珍珠奶茶"},{"cupId":31,"proId":127,"proName":"珍珠奶茶"},{"cupId":32,"proId":127,"proName":"珍珠奶茶"}],"orderDeailId":14}]
         * ordNo : DDMDCN12000100010001201807170004
         * orderid : ededf08181ce4ea2b02a8a700ea152cb
         * totalPrice : 100
         * createTime : 2018-07-17 14:20:52
         * ordSource : 2
         */

        private String ordNo;
        private String orderid;
        private int totalPrice;
        private String createTime;
        private String ordSource;
        private List<ProsBean> pros;
        private String payType;

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getOrdNo() {
            return ordNo;
        }

        public void setOrdNo(String ordNo) {
            this.ordNo = ordNo;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
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
             * price : 100
             * proId : 127
             * proCount : 5
             * proName : 珍珠奶茶
             * cups : [{"cupId":28,"proId":127,"proName":"珍珠奶茶"},{"cupId":29,"proId":127,"proName":"珍珠奶茶"},{"cupId":30,"proId":127,"proName":"珍珠奶茶"},{"cupId":31,"proId":127,"proName":"珍珠奶茶"},{"cupId":32,"proId":127,"proName":"珍珠奶茶"}]
             * orderDeailId : 14
             */

            private int price;
            private int proId;
            private int proCount;
            private String proName;
            private int orderDeailId;
            private List<CupsBean> cups;

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
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
                 * cupId : 28
                 * proId : 127
                 * proName : 珍珠奶茶
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
