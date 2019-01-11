package com.huojumu.model;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/16
 * Description: 扫描枪支付上传
 */
public class OrderInfo {

    /**
     * orderID : 12121212122
     * createTime : 2017-01-01 00:00:00
     * shopID : 3
     * ordSource : 2
     * enterpriseID : 34
     * pinpaiID : 33
     * quanIds : [32,45,22]
     * orderType : 1
     * startTime : 2018-11-13 16:00:00
     * endTime : 2018-11-13 16:30:00
     * data : [{"proId":2,"num":3,"proType":"1","tastes":[{"tasteId":1},{"tasteId":2}],"makes":[{"practiceId":1},{"practiceId":2}],"mats":[{"proMatId":1},{"proMatId":2}]},{"proId":4,"num":2,"proType":"2","taocan":[{"combID":1,"proId":2,"tastes":[{"tasteId":1},{"tasteId":2}],"makes":[{"practiceId":1},{"practiceId":2}],"mats":[{"proMatId":1},{"proMatId":2}]}]}]
     */

    private String orderID;
    private String createTime;
    private int shopID;
    private String ordSource = "3";
    private int enterpriseID;
    private int pinpaiID;
    private String orderType = "1";
    private String startTime;
    private String endTime;
    private String payType;
    private List<Integer> quanIds;
    private List<DataBean> data;
    private String discountsType;

    public String getDiscountsType() {
        return discountsType;
    }

    public void setDiscountsType(String discountsType) {
        this.discountsType = discountsType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public String getOrdSource() {
        return ordSource;
    }

    public void setOrdSource(String ordSource) {
        this.ordSource = ordSource;
    }

    public int getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(int enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public int getPinpaiID() {
        return pinpaiID;
    }

    public void setPinpaiID(int pinpaiID) {
        this.pinpaiID = pinpaiID;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getQuanIds() {
        return quanIds;
    }

    public void setQuanIds(List<Integer> quanIds) {
        this.quanIds = quanIds;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * proId : 2
         * num : 3
         * proType : 1
         * tastes : [{"tasteId":1},{"tasteId":2}]
         * makes : [{"practiceId":1},{"practiceId":2}]
         * mats : [{"proMatId":1},{"proMatId":2}]
         * taocan : [{"combID":1,"proId":2,"tastes":[{"tasteId":1},{"tasteId":2}],"makes":[{"practiceId":1},{"practiceId":2}],"mats":[{"proMatId":1},{"proMatId":2}]}]
         */

        private int proId;
        private int num;
        private String proType;
        private List<TastesBean> tastes;
        private List<MakesBean> makes;
        private List<MatsBean> mats;
        private List<TaocanBean> taocan;

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getProType() {
            return proType;
        }

        public void setProType(String proType) {
            this.proType = proType;
        }

        public List<TastesBean> getTastes() {
            return tastes;
        }

        public void setTastes(List<TastesBean> tastes) {
            this.tastes = tastes;
        }

        public List<MakesBean> getMakes() {
            return makes;
        }

        public void setMakes(List<MakesBean> makes) {
            this.makes = makes;
        }

        public List<MatsBean> getMats() {
            return mats;
        }

        public void setMats(List<MatsBean> mats) {
            this.mats = mats;
        }

        public List<TaocanBean> getTaocan() {
            return taocan;
        }

        public void setTaocan(List<TaocanBean> taocan) {
            this.taocan = taocan;
        }

        public static class TastesBean {
            /**
             * tasteId : 1
             */

            private int tasteId;

            public int getTasteId() {
                return tasteId;
            }

            public void setTasteId(int tasteId) {
                this.tasteId = tasteId;
            }
        }

        public static class MakesBean {
            /**
             * practiceId : 1
             */

            private int practiceId;

            public int getPracticeId() {
                return practiceId;
            }

            public void setPracticeId(int practiceId) {
                this.practiceId = practiceId;
            }
        }

        public static class MatsBean {
            /**
             * proMatId : 1
             */

            private int proMatId;

            public int getProMatId() {
                return proMatId;
            }

            public void setProMatId(int proMatId) {
                this.proMatId = proMatId;
            }
        }

        public static class TaocanBean {
            /**
             * combID : 1
             * proId : 2
             * tastes : [{"tasteId":1},{"tasteId":2}]
             * makes : [{"practiceId":1},{"practiceId":2}]
             * mats : [{"proMatId":1},{"proMatId":2}]
             */

            private int combID;
            private int proId;
            private List<TastesBeanX> tastes;
            private List<MakesBeanX> makes;
            private List<MatsBeanX> mats;

            public int getCombID() {
                return combID;
            }

            public void setCombID(int combID) {
                this.combID = combID;
            }

            public int getProId() {
                return proId;
            }

            public void setProId(int proId) {
                this.proId = proId;
            }

            public List<TastesBeanX> getTastes() {
                return tastes;
            }

            public void setTastes(List<TastesBeanX> tastes) {
                this.tastes = tastes;
            }

            public List<MakesBeanX> getMakes() {
                return makes;
            }

            public void setMakes(List<MakesBeanX> makes) {
                this.makes = makes;
            }

            public List<MatsBeanX> getMats() {
                return mats;
            }

            public void setMats(List<MatsBeanX> mats) {
                this.mats = mats;
            }

            public static class TastesBeanX {
                /**
                 * tasteId : 1
                 */

                private int tasteId;

                public int getTasteId() {
                    return tasteId;
                }

                public void setTasteId(int tasteId) {
                    this.tasteId = tasteId;
                }
            }

            public static class MakesBeanX {
                /**
                 * practiceId : 1
                 */

                private int practiceId;

                public int getPracticeId() {
                    return practiceId;
                }

                public void setPracticeId(int practiceId) {
                    this.practiceId = practiceId;
                }
            }

            public static class MatsBeanX {
                /**
                 * proMatId : 1
                 */

                private int proMatId;

                public int getProMatId() {
                    return proMatId;
                }

                public void setProMatId(int proMatId) {
                    this.proMatId = proMatId;
                }
            }
        }
    }
}
