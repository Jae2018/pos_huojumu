package com.huojumu.model;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/11/6
 * Description:
 */
public class OrdersList {

    /**
     * total : 34
     * rows : [{"ordNo":"DDMDCN11000100010001201812050048","payType":null,"createTime":"2018-12-05 17:51:00","totalPrice":2,"ordSource":"1","ordId":"c48b0e512cf3024791280632dce30d","status":"3"},{"ordNo":"DDMDCN11000100010001201812050039","payType":"020","createTime":"2018-12-05 16:18:26","totalPrice":2,"ordSource":"1","ordId":"02db67668fbaa8480e883078de631f","status":"2"},{"ordNo":"DDMDCN11000100010001201812050038","payType":null,"createTime":"2018-12-05 16:18:21","totalPrice":2,"ordSource":"1","ordId":"fa0dad86b886fb4f7fb8bf1b31a603","status":"3"},{"ordNo":"DDMDCN11000100010001201812050034","payType":null,"createTime":"2018-12-05 15:25:13","totalPrice":2,"ordSource":"1","ordId":"a8c9debbeb3efe4a82e8d08e24efeb","status":"2"},{"ordNo":"DDMDCN11000100010001201812050033","payType":null,"createTime":"2018-12-05 15:24:11","totalPrice":2,"ordSource":"1","ordId":"8cc3eb2aa3aefa462cabdb2ae28492","status":"2"},{"ordNo":"DDMDCN11000100010001201812050032","payType":null,"createTime":"2018-12-05 15:23:57","totalPrice":2,"ordSource":"1","ordId":"629a5d65c42d9c4c34cb1fbce8286d","status":"2"},{"ordNo":"DDMDCN11000100010001201812050031","payType":"020","createTime":"2018-12-05 15:23:33","totalPrice":2,"ordSource":"1","ordId":"0b2ef5334bca9445ef48282430caa2","status":"2"},{"ordNo":"DDMDCN11000100010001201812050030","payType":null,"createTime":"2018-12-05 15:22:26","totalPrice":2,"ordSource":"1","ordId":"831de576bf40bb4e2bb8550b8d6174","status":"2"},{"ordNo":"DDMDCN11000100010001201812050029","payType":null,"createTime":"2018-12-05 15:21:49","totalPrice":2,"ordSource":"1","ordId":"7b7df0a02164724adb288db2929647","status":"2"},{"ordNo":"DDMDCN11000100010001201812050028","payType":null,"createTime":"2018-12-05 15:21:41","totalPrice":2,"ordSource":"1","ordId":"6ce685fd77fbc7419e789d478c76a0","status":"2"}]
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
         * ordNo : DDMDCN11000100010001201812050048
         * payType : null
         * createTime : 2018-12-05 17:51:00
         * totalPrice : 2
         * ordSource : 1
         * ordId : c48b0e512cf3024791280632dce30d
         * status : 3
         */

        private String ordNo;
        private String payType;
        private String createTime;
        private double totalPrice;
        private String ordSource;
        private String ordId;
        private String status;

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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
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
