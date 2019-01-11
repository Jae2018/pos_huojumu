package com.huojumu.model;

import java.util.List;

public class OrderDetail {


    /**
     * mats : [{"proMatId":40,"price":3,"matName":"纯牛奶"}]
     * orderNo : DDGH-CN12-0002-0001-0001201805090003
     * tastes : []
     * makes : []
     * id : 311
     * proName : 拉花卡布奇诺
     * status : 2
     */

    private String orderNo;
    private int id;
    private String proName;
    private String status;
    private List<MatsBean> mats;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MatsBean> getMats() {
        return mats;
    }

    public void setMats(List<MatsBean> mats) {
        this.mats = mats;
    }

    public static class MatsBean {
        /**
         * proMatId : 40
         * price : 3
         * matName : 纯牛奶
         */

        private int proMatId;
        private int price;
        private String matName;

        public int getProMatId() {
            return proMatId;
        }

        public void setProMatId(int proMatId) {
            this.proMatId = proMatId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getMatName() {
            return matName;
        }

        public void setMatName(String matName) {
            this.matName = matName;
        }
    }
}
