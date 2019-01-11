package com.huojumu.model;

/**
 * @author : Jie
 * Date: 2018/9/27
 * Description:
 */
public class StoreInfo {


    /**
     * shop : {"sysId":1,"latitude":null,"longtitude":null,"standardId":1,"shopName":"展会门店1","remark":null,"cityId":6,"parentEntId":2,"countryId":8,"eMail":"1@1.com","sysStatus":null,"updateBy":1,"id":1,"addr":"北京市朝阳区XXX街56号","mobile":"18555555558","telephone":"010-55545422","updateTime":"2018-10-17 14:39:53","provinceId":36,"linkman":"直营门店管理员","isPayment":null,"createBy":2,"areaId":11,"createTime":"2018-08-07 13:17:21","memberType":"3","shopNo":"MD-CN11-0001-0001-0001","status":"0","storeCode":null}
     * enterPrise : {"licenseNo":"8945489415151","entName":"直营企业01","wechatAppid":null,"msgId":null,"remark":null,"cityId":6,"discountsType":null,"parentEntId":1,"countryId":8,"openingBank":"华夏银行","eMail":"3@3.com","entNo":"ZY-CN11-0001-0001","entType":"2","accountname":"莉莉","serviceTel":"010-55548879","updateBy":null,"legalPerson":"莉莉","registerAddr":"北京市朝阳区XXX街56号&lt:&gt;","id":2,"addr":"北京市朝阳区XXX街56号","legalPersonIdNo":"155555558461284252","accountType":"1","mobile":"13111111111","businessScope":"奶茶、咖啡、西点","telephone":"02228174849","updateTime":null,"establishmentTime":"2018-08-01 00:00:00","provinceId":36,"linkman":"莉莉","zipcode":"300001","isPayment":null,"createBy":1,"areaId":11,"organizationCode":"4158641894981","appid":"","validTime":"2020-05-09 00:00:00","msgCount":null,"memberType":"2","shortName":"直营企业01","cerateTime":"2018-08-07 13:09:30","registerName":"直营企业01","account":"45564456415649648","merchantNo":"","status":"0"}
     * equipment : {"eqpNo":"EQP-03-0059","eqpType":"3","latitude":null,"longtitude":null,"scale":null,"remark":null,"repairCycle":null,"updateTime":"2019-01-10 18:26:10","terminalId":null,"accessToken":null,"terminalTime":null,"createBy":null,"uniqueCode":"b77d162c-c0a4-46d8-ac97-3c92c7e97258","updateBy":null,"createTime":"2019-01-10 18:26:10","eqpName":null,"onlineTime":null,"attention":null,"serviceLife":null,"location":null,"id":60,"shopId":1,"status":"0"}
     * parentEnterPrise : {"licenseNo":"8945489415151","entName":"测试品牌","wechatAppid":"","msgId":null,"remark":null,"threshold":0,"cityId":6,"discountsType":"1","parentEntId":null,"countryId":8,"openingBank":"华夏银行","eMail":"22@qq.com","entNo":"PP-CN11-0001","entType":"1","accountname":"李月","serviceTel":"010-66666666","updateBy":1,"legalPerson":"李月","registerAddr":"北京市朝阳区XXX街56号","id":1,"addr":"北京市朝阳区XXX街56号","legalPersonIdNo":"142557898949879","accountType":"1","logoPath":"https://www.goodb2b.cn/bootstrap_file/bbd0ad715d528697d90c9252156afdb8.png","mobile":"18555555558","businessScope":"奶茶、咖啡、西点","telephone":"010-55545422","updateTime":"2019-01-03 13:05:59","establishmentTime":"2018-08-07 00:00:00","provinceId":36,"linkman":"李月","zipcode":"300000","isPayment":null,"createBy":1,"areaId":11,"organizationCode":"4158641894981","appid":"wxe719f890bf88321a","validTime":"2020-07-15 00:00:00","msgCount":null,"memberType":"1","shortName":"测试品牌","cerateTime":"2018-08-07 11:41:53","registerName":"小玎小珰","account":"45564456415649648","merchantNo":"830400270000006","status":"0"}
     */

    private ShopBean shop;
    private EnterPriseBean enterPrise;
    private EquipmentBean equipment;
    private ParentEnterPriseBean parentEnterPrise;

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public EnterPriseBean getEnterPrise() {
        return enterPrise;
    }

    public void setEnterPrise(EnterPriseBean enterPrise) {
        this.enterPrise = enterPrise;
    }

    public EquipmentBean getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentBean equipment) {
        this.equipment = equipment;
    }

    public ParentEnterPriseBean getParentEnterPrise() {
        return parentEnterPrise;
    }

    public void setParentEnterPrise(ParentEnterPriseBean parentEnterPrise) {
        this.parentEnterPrise = parentEnterPrise;
    }

    public static class ShopBean {
        /**
         * sysId : 1
         * latitude : null
         * longtitude : null
         * standardId : 1
         * shopName : 展会门店1
         * remark : null
         * cityId : 6
         * parentEntId : 2
         * countryId : 8
         * eMail : 1@1.com
         * sysStatus : null
         * updateBy : 1
         * id : 1
         * addr : 北京市朝阳区XXX街56号
         * mobile : 18555555558
         * telephone : 010-55545422
         * updateTime : 2018-10-17 14:39:53
         * provinceId : 36
         * linkman : 直营门店管理员
         * isPayment : null
         * createBy : 2
         * areaId : 11
         * createTime : 2018-08-07 13:17:21
         * memberType : 3
         * shopNo : MD-CN11-0001-0001-0001
         * status : 0
         * storeCode : null
         */

        private int sysId;
        private Object latitude;
        private Object longtitude;
        private int standardId;
        private String shopName;
        private Object remark;
        private int cityId;
        private int parentEntId;
        private int countryId;
        private String eMail;
        private Object sysStatus;
        private int updateBy;
        private int id;
        private String addr;
        private String mobile;
        private String telephone;
        private String updateTime;
        private int provinceId;
        private String linkman;
        private Object isPayment;
        private int createBy;
        private int areaId;
        private String createTime;
        private String memberType;
        private String shopNo;
        private String status;
        private Object storeCode;

        public int getSysId() {
            return sysId;
        }

        public void setSysId(int sysId) {
            this.sysId = sysId;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getLongtitude() {
            return longtitude;
        }

        public void setLongtitude(Object longtitude) {
            this.longtitude = longtitude;
        }

        public int getStandardId() {
            return standardId;
        }

        public void setStandardId(int standardId) {
            this.standardId = standardId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public int getParentEntId() {
            return parentEntId;
        }

        public void setParentEntId(int parentEntId) {
            this.parentEntId = parentEntId;
        }

        public int getCountryId() {
            return countryId;
        }

        public void setCountryId(int countryId) {
            this.countryId = countryId;
        }

        public String getEMail() {
            return eMail;
        }

        public void setEMail(String eMail) {
            this.eMail = eMail;
        }

        public Object getSysStatus() {
            return sysStatus;
        }

        public void setSysStatus(Object sysStatus) {
            this.sysStatus = sysStatus;
        }

        public int getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(int updateBy) {
            this.updateBy = updateBy;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public Object getIsPayment() {
            return isPayment;
        }

        public void setIsPayment(Object isPayment) {
            this.isPayment = isPayment;
        }

        public int getCreateBy() {
            return createBy;
        }

        public void setCreateBy(int createBy) {
            this.createBy = createBy;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getMemberType() {
            return memberType;
        }

        public void setMemberType(String memberType) {
            this.memberType = memberType;
        }

        public String getShopNo() {
            return shopNo;
        }

        public void setShopNo(String shopNo) {
            this.shopNo = shopNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(Object storeCode) {
            this.storeCode = storeCode;
        }
    }

    public static class EnterPriseBean {
        /**
         * licenseNo : 8945489415151
         * entName : 直营企业01
         * wechatAppid : null
         * msgId : null
         * remark : null
         * cityId : 6
         * discountsType : null
         * parentEntId : 1
         * countryId : 8
         * openingBank : 华夏银行
         * eMail : 3@3.com
         * entNo : ZY-CN11-0001-0001
         * entType : 2
         * accountname : 莉莉
         * serviceTel : 010-55548879
         * updateBy : null
         * legalPerson : 莉莉
         * registerAddr : 北京市朝阳区XXX街56号&lt:&gt;
         * id : 2
         * addr : 北京市朝阳区XXX街56号
         * legalPersonIdNo : 155555558461284252
         * accountType : 1
         * mobile : 13111111111
         * businessScope : 奶茶、咖啡、西点
         * telephone : 02228174849
         * updateTime : null
         * establishmentTime : 2018-08-01 00:00:00
         * provinceId : 36
         * linkman : 莉莉
         * zipcode : 300001
         * isPayment : null
         * createBy : 1
         * areaId : 11
         * organizationCode : 4158641894981
         * appid :
         * validTime : 2020-05-09 00:00:00
         * msgCount : null
         * memberType : 2
         * shortName : 直营企业01
         * cerateTime : 2018-08-07 13:09:30
         * registerName : 直营企业01
         * account : 45564456415649648
         * merchantNo :
         * status : 0
         */

        private String licenseNo;
        private String entName;
        private Object wechatAppid;
        private Object msgId;
        private Object remark;
        private int cityId;
        private Object discountsType;
        private int parentEntId;
        private int countryId;
        private String openingBank;
        private String eMail;
        private String entNo;
        private String entType;
        private String accountname;
        private String serviceTel;
        private Object updateBy;
        private String legalPerson;
        private String registerAddr;
        private int id;
        private String addr;
        private String legalPersonIdNo;
        private String accountType;
        private String mobile;
        private String businessScope;
        private String telephone;
        private Object updateTime;
        private String establishmentTime;
        private int provinceId;
        private String linkman;
        private String zipcode;
        private Object isPayment;
        private int createBy;
        private int areaId;
        private String organizationCode;
        private String appid;
        private String validTime;
        private Object msgCount;
        private String memberType;
        private String shortName;
        private String cerateTime;
        private String registerName;
        private String account;
        private String merchantNo;
        private String status;

        public String getLicenseNo() {
            return licenseNo;
        }

        public void setLicenseNo(String licenseNo) {
            this.licenseNo = licenseNo;
        }

        public String getEntName() {
            return entName;
        }

        public void setEntName(String entName) {
            this.entName = entName;
        }

        public Object getWechatAppid() {
            return wechatAppid;
        }

        public void setWechatAppid(Object wechatAppid) {
            this.wechatAppid = wechatAppid;
        }

        public Object getMsgId() {
            return msgId;
        }

        public void setMsgId(Object msgId) {
            this.msgId = msgId;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public Object getDiscountsType() {
            return discountsType;
        }

        public void setDiscountsType(Object discountsType) {
            this.discountsType = discountsType;
        }

        public int getParentEntId() {
            return parentEntId;
        }

        public void setParentEntId(int parentEntId) {
            this.parentEntId = parentEntId;
        }

        public int getCountryId() {
            return countryId;
        }

        public void setCountryId(int countryId) {
            this.countryId = countryId;
        }

        public String getOpeningBank() {
            return openingBank;
        }

        public void setOpeningBank(String openingBank) {
            this.openingBank = openingBank;
        }

        public String getEMail() {
            return eMail;
        }

        public void setEMail(String eMail) {
            this.eMail = eMail;
        }

        public String getEntNo() {
            return entNo;
        }

        public void setEntNo(String entNo) {
            this.entNo = entNo;
        }

        public String getEntType() {
            return entType;
        }

        public void setEntType(String entType) {
            this.entType = entType;
        }

        public String getAccountname() {
            return accountname;
        }

        public void setAccountname(String accountname) {
            this.accountname = accountname;
        }

        public String getServiceTel() {
            return serviceTel;
        }

        public void setServiceTel(String serviceTel) {
            this.serviceTel = serviceTel;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
        }

        public String getRegisterAddr() {
            return registerAddr;
        }

        public void setRegisterAddr(String registerAddr) {
            this.registerAddr = registerAddr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getLegalPersonIdNo() {
            return legalPersonIdNo;
        }

        public void setLegalPersonIdNo(String legalPersonIdNo) {
            this.legalPersonIdNo = legalPersonIdNo;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBusinessScope() {
            return businessScope;
        }

        public void setBusinessScope(String businessScope) {
            this.businessScope = businessScope;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public String getEstablishmentTime() {
            return establishmentTime;
        }

        public void setEstablishmentTime(String establishmentTime) {
            this.establishmentTime = establishmentTime;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public Object getIsPayment() {
            return isPayment;
        }

        public void setIsPayment(Object isPayment) {
            this.isPayment = isPayment;
        }

        public int getCreateBy() {
            return createBy;
        }

        public void setCreateBy(int createBy) {
            this.createBy = createBy;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getOrganizationCode() {
            return organizationCode;
        }

        public void setOrganizationCode(String organizationCode) {
            this.organizationCode = organizationCode;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getValidTime() {
            return validTime;
        }

        public void setValidTime(String validTime) {
            this.validTime = validTime;
        }

        public Object getMsgCount() {
            return msgCount;
        }

        public void setMsgCount(Object msgCount) {
            this.msgCount = msgCount;
        }

        public String getMemberType() {
            return memberType;
        }

        public void setMemberType(String memberType) {
            this.memberType = memberType;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getCerateTime() {
            return cerateTime;
        }

        public void setCerateTime(String cerateTime) {
            this.cerateTime = cerateTime;
        }

        public String getRegisterName() {
            return registerName;
        }

        public void setRegisterName(String registerName) {
            this.registerName = registerName;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getMerchantNo() {
            return merchantNo;
        }

        public void setMerchantNo(String merchantNo) {
            this.merchantNo = merchantNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class EquipmentBean {
        /**
         * eqpNo : EQP-03-0059
         * eqpType : 3
         * latitude : null
         * longtitude : null
         * scale : null
         * remark : null
         * repairCycle : null
         * updateTime : 2019-01-10 18:26:10
         * terminalId : null
         * accessToken : null
         * terminalTime : null
         * createBy : null
         * uniqueCode : b77d162c-c0a4-46d8-ac97-3c92c7e97258
         * updateBy : null
         * createTime : 2019-01-10 18:26:10
         * eqpName : null
         * onlineTime : null
         * attention : null
         * serviceLife : null
         * location : null
         * id : 60
         * shopId : 1
         * status : 0
         */

        private String eqpNo;
        private String eqpType;
        private Object latitude;
        private Object longtitude;
        private Object scale;
        private Object remark;
        private Object repairCycle;
        private String updateTime;
        private Object terminalId;
        private Object accessToken;
        private Object terminalTime;
        private Object createBy;
        private String uniqueCode;
        private Object updateBy;
        private String createTime;
        private Object eqpName;
        private Object onlineTime;
        private Object attention;
        private Object serviceLife;
        private Object location;
        private int id;
        private int shopId;
        private String status;

        public String getEqpNo() {
            return eqpNo;
        }

        public void setEqpNo(String eqpNo) {
            this.eqpNo = eqpNo;
        }

        public String getEqpType() {
            return eqpType;
        }

        public void setEqpType(String eqpType) {
            this.eqpType = eqpType;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getLongtitude() {
            return longtitude;
        }

        public void setLongtitude(Object longtitude) {
            this.longtitude = longtitude;
        }

        public Object getScale() {
            return scale;
        }

        public void setScale(Object scale) {
            this.scale = scale;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getRepairCycle() {
            return repairCycle;
        }

        public void setRepairCycle(Object repairCycle) {
            this.repairCycle = repairCycle;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Object getTerminalId() {
            return terminalId;
        }

        public void setTerminalId(Object terminalId) {
            this.terminalId = terminalId;
        }

        public Object getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(Object accessToken) {
            this.accessToken = accessToken;
        }

        public Object getTerminalTime() {
            return terminalTime;
        }

        public void setTerminalTime(Object terminalTime) {
            this.terminalTime = terminalTime;
        }

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public String getUniqueCode() {
            return uniqueCode;
        }

        public void setUniqueCode(String uniqueCode) {
            this.uniqueCode = uniqueCode;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getEqpName() {
            return eqpName;
        }

        public void setEqpName(Object eqpName) {
            this.eqpName = eqpName;
        }

        public Object getOnlineTime() {
            return onlineTime;
        }

        public void setOnlineTime(Object onlineTime) {
            this.onlineTime = onlineTime;
        }

        public Object getAttention() {
            return attention;
        }

        public void setAttention(Object attention) {
            this.attention = attention;
        }

        public Object getServiceLife() {
            return serviceLife;
        }

        public void setServiceLife(Object serviceLife) {
            this.serviceLife = serviceLife;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class ParentEnterPriseBean {
        /**
         * licenseNo : 8945489415151
         * entName : 测试品牌
         * wechatAppid :
         * msgId : null
         * remark : null
         * threshold : 0
         * cityId : 6
         * discountsType : 1
         * parentEntId : null
         * countryId : 8
         * openingBank : 华夏银行
         * eMail : 22@qq.com
         * entNo : PP-CN11-0001
         * entType : 1
         * accountname : 李月
         * serviceTel : 010-66666666
         * updateBy : 1
         * legalPerson : 李月
         * registerAddr : 北京市朝阳区XXX街56号
         * id : 1
         * addr : 北京市朝阳区XXX街56号
         * legalPersonIdNo : 142557898949879
         * accountType : 1
         * logoPath : https://www.goodb2b.cn/bootstrap_file/bbd0ad715d528697d90c9252156afdb8.png
         * mobile : 18555555558
         * businessScope : 奶茶、咖啡、西点
         * telephone : 010-55545422
         * updateTime : 2019-01-03 13:05:59
         * establishmentTime : 2018-08-07 00:00:00
         * provinceId : 36
         * linkman : 李月
         * zipcode : 300000
         * isPayment : null
         * createBy : 1
         * areaId : 11
         * organizationCode : 4158641894981
         * appid : wxe719f890bf88321a
         * validTime : 2020-07-15 00:00:00
         * msgCount : null
         * memberType : 1
         * shortName : 测试品牌
         * cerateTime : 2018-08-07 11:41:53
         * registerName : 小玎小珰
         * account : 45564456415649648
         * merchantNo : 830400270000006
         * status : 0
         */

        private String licenseNo;
        private String entName;
        private String wechatAppid;
        private Object msgId;
        private Object remark;
        private int threshold;
        private int cityId;
        private String discountsType;
        private Object parentEntId;
        private int countryId;
        private String openingBank;
        private String eMail;
        private String entNo;
        private String entType;
        private String accountname;
        private String serviceTel;
        private int updateBy;
        private String legalPerson;
        private String registerAddr;
        private int id;
        private String addr;
        private String legalPersonIdNo;
        private String accountType;
        private String logoPath;
        private String mobile;
        private String businessScope;
        private String telephone;
        private String updateTime;
        private String establishmentTime;
        private int provinceId;
        private String linkman;
        private String zipcode;
        private Object isPayment;
        private int createBy;
        private int areaId;
        private String organizationCode;
        private String appid;
        private String validTime;
        private Object msgCount;
        private String memberType;
        private String shortName;
        private String cerateTime;
        private String registerName;
        private String account;
        private String merchantNo;
        private String status;

        public String getLicenseNo() {
            return licenseNo;
        }

        public void setLicenseNo(String licenseNo) {
            this.licenseNo = licenseNo;
        }

        public String getEntName() {
            return entName;
        }

        public void setEntName(String entName) {
            this.entName = entName;
        }

        public String getWechatAppid() {
            return wechatAppid;
        }

        public void setWechatAppid(String wechatAppid) {
            this.wechatAppid = wechatAppid;
        }

        public Object getMsgId() {
            return msgId;
        }

        public void setMsgId(Object msgId) {
            this.msgId = msgId;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getThreshold() {
            return threshold;
        }

        public void setThreshold(int threshold) {
            this.threshold = threshold;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getDiscountsType() {
            return discountsType;
        }

        public void setDiscountsType(String discountsType) {
            this.discountsType = discountsType;
        }

        public Object getParentEntId() {
            return parentEntId;
        }

        public void setParentEntId(Object parentEntId) {
            this.parentEntId = parentEntId;
        }

        public int getCountryId() {
            return countryId;
        }

        public void setCountryId(int countryId) {
            this.countryId = countryId;
        }

        public String getOpeningBank() {
            return openingBank;
        }

        public void setOpeningBank(String openingBank) {
            this.openingBank = openingBank;
        }

        public String getEMail() {
            return eMail;
        }

        public void setEMail(String eMail) {
            this.eMail = eMail;
        }

        public String getEntNo() {
            return entNo;
        }

        public void setEntNo(String entNo) {
            this.entNo = entNo;
        }

        public String getEntType() {
            return entType;
        }

        public void setEntType(String entType) {
            this.entType = entType;
        }

        public String getAccountname() {
            return accountname;
        }

        public void setAccountname(String accountname) {
            this.accountname = accountname;
        }

        public String getServiceTel() {
            return serviceTel;
        }

        public void setServiceTel(String serviceTel) {
            this.serviceTel = serviceTel;
        }

        public int getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(int updateBy) {
            this.updateBy = updateBy;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
        }

        public String getRegisterAddr() {
            return registerAddr;
        }

        public void setRegisterAddr(String registerAddr) {
            this.registerAddr = registerAddr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getLegalPersonIdNo() {
            return legalPersonIdNo;
        }

        public void setLegalPersonIdNo(String legalPersonIdNo) {
            this.legalPersonIdNo = legalPersonIdNo;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getLogoPath() {
            return logoPath;
        }

        public void setLogoPath(String logoPath) {
            this.logoPath = logoPath;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBusinessScope() {
            return businessScope;
        }

        public void setBusinessScope(String businessScope) {
            this.businessScope = businessScope;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getEstablishmentTime() {
            return establishmentTime;
        }

        public void setEstablishmentTime(String establishmentTime) {
            this.establishmentTime = establishmentTime;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public Object getIsPayment() {
            return isPayment;
        }

        public void setIsPayment(Object isPayment) {
            this.isPayment = isPayment;
        }

        public int getCreateBy() {
            return createBy;
        }

        public void setCreateBy(int createBy) {
            this.createBy = createBy;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getOrganizationCode() {
            return organizationCode;
        }

        public void setOrganizationCode(String organizationCode) {
            this.organizationCode = organizationCode;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getValidTime() {
            return validTime;
        }

        public void setValidTime(String validTime) {
            this.validTime = validTime;
        }

        public Object getMsgCount() {
            return msgCount;
        }

        public void setMsgCount(Object msgCount) {
            this.msgCount = msgCount;
        }

        public String getMemberType() {
            return memberType;
        }

        public void setMemberType(String memberType) {
            this.memberType = memberType;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getCerateTime() {
            return cerateTime;
        }

        public void setCerateTime(String cerateTime) {
            this.cerateTime = cerateTime;
        }

        public String getRegisterName() {
            return registerName;
        }

        public void setRegisterName(String registerName) {
            this.registerName = registerName;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getMerchantNo() {
            return merchantNo;
        }

        public void setMerchantNo(String merchantNo) {
            this.merchantNo = merchantNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
