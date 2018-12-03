package com.huojumu.model;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/19
 * Description:
 */
public class InventoryList {
    /**
     * total : 3
     * rows : [{"creator":"设备运维","compId":1,"warehouseId":1,"createTime":"2018-10-17 14:23:57","executor":"设备运维","checkNo":"PDCN110001040001201810170003","checkId":11,"executiveTime":"2018-10-17 14:24:25"},{"creator":"设备运维","compId":1,"warehouseId":1,"createTime":"2018-10-17 14:23:41","executor":"设备运维","checkNo":"PDCN110001040001201810170002","checkId":10,"executiveTime":"2018-10-17 14:24:14"},{"creator":"设备运维","compId":1,"warehouseId":1,"createTime":"2018-10-17 14:23:01","executor":"设备运维","checkNo":"PDCN110001040001201810170001","checkId":9,"executiveTime":"2018-10-17 14:23:15"}]
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
         * creator : 设备运维
         * compId : 1
         * warehouseId : 1
         * createTime : 2018-10-17 14:23:57
         * executor : 设备运维
         * checkNo : PDCN110001040001201810170003
         * checkId : 11
         * executiveTime : 2018-10-17 14:24:25
         */

        private String creator;
        private int compId;
        private int warehouseId;
        private String createTime;
        private String executor;
        private String checkNo;
        private int checkId;
        private String executiveTime;

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public int getCompId() {
            return compId;
        }

        public void setCompId(int compId) {
            this.compId = compId;
        }

        public int getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(int warehouseId) {
            this.warehouseId = warehouseId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getExecutor() {
            return executor;
        }

        public void setExecutor(String executor) {
            this.executor = executor;
        }

        public String getCheckNo() {
            return checkNo;
        }

        public void setCheckNo(String checkNo) {
            this.checkNo = checkNo;
        }

        public int getCheckId() {
            return checkId;
        }

        public void setCheckId(int checkId) {
            this.checkId = checkId;
        }

        public String getExecutiveTime() {
            return executiveTime;
        }

        public void setExecutiveTime(String executiveTime) {
            this.executiveTime = executiveTime;
        }
    }
}
