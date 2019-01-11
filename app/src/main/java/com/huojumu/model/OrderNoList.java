package com.huojumu.model;

import java.util.List;

public class OrderNoList {


    /**
     * total : 24
     * rows : [{"ordNo":"DDGH-CN12-0002-0001-0001201805090007","createTime":"2018-05-10 10:58:41","totalPrice":12,"ordSource":"2","ordId":"e208368ecb514d00ad027dc56c2e3afc","status":"2"},{"ordNo":"DDGH-CN12-0002-0001-0001201805090015","createTime":"2018-05-10 10:58:41","totalPrice":12,"ordSource":"2","ordId":"f7d8f21d3eb7423992dca378bc6e5776","status":"2"},{"ordNo":"DDGH-CN12-0002-0001-0001201805100010","createTime":"2018-05-10 10:58:41","totalPrice":48.1,"ordSource":null,"ordId":"a1bee6548da5477aaffa35879f64fd92","status":"2"},{"ordNo":"DDGH-CN12-0002-0001-0001201805090003","createTime":"2018-05-10 10:58:40","totalPrice":16.1,"ordSource":"2","ordId":"e171c88dd3e24ae58660157c1905a0b7","status":"2"},{"ordNo":"DDGH-CN12-0002-0001-0001201805090017","createTime":"2018-05-10 10:58:40","totalPrice":12,"ordSource":"2","ordId":"e1e8979ec21c4571b1df7904022164b1","status":"2"},{"ordNo":"DDGH-CN12-0002-0001-0001201805090014","createTime":"2018-05-10 10:58:39","totalPrice":12,"ordSource":"2","ordId":"daa72131218e48ec97b99f10b1ed209a","status":"2"},{"ordNo":"DDGH-CN12-0002-0001-0001201805090004","createTime":"2018-05-10 10:58:39","totalPrice":0.05,"ordSource":"2","ordId":"dea2e3f4f0f6409d9a968eb352d44ce2","status":"2"},{"ordNo":"DDGH-CN12-0002-0001-0001201805090020","createTime":"2018-05-10 10:58:38","totalPrice":0.2,"ordSource":"2","ordId":"c17de8b75b1c4788a365d0bc91fc32b5","status":"2"},{"ordNo":"DDGH-CN12-0002-0001-0001201805090013","createTime":"2018-05-10 10:58:38","totalPrice":12,"ordSource":"2","ordId":"cce8b3467ef148d1933df449d2a3ccee","status":"2"},{"ordNo":"DDGH-CN12-0002-0001-0001201805100004","createTime":"2018-05-10 10:58:37","totalPrice":54.1,"ordSource":"2","ordId":"a85736ffcc4b4c649894db237c8a3d73","status":"2"}]
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
         * ordNo : DDGH-CN12-0002-0001-0001201805090007
         * createTime : 2018-05-10 10:58:41
         * totalPrice : 12
         * ordSource : 2
         * ordId : e208368ecb514d00ad027dc56c2e3afc
         * status : 2
         */

        private String ordNo;
        private String createTime;
        private int totalPrice;
        private String ordSource;
        private String ordId;
        private String status;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getOrdSource() {
            return ordSource;
        }

        public void setOrdSource(String ordSource) {
            this.ordSource = ordSource;
        }

        public String getOrdId() {
            return ordId;
        }

        public void setOrdId(String ordId) {
            this.ordId = ordId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
