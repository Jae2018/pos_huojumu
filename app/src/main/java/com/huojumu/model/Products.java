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
         * tastes	口味集合
         * tasteId	口味id
         * tasteName	口味名称
         * groupId	口味组id
         * multiple	口味倍数
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
         * sacles	规格集合
         * scaleId	规格id
         * scaName	规格名称
         * unitId	单位id
         * unitName	单位名称
         * type	类型:1-冷饮;2-热饮;
         * capacity	容量
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
         * products	    产品集合 包含套餐
         * scaleId	    规格id
         * mats	        加料集合
         * proType	    产品类型
         * imgs	        产品图片集合
         * tastes	    产品口味集合
         * origionPrice	原价
         * makes	    做法集合
         * proNo	    产品编号
         * compId	    所属企业id
         * price	    产品价格
         * typeId	    所属小类id
         * startTime	上架时间
         * proName	    产品名称
         * endTime	    下架时间
         * isSaled	    是否销售:0-可销售;1-不可销售;
         * isBargain	是否特价:0-否;1-是;
         * isDiscount	是否打折:0-否;1-是;
         * isMoneyOff	是否满减:0-否;1-是;
         * isPresented	是否赠送:0-否;1-是;
         * saleCnt	    销量
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
             * mats	            加料集合
             * proMatId	        加料集合
             * dosage	        用量
             * ingredientDosage	加料用量
             * ingredientPrice	加料金额
             * addIndex	        加料顺序
             * matNo	        加料编号
             * orgId	        加料id
             * matName	        加料名称
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
             * makes	    做法集合
             * makeGroupId	产品做法组Id
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
             * imgs	        产品图片集合
             * path	        图片下载地址
             * sourceType	图片类型
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
             * tastes	        产品口味集合
             * tasteGroupId	    产品口味组Id
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
