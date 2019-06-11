package com.huojumu.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/16
 * Description: 扫描枪支付上传
 */
public class OrderInfo implements Parcelable {


    /**
     * orderID : 12121212122
     * createTime : 2017-01-01 00:00:00
     * shopID : 3
     * ordSource : 2
     * enterpriseID : 34
     * pinpaiID : 33
     * quanIds : [32,45,22]
     * orderType : 2
     * startTime : 2018-11-13 16:00:00
     * endTime : 2018-11-13 16:30:00
     * payType : 900
     * discountsType : 2
     * discountsActivity : 6
     * data : [{"proId":2,"num":3,"proType":"1","tastes":[{"tasteId":1},{"tasteId":2}],"makes":[{"practiceId":1},{"practiceId":2}],"mats":[{"proMatId":1},{"proMatId":2}]}]
     */

    private String orderID;
    private String oldOrderID;
    private String createTime;
    private int shopID;
    private String ordSource;
    private int enterpriseID;
    private int pinpaiID;
    private String orderType;
    private String startTime;
    private String endTime;
    private String payType;
    private String discountsType;
    private String discountsActivity;
    private double manualDiscount;
    private String manualDiscountReason;
    private List<Integer> quanIds;
    private List<DataBean> data;

    public String getOldOrderId() {
        return oldOrderID;
    }

    public void setOldOrderId(String oldOrderId) {
        this.oldOrderID = oldOrderId;
    }

    public double getManualDiscount() {
        return manualDiscount;
    }

    public void setManualDiscount(double manualDiscount) {
        this.manualDiscount = manualDiscount;
    }

    public String getManualDiscountReason() {
        return manualDiscountReason;
    }

    public void setManualDiscountReason(String manualDiscountReason) {
        this.manualDiscountReason = manualDiscountReason;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getDiscountsType() {
        return discountsType;
    }

    public void setDiscountsType(String discountsType) {
        this.discountsType = discountsType;
    }

    public String getDiscountsActivity() {
        return discountsActivity;
    }

    public void setDiscountsActivity(String discountsActivity) {
        this.discountsActivity = discountsActivity;
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

    public static class DataBean implements Parcelable {
        /**
         * proId : 2
         * num : 3
         * proType : 1
         * tastes : [{"tasteId":1},{"tasteId":2}]
         * makes : [{"practiceId":1},{"practiceId":2}]
         * mats : [{"proMatId":1},{"proMatId":2}]
         */

        private int proId;
        private int num;
        private String proType;
        private List<TastesBean> tastes;
        private List<MakesBean> makes;
        private List<MatsBean> mats;
        private List<ScaleBean> scales;

        public List<ScaleBean> getScales() {
            return scales;
        }

        public void setScales(List<ScaleBean> scales) {
            this.scales = scales;
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.proId);
            dest.writeInt(this.num);
            dest.writeString(this.proType);
            dest.writeTypedList(this.tastes);
            dest.writeList(this.makes);
            dest.writeTypedList(this.mats);
            dest.writeTypedList(this.scales);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.proId = in.readInt();
            this.num = in.readInt();
            this.proType = in.readString();
            this.tastes = in.createTypedArrayList(TastesBean.CREATOR);
            this.makes = new ArrayList<MakesBean>();
            in.readList(this.makes, MakesBean.class.getClassLoader());
            this.mats = in.createTypedArrayList(MatsBean.CREATOR);
            this.scales = in.createTypedArrayList(ScaleBean.CREATOR);
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderID);
        dest.writeString(this.createTime);
        dest.writeInt(this.shopID);
        dest.writeString(this.ordSource);
        dest.writeInt(this.enterpriseID);
        dest.writeInt(this.pinpaiID);
        dest.writeString(this.orderType);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.payType);
        dest.writeString(this.discountsType);
        dest.writeString(this.discountsActivity);
        dest.writeDouble(this.manualDiscount);
        dest.writeString(this.manualDiscountReason);
        dest.writeList(this.quanIds);
        dest.writeList(this.data);
    }

    public OrderInfo() {
    }

    protected OrderInfo(Parcel in) {
        this.orderID = in.readString();
        this.createTime = in.readString();
        this.shopID = in.readInt();
        this.ordSource = in.readString();
        this.enterpriseID = in.readInt();
        this.pinpaiID = in.readInt();
        this.orderType = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.payType = in.readString();
        this.discountsType = in.readString();
        this.discountsActivity = in.readString();
        this.manualDiscount = in.readInt();
        this.manualDiscountReason = in.readString();
        this.quanIds = new ArrayList<Integer>();
        in.readList(this.quanIds, Integer.class.getClassLoader());
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderInfo> CREATOR = new Parcelable.Creator<OrderInfo>() {
        @Override
        public OrderInfo createFromParcel(Parcel source) {
            return new OrderInfo(source);
        }

        @Override
        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };
}
