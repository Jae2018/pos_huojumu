package com.huojumu.model;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/11/29
 * Description:
 */
public class OrderDetails {


    /**
     * operator : {"joinTime":"2019-03-19 15:36:30","nickname":"张三","userId":22}
     * orderdetail : {"pros":[{"mats":[{"proMatId":2187,"ingredientPrice":1,"matName":"芦荟粒"}],"tastes":[{"tasteId":1,"tasteName":"半糖"}],"sumPrice":"15.00","price":14,"proId":133,"proCount":1,"proName":"樱花草莓奶","cups":[{"cupId":125,"proId":133,"proName":"樱花草莓奶"}],"orderDeailId":91}],"ordNo":"DDMDCN51000100010004201904030060","payType":"900","orderid":"02bff9c685484866b0a885f88f7570ea","totalPrice":15,"createTime":"2019-04-03 17:58:02","ordSource":"3"}
     */

    private OperatorBean operator;
    private OrderdetailBean orderdetail;
    /**
     * member : {"joinTime":"2019-07-01 14:12:13","nickname":null,"totalConsumption":0.14,"memberId":39}
     */

    private MemberBean member;

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

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public static class OperatorBean {
        /**
         * joinTime : 2019-03-19 15:36:30
         * nickname : 张三
         * userId : 22
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
         * pros : [{"mats":[{"proMatId":2187,"ingredientPrice":1,"matName":"芦荟粒"}],"tastes":[{"tasteId":1,"tasteName":"半糖"}],"sumPrice":"15.00","price":14,"proId":133,"proCount":1,"proName":"樱花草莓奶","cups":[{"cupId":125,"proId":133,"proName":"樱花草莓奶"}],"orderDeailId":91}]
         * ordNo : DDMDCN51000100010004201904030060
         * payType : 900
         * orderid : 02bff9c685484866b0a885f88f7570ea
         * totalPrice : 15
         * createTime : 2019-04-03 17:58:02
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
             * mats : [{"proMatId":2187,"ingredientPrice":1,"matName":"芦荟粒"}]
             * tastes : [{"tasteId":1,"tasteName":"半糖"}]
             * sumPrice : 15.00
             * price : 14
             * proId : 133
             * proCount : 1
             * proName : 樱花草莓奶
             * cups : [{"cupId":125,"proId":133,"proName":"樱花草莓奶"}]
             * orderDeailId : 91
             */

            private String sumPrice;
            private double price;
            private int proId;
            private int proCount;
            private String proName;
            private String proNameEn;
            private int orderDeailId;
            private String matStr;
            private String scaleName;
            private List<MatsBean> mats;
            private List<TastesBean> tastes;
            private List<CupsBean> cups;

            public String getProNameEn() {
                return proNameEn;
            }

            public void setProNameEn(String proNameEn) {
                this.proNameEn = proNameEn;
            }

            public String getMatStr() {
                return matStr;
            }

            public void setMatStr(String matStr) {
                this.matStr = matStr;
            }

            public String getScaleName() {
                return scaleName;
            }

            public void setScaleName(String scaleName) {
                this.scaleName = scaleName;
            }

            public String getSumPrice() {
                return sumPrice;
            }

            public void setSumPrice(String sumPrice) {
                this.sumPrice = sumPrice;
            }

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

            public List<MatsBean> getMats() {
                return mats;
            }

            public void setMats(List<MatsBean> mats) {
                this.mats = mats;
            }

            public List<TastesBean> getTastes() {
                return tastes;
            }

            public void setTastes(List<TastesBean> tastes) {
                this.tastes = tastes;
            }

            public List<CupsBean> getCups() {
                return cups;
            }

            public void setCups(List<CupsBean> cups) {
                this.cups = cups;
            }

            public static class MatsBean {
                /**
                 * proMatId : 2187
                 * ingredientPrice : 1
                 * matName : 芦荟粒
                 */

                private int proMatId;
                private int ingredientPrice;
                private String matName;
                private String matNameEn;

                public String getMatNameEn() {
                    return matNameEn;
                }

                public void setMatNameEn(String matNameEn) {
                    this.matNameEn = matNameEn;
                }

                public int getProMatId() {
                    return proMatId;
                }

                public void setProMatId(int proMatId) {
                    this.proMatId = proMatId;
                }

                public int getIngredientPrice() {
                    return ingredientPrice;
                }

                public void setIngredientPrice(int ingredientPrice) {
                    this.ingredientPrice = ingredientPrice;
                }

                public String getMatName() {
                    return matName;
                }

                public void setMatName(String matName) {
                    this.matName = matName;
                }
            }

            public static class TastesBean {
                /**
                 * tasteId : 1
                 * tasteName : 半糖
                 */

                private int tasteId;
                private String tasteName;
                private String tasteNameEn;

                public String getTasteNameEn() {
                    return tasteNameEn;
                }

                public void setTasteNameEn(String tasteNameEn) {
                    this.tasteNameEn = tasteNameEn;
                }

                public int getTasteId() {
                    return tasteId;
                }

                public void setTasteId(int tasteId) {
                    this.tasteId = tasteId;
                }

                public String getTasteName() {
                    return tasteName;
                }

                public void setTasteName(String tasteName) {
                    this.tasteName = tasteName;
                }
            }

            public static class CupsBean {
                /**
                 * cupId : 125
                 * proId : 133
                 * proName : 樱花草莓奶
                 */

                private int cupId;
                private int proId;
                private String proName;
                private String proNameEn;

                public String getProNameEn() {
                    return proNameEn;
                }

                public void setProNameEn(String proNameEn) {
                    this.proNameEn = proNameEn;
                }

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

    public static class MemberBean {
        /**
         * joinTime : 2019-07-01 14:12:13
         * nickname : null
         * totalConsumption : 0.14
         * memberId : 39
         */

        private String joinTime;
        private Object nickname;
        private double totalConsumption;
        private int memberId;

        public String getJoinTime() {
            return joinTime;
        }

        public void setJoinTime(String joinTime) {
            this.joinTime = joinTime;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
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
