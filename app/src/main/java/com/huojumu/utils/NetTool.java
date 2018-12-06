package com.huojumu.utils;

import com.huojumu.model.BaseBean;
import com.huojumu.model.InventoryList;
import com.huojumu.model.Material;
import com.huojumu.model.OrderBack;
import com.huojumu.model.OrderBackInfo;
import com.huojumu.model.OrdersList;
import com.huojumu.model.Products;
import com.huojumu.model.SmallType;
import com.huojumu.model.StoreInfo;
import com.huojumu.model.UpdateBean;
import com.huojumu.model.Vips;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.util.List;

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
        public Request authenticate(Route route, Response response) {
            return response.request().newBuilder().header(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN)).build();
        }
    })
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build());

    public static MyOkHttp getNetApi() {
        return okHttp;
    }

    public static void getCode(String machineCode, GsonResponseHandler<BaseBean<String>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "system/checkcode.action")
                .addParam("machineCode", machineCode)
                .enqueue(handler);
    }

    public static void login(String username, String password, String code, String machineCode, GsonResponseHandler<BaseBean<String>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "system/logon.action")
                .addParam("userCode", username)
                .addParam("password", password)
                .addParam("checkCode", code)
                .addParam("machineCode", machineCode)
                .enqueue(handler);
    }

    public static void getMachineInfo(String machineCode, GsonResponseHandler<BaseBean<StoreInfo>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "system/machineinfo.action")
                .addParam("machineCode", machineCode)
                .enqueue(handler);
    }

    public static void getSmallType(int shopID, int enterpriseID, int pinpaiID, GsonResponseHandler<BaseBean<List<SmallType>>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "product/shopstype.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("shopID", shopID + "")
                .addParam("enterpriseID", enterpriseID + "")
                .addParam("pinpaiID", pinpaiID + "")
                .enqueue(handler);
    }

    public static void getStoreProduces(int shopID, int enterpriseID, int pinpaiID, GsonResponseHandler<BaseBean<Products>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "product/shopproduct.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("shopID", shopID + "")
                .addParam("enterpriseID", enterpriseID + "")
                .addParam("pinpaiID", pinpaiID + "")
                .enqueue(handler);
    }

    public static void getInventoryList(int shopId, int pageNum, GsonResponseHandler<BaseBean<InventoryList>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "store/queryCheckInventoryList.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("shopId", shopId + "")
                .addParam("enterpriseID", pageNum + "")
                .enqueue(handler);
    }

    public static void postOrder(String json, GsonResponseHandler<BaseBean<OrderBack>> handler) {
        okHttp.post().url(Constant.BASE_URL + "order/submit.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("data", json)
                .enqueue(handler);
    }

    public static void postOrder(String orderNo, String payType, String authNo, GsonResponseHandler<BaseBean<OrderBack>> handler) {
        okHttp.post().url(Constant.BASE_URL + "pay/barcodepay.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("orderNo", orderNo)
                .addParam("payType", payType)
                .addParam("authNo", authNo)
                .enqueue(handler);
    }

    public static void changePWD(String old, String newPwd, GsonResponseHandler<BaseBean<String>> handler) {
        okHttp.post().url(Constant.BASE_URL + "system/editPwd.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("pwd", old)
                .addParam("newPwd", newPwd)
                .enqueue(handler);
    }

    public static void getMaterial(int shopId, int enterpriseID, int pinpaiID, GsonResponseHandler<BaseBean<Material>> handler) {
        okHttp.post().url(Constant.BASE_URL + "store/wareall.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("id", shopId + "")
                .addParam("cid", enterpriseID + "")
                .addParam("pid", pinpaiID + "")
                .enqueue(handler);
    }

    //订单列表
    public static void getStoreOrders(int shopID, int enterpriseID, int pinpaiID, GsonResponseHandler<BaseBean<List<OrdersList>>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "order/getcuplist.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("shopID", shopID + "")
                .addParam("enterpriseID", enterpriseID + "")
                .addParam("pinpaiID", pinpaiID + "")
                .addParam("status", "1")
                .enqueue(handler);
    }

    //退单
    public static void getPayBack(int shopId, String orderId, String cancelRadio, GsonResponseHandler<BaseBean<String>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "pay/refund.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("shopId", shopId + "")
                .addParam("orderId", orderId + "")
                .addParam("cancelRadio", cancelRadio)
                .enqueue(handler);
    }

    public static void update(String uuid, GsonResponseHandler<BaseBean<UpdateBean>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "pay/refund.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("uuid", uuid)
                .enqueue(handler);
    }

    public static void getVipList(int enterpriseID, int pinpaiID, GsonResponseHandler<BaseBean<List<Vips>>> handler) {
        okHttp.post()
                .url(Constant.BASE_URL + "pay/refund.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("enterpriseID", enterpriseID + "")
                .addParam("pinpaiID", pinpaiID + "")
                .enqueue(handler);
    }

    public static void getOrderInfo(String orderid, GsonResponseHandler<BaseBean<OrderBackInfo>> handler) {///system/orderdetail.action
        okHttp.post()
                .url(Constant.BASE_URL + "pay/refund.action").addHeader(Constant.TOKEN, SpUtil.getString(Constant.MY_TOKEN))
                .addParam("orderid", orderid + "")
                .enqueue(handler);
    }
}
