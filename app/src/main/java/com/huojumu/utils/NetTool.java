package com.huojumu.utils;

import android.support.annotation.NonNull;

import com.huojumu.model.ActivesBean;
import com.huojumu.model.AdsBean;
import com.huojumu.model.BaseBean;
import com.huojumu.model.BoxPay;
import com.huojumu.model.DailyInfo;
import com.huojumu.model.InventoryDetail;
import com.huojumu.model.InventoryList;
import com.huojumu.model.Material;
import com.huojumu.model.OrderBack;
import com.huojumu.model.OrderDetails;
import com.huojumu.model.OrderEnableBackBean;
import com.huojumu.model.OrdersList;
import com.huojumu.model.Production;
import com.huojumu.model.SmallType;
import com.huojumu.model.Specification;
import com.huojumu.model.StoreInfo;
import com.huojumu.model.UpdateBean;
import com.huojumu.model.VipListBean;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Description :
 *
 * @author Jie
 * date 2018/10/27.
 * email 494669467@qq.com
 */
public class NetTool {

    private static MyOkHttp okHttp = new MyOkHttp(new OkHttpClient.Builder().authenticator(new Authenticator() {

        @Override
        public Request authenticate(@NonNull Route route, @NonNull Response response) {
            return response.request().newBuilder().header(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN)).build();
        }
    })
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(90, TimeUnit.SECONDS)
            .build());

    public static MyOkHttp getNetApi() {
        return okHttp;
    }

    public static void getLoginQRCode(String machineCode, GsonResponseHandler<BaseBean<String>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "duty/createQRCode.action")
                .addParam("machineCode", machineCode)
                .enqueue(handler);
    }

    //设备所属信息查询
    static void getMachineInfo(String machineCode, GsonResponseHandler<BaseBean<StoreInfo>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "system/machineinfo.action")
                .addParam("machineCode", machineCode)
                .enqueue(handler);
    }

    //分类查询
    public static void getSmallType(int shopID, int enterpriseID, int pinpaiID, GsonResponseHandler<BaseBean<List<SmallType>>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "product/shopstype.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("shopID", shopID + "")
                .addParam("enterpriseID", enterpriseID + "")
                .addParam("pinpaiID", pinpaiID + "")
                .enqueue(handler);
    }

    //商品列表查询
    public static void getStoreProduces(int shopID, String isRecommend, GsonResponseHandler<BaseBean<List<Production>>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "product/showPros.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("shopID", shopID + "")
                .addParam("pinpaiID",SpUtil.getInt(Constant.PINPAI_ID)+"")
                .addParam("isRecommend", isRecommend)
                .enqueue(handler);
    }

    //盘点列表
    public static void getInventoryList(int shopId, int pageNum, GsonResponseHandler<BaseBean<InventoryList>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "store/queryCheckInventoryList.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("shopId", shopId + "")
                .addParam("pageNum", pageNum + "")
                .addParam("pageSize", "10")
                .enqueue(handler);
    }

    //盘点明细
    public static void getInventoryDetail(String checkId, GsonResponseHandler<BaseBean<List<InventoryDetail>>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "store/queryCheckInventoryDetail.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("checkId", checkId)
                .enqueue(handler);
    }

    //提交订单
    public static void postOrder(String json, GsonResponseHandler<BaseBean<OrderBack>> handler) {
        okHttp.post().url(Constant.BASE_URL + "order/submit.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("data", json)
                .enqueue(handler);
    }

    //修改密码
    public static void changePWD(String old, String newPwd, GsonResponseHandler<BaseBean<String>> handler) {
        okHttp.post().url(Constant.BASE_URL + "system/editPwd.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("pwd", old)
                .addParam("newPwd", newPwd)
                .enqueue(handler);
    }

    //小白盒支付
    public static void payByBox(String orderNo, String payType,String authNo, GsonResponseHandler<BaseBean<BoxPay>> handler){
        okHttp.post().url(Constant.BASE_URL + "pay/barcodepay.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("orderNo", orderNo)
                .addParam("payType", payType)
                .addParam("authNo", authNo)
                .enqueue(handler);
    }


    //盘点原料
    public static void getMaterial(int shopId, int enterpriseID, int pinpaiID, GsonResponseHandler<BaseBean<Material>> handler) {
        okHttp.post().url(Constant.BASE_URL + "store/wareall.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("id", shopId + "")
                .addParam("cid", enterpriseID + "")
                .addParam("pid", pinpaiID + "")
                .enqueue(handler);
    }

    //订单列表
    public static void getStoreOrderList(int pageNum, GsonResponseHandler<BaseBean<OrdersList>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "system/orderon.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("type", "7")
//                .addParam("ordSource", "3")
                .addParam("ordDate", PrinterUtil.getTime())
                .addParam("pageNum", pageNum + "")
                .addParam("pageSize", "10")
                .enqueue(handler);
    }

    //可退单列表
    public static void getEnableBackOrderList(int shopID, String orderNo, GsonResponseHandler<BaseBean<OrderEnableBackBean>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "pay/findOrderByNo.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("shopId", shopID + "")
                .addParam("orderNo", orderNo)
                .enqueue(handler);
    }


    //订单详情
    public static void getOrderInfo(String orderid, GsonResponseHandler<BaseBean<OrderDetails>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "system/orderdetail.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("orderid", orderid)
                .enqueue(handler);
    }

    //退单
    public static void getPayBack(int shopId, String orderId, String payType, GsonResponseHandler<BaseBean<String>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "pay/refund.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("shopId", shopId + "")
                .addParam("orderId", orderId + "")
                .addParam("cancelRadio", "pos退单")
                .addParam("payType", payType)
                .addParam("cancelTadio", "pos退单")
                .enqueue(handler);
    }

    //交班
    public static void takeOver(long timestamp, GsonResponseHandler<BaseBean<String>> handler) {
        okHttp.post().url(Constant.BASE_URL + "duty/shift.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("token", SpUtil.getString(Constant.MY_TOKEN).split(" ")[1])
                .addParam("timestamp", timestamp + "")
                .enqueue(handler);
    }

    //日結
    public static void settlement(int storeId, GsonResponseHandler<BaseBean<String>> handler) {
        okHttp.post().url(Constant.BASE_URL + "duty/settlement.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("token", SpUtil.getString(Constant.MY_TOKEN).split(" ")[1])
                .addParam("storeId", storeId + "")
                .enqueue(handler);
    }

    //交班工作信息
    public static void getDailyInfo(int storeId, int pinpaiId, int no, int status, GsonResponseHandler<BaseBean<DailyInfo>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "duty/shiftInfo.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("token", SpUtil.getString(Constant.MY_TOKEN).split(" ")[1])
                .addParam("storeId", "" + storeId)
                .addParam("pinpaiId", "" + pinpaiId)
                .addParam("pageNum", no + "")
                .addParam("pageSize", 10 + "")
                .addParam("status", status + "")
                .enqueue(handler);
    }

    //日結工作信息
    public static void getSettlementInfo(int storeId, int pageNum, GsonResponseHandler<BaseBean<DailyInfo>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "duty/settlementInfo.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("token", SpUtil.getString(Constant.MY_TOKEN).split(" ")[1])
                .addParam("storeId", "" + storeId)
                .addParam("pageNum", "" + pageNum)
                .addParam("pageSize", 10 + "")
                .enqueue(handler);
    }

    //查询单品规格信息
    public static void getSpecification(int pinpaiID, int proId, int shopId,GsonResponseHandler<BaseBean<Specification>> handler) {
        okHttp.post().url(Constant.BASE_URL + "product/tasteChoose.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("pinpaiID", pinpaiID + "")
                .addParam("proId", proId + "")
                .addParam("shopId", shopId + "")
                .enqueue(handler);
    }

    //活动信息列表
    public static void getActiveInfo(int shopID, int enterpriseID, int pinpaiID, GsonResponseHandler<BaseBean<List<ActivesBean>>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "salespromotion/getpromotion.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("shopID", "" + shopID)
                .addParam("enterpriseID", "" + enterpriseID)
                .addParam("pinpaiID", "" + pinpaiID)
                .enqueue(handler);
    }

    //会员信息列表
    public static void getVipList(int brandId, int pageNum, GsonResponseHandler<BaseBean<VipListBean>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "member/memberPages.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("brandId", "" + brandId)
                .addParam("pageNum", pageNum + "")
                .addParam("pageSize", "10")
                .enqueue(handler);
    }

    //广告
    public static void getAdsList(int shopID, GsonResponseHandler<BaseBean<List<AdsBean>>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "system/advsearch.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("shopID", "" + shopID)
                .addParam("fileType","3")
                .enqueue(handler);
    }

    /**
     * 更新版本查询
     * https://www.goodb2b.cn/filever/filemanager/queryLastVersion?projectName=pos
     */
    public static void updateApk(GsonResponseHandler<UpdateBean> handler){
        okHttp.get().url("https://www.goodb2b.cn/filever/filemanager/queryLastVersion?projectName=pos")
                .enqueue(handler);
    }

}
