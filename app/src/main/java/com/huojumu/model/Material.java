package com.huojumu.model;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/29
 * Description:
 */
public class Material {

    /**
     * total : 1
     * rows : [{"unitName":"kg","matId":26,"stocks":99999,"matName":"草莓爆爆蛋"}]
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
         * unitName : kg
         * matId : 26
         * stocks : 99999
         * matName : 草莓爆爆蛋
         */

        private String unitName;
        private int matId;
        private int stocks;
        private String matName;

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public int getMatId() {
            return matId;
        }

        public void setMatId(int matId) {
            this.matId = matId;
        }

        public int getStocks() {
            return stocks;
        }

        public void setStocks(int stocks) {
            this.stocks = stocks;
        }

        public String getMatName() {
            return matName;
        }

        public void setMatName(String matName) {
            this.matName = matName;
        }
    }
}
