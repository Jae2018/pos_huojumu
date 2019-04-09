package com.huojumu.model;

import java.util.List;

/**
 * 规格
 * */
public class Specification {
    private List<MatsBean> mats;
    private List<TastesBean> tastes;
    private List<TasteListBeanX> tasteList;
    private List<ScaleBean> scales;
    private List<MakesBean> makes;

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

    public List<TasteListBeanX> getTasteList() {
        return tasteList;
    }

    public void setTasteList(List<TasteListBeanX> tasteList) {
        this.tasteList = tasteList;
    }

    public List<ScaleBean> getScales() {
        return scales;
    }

    public void setScales(List<ScaleBean> scales) {
        this.scales = scales;
    }

    public List<MakesBean> getMakes() {
        return makes;
    }

    public void setMakes(List<MakesBean> makes) {
        this.makes = makes;
    }

    public static class TasteListBeanX {
        /**
         * groupName : 甜
         * tasteList : [{"tasteId":1,"tasteName":"半糖","multiple":0.5},{"tasteId":2,"tasteName":"默认口味","multiple":1},{"tasteId":3,"tasteName":"多糖","multiple":2},{"tasteId":9,"tasteName":"少糖","multiple":0.2},{"tasteId":10,"tasteName":"无糖","multiple":0}]
         * groupId : 1
         */

        private String groupName;
        private int groupId;
        private List<TasteListBean> tasteList;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public List<TasteListBean> getTasteList() {
            return tasteList;
        }

        public void setTasteList(List<TasteListBean> tasteList) {
            this.tasteList = tasteList;
        }

        public static class TasteListBean {
            /**
             * tasteId : 1
             * tasteName : 半糖
             * multiple : 0.5
             */

            private int tasteId;
            private String tasteName;
            private double multiple;

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

            public double getMultiple() {
                return multiple;
            }

            public void setMultiple(double multiple) {
                this.multiple = multiple;
            }
        }
    }

    }


//    private List<MatsBean> mats;
//    private List<TastesBean> tasteList;
//    private List<MakesBean> makes;
//    private List<ScaleBean> scales;
//
//    public List<ScaleBean> getScales() {
//        return scales;
//    }
//
//    public void setScales(List<ScaleBean> scales) {
//        this.scales = scales;
//    }
//
//    public List<MatsBean> getMats() {
//        return mats;
//    }
//
//    public void setMats(List<MatsBean> mats) {
//        this.mats = mats;
//    }
//
//    public List<TastesBean> getTastes() {
//        return tasteList;
//    }
//
//    public void setTastes(List<TastesBean> tastes) {
//        this.tasteList = tastes;
//    }
//
//    public List<MakesBean> getMakes() {
//        return makes;
//    }
//
//    public void setMakes(List<MakesBean> makes) {
//        this.makes = makes;
//    }

