package com.huojumu.model;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/9/29
 * Description: 商品信息
 */
public class Products {

    private String orderId;
    private List<TastesBean> tastes;
    private List<ScalesBean> scales;
    private List<String> makes;
    private List<ProductsBean> products;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<TastesBean> getTastes() {
        return tastes;
    }

    public void setTastes(List<TastesBean> tastes) {
        this.tastes = tastes;
    }

    public List<ScalesBean> getScales() {
        return scales;
    }

    public void setScales(List<ScalesBean> scales) {
        this.scales = scales;
    }

    public List<String> getMakes() {
        return makes;
    }

    public void setMakes(List<String> makes) {
        this.makes = makes;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class TastesBean {

        /**
         * tasteId : 8
         * tasteName : kouwei1
         * groupId : 6
         * multiple : 0.2
         */

        private int tasteId;
        private String tasteName;
        private int groupId;
        private double multiple;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
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

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public double getMultiple() {
            return multiple;
        }

        public void setMultiple(double multiple) {
            this.multiple = multiple;
        }
    }

    public static class ScalesBean {

        /**
         * scaleId : 1
         * scaName : 冷饮小杯
         * unitName : ml
         * unitId : 20
         * type : 1
         * capacity : 500.0
         */

        private int scaleId;
        private String scaName;
        private String unitName;
        private int unitId;
        private String type;
        private double capacity;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getScaleId() {
            return scaleId;
        }

        public void setScaleId(int scaleId) {
            this.scaleId = scaleId;
        }

        public String getScaName() {
            return scaName;
        }

        public void setScaName(String scaName) {
            this.scaName = scaName;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public int getUnitId() {
            return unitId;
        }

        public void setUnitId(int unitId) {
            this.unitId = unitId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getCapacity() {
            return capacity;
        }

        public void setCapacity(double capacity) {
            this.capacity = capacity;
        }
    }

    public static class ProductsBean {

        /**
         * scaleId : 1
         * mats : []
         * proType : 1
         * imgs : [{"path":"https://www.goodb2b.cn/bootstrap_file/9eae1aaa34901582a7beb91388c1d6bf.jpg","sourceType":"1"}]
         * tastes : [{"tasteGroupId":1}]
         * origionPrice : 10.0
         * makes : []
         * remark : 红西柚优格多多
         * isDiscount : 0
         * proNo : CP-CN11-0001-0007
         * isMoneyOff : 0
         * saleCnt : 0
         * compId : 1
         * price : 0.01
         * proId : 7
         * isBargain : 1
         * isSaled : 0
         * isPresented : 0
         * typeId : 47
         * startTime : 2018-08-08 00:00:00
         * proName : 红西柚益菌多多
         * endTime : 2018-11-22 00:00:00
         */

        private int scaleId;
        private String proType;
        private double origionPrice;
        private String remark;
        private String isDiscount;
        private String proNo;
        private String isMoneyOff;
        private int saleCnt;
        private int compId;
        private double price;
        private int proId;
        private String isBargain;
        private String isSaled;
        private String isPresented;
        private int typeId;
        private String startTime;
        private String proName;
        private String endTime;
        private List<Mats> mats;
        private List<ImgsBean> imgs;
        private List<TastesBean> tastes;
        private List<Makes> makes;
        private List<TaocanBean> taocan;
        private int number = 1;
        private String addon;
        private String tasteStr;

        public String getTasteStr() {
            return tasteStr;
        }

        public void setTasteStr(String tasteStr) {
            this.tasteStr = tasteStr;
        }

        public List<TaocanBean> getTaocan() {
            return taocan;
        }

        public void setTaocan(List<TaocanBean> taocan) {
            this.taocan = taocan;
        }

        public String getAddon() {
            return addon;
        }

        public void setAddon(String addon) {
            this.addon = addon;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getScaleId() {
            return scaleId;
        }

        public void setScaleId(int scaleId) {
            this.scaleId = scaleId;
        }

        public String getProType() {
            return proType;
        }

        public void setProType(String proType) {
            this.proType = proType;
        }

        public double getOrigionPrice() {
            return origionPrice;
        }

        public void setOrigionPrice(double origionPrice) {
            this.origionPrice = origionPrice;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getIsDiscount() {
            return isDiscount;
        }

        public void setIsDiscount(String isDiscount) {
            this.isDiscount = isDiscount;
        }

        public String getProNo() {
            return proNo;
        }

        public void setProNo(String proNo) {
            this.proNo = proNo;
        }

        public String getIsMoneyOff() {
            return isMoneyOff;
        }

        public void setIsMoneyOff(String isMoneyOff) {
            this.isMoneyOff = isMoneyOff;
        }

        public int getSaleCnt() {
            return saleCnt;
        }

        public void setSaleCnt(int saleCnt) {
            this.saleCnt = saleCnt;
        }

        public int getCompId() {
            return compId;
        }

        public void setCompId(int compId) {
            this.compId = compId;
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

        public String getIsBargain() {
            return isBargain;
        }

        public void setIsBargain(String isBargain) {
            this.isBargain = isBargain;
        }

        public String getIsSaled() {
            return isSaled;
        }

        public void setIsSaled(String isSaled) {
            this.isSaled = isSaled;
        }

        public String getIsPresented() {
            return isPresented;
        }

        public void setIsPresented(String isPresented) {
            this.isPresented = isPresented;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public List<Mats> getMats() {
            return mats;
        }

        public void setMats(List<Mats> mats) {
            this.mats = mats;
        }

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public List<TastesBean> getTastes() {
            return tastes;
        }

        public void setTastes(List<TastesBean> tastes) {
            this.tastes = tastes;
        }

        public List<Makes> getMakes() {
            return makes;
        }

        public void setMakes(List<Makes> makes) {
            this.makes = makes;
        }

        public static class Mats {

            /**
             * proMatId : 40
             * dosage : 11
             * ingredientDosage : 5
             * ingredientPrice : 3
             * addIndex : 1
             * matNo : YL-0003
             * orgId : 3
             * matName : 纯牛奶
             */

            private int proMatId;
            private int dosage;
            private int ingredientDosage;
            private int ingredientPrice;
            private int addIndex;
            private String matNo;
            private int orgId;
            private String matName;
            private boolean selected;

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public int getProMatId() {
                return proMatId;
            }

            public void setProMatId(int proMatId) {
                this.proMatId = proMatId;
            }

            public int getDosage() {
                return dosage;
            }

            public void setDosage(int dosage) {
                this.dosage = dosage;
            }

            public int getIngredientDosage() {
                return ingredientDosage;
            }

            public void setIngredientDosage(int ingredientDosage) {
                this.ingredientDosage = ingredientDosage;
            }

            public int getIngredientPrice() {
                return ingredientPrice;
            }

            public void setIngredientPrice(int ingredientPrice) {
                this.ingredientPrice = ingredientPrice;
            }

            public int getAddIndex() {
                return addIndex;
            }

            public void setAddIndex(int addIndex) {
                this.addIndex = addIndex;
            }

            public String getMatNo() {
                return matNo;
            }

            public void setMatNo(String matNo) {
                this.matNo = matNo;
            }

            public int getOrgId() {
                return orgId;
            }

            public void setOrgId(int orgId) {
                this.orgId = orgId;
            }

            public String getMatName() {
                return matName;
            }

            public void setMatName(String matName) {
                this.matName = matName;
            }
        }

        public static class Makes {

            /**
             * practiceName : 加冰
             * groupId : 1
             * practiceId : 1
             * multiple : 1
             */

            private String practiceName;
            private String groupId;
            private int practiceId;
            private int multiple;

            public String getPracticeName() {
                return practiceName;
            }

            public void setPracticeName(String practiceName) {
                this.practiceName = practiceName;
            }

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public int getPracticeId() {
                return practiceId;
            }

            public void setPracticeId(int practiceId) {
                this.practiceId = practiceId;
            }

            public int getMultiple() {
                return multiple;
            }

            public void setMultiple(int multiple) {
                this.multiple = multiple;
            }
        }

        public static class ImgsBean {
            /**
             * path : https://www.goodb2b.cn/bootstrap_file/9eae1aaa34901582a7beb91388c1d6bf.jpg
             * sourceType : 1
             */

            private String path;
            private String sourceType;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getSourceType() {
                return sourceType;
            }

            public void setSourceType(String sourceType) {
                this.sourceType = sourceType;
            }
        }

        public static class TastesBean {
            /**
             * tasteGroupId : 1
             */
            private int tasteId = 0;
            private String tasteName;
            private int groupId = 0;
            private double multiple;
            private boolean hasSelected;
            private int tasteGroupId = 0;

            public int getTasteGroupId() {
                return tasteGroupId;
            }

            public void setTasteGroupId(int tasteGroupId) {
                this.tasteGroupId = tasteGroupId;
            }

            public boolean isHasSelected() {
                return hasSelected;
            }

            public void setHasSelected(boolean hasSelected) {
                this.hasSelected = hasSelected;
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

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
            }

            public double getMultiple() {
                return multiple;
            }

            public void setMultiple(double multiple) {
                this.multiple = multiple;
            }

        }

        public static class TaocanBean {
            /**
             * isChanaged : 1
             * proId : 1
             * diffPrice : 0
             * proCount : 1
             * id : 3
             * proComboId : 10
             * parentId : null
             */

            private String isChanaged;
            private int proId;
            private int diffPrice;
            private int proCount;
            private int id;
            private int proComboId;
            private Object parentId;
            private boolean selected;

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public String getIsChanaged() {
                return isChanaged;
            }

            public void setIsChanaged(String isChanaged) {
                this.isChanaged = isChanaged;
            }

            public int getProId() {
                return proId;
            }

            public void setProId(int proId) {
                this.proId = proId;
            }

            public int getDiffPrice() {
                return diffPrice;
            }

            public void setDiffPrice(int diffPrice) {
                this.diffPrice = diffPrice;
            }

            public int getProCount() {
                return proCount;
            }

            public void setProCount(int proCount) {
                this.proCount = proCount;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProComboId() {
                return proComboId;
            }

            public void setProComboId(int proComboId) {
                this.proComboId = proComboId;
            }

            public Object getParentId() {
                return parentId;
            }

            public void setParentId(Object parentId) {
                this.parentId = parentId;
            }
        }
    }
}
