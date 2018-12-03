//package com.haoshitong.utils;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.haoshitong.model.OrderBack;
//import com.haoshitong.model.OrderInfo;
//import com.haoshitong.net.BaseObserver;
//import com.haoshitong.net.NetTool;
//
///**
// * @author : Jie
// * Date: 2018/10/16
// * Description: 支付工具类
// */
//public class PaymentUtil {
//
//    private static Gson gson = new Gson();
//    /**
//     * 银联、支付宝、微信
//     */
//    public static void payOnline(OrderInfo orderInfo) {
//        String json = gson.toJson(orderInfo);
//        NetTool.postOrder(json, new BaseObserver<OrderBack>() {
//            @Override
//            public void onSuccess(OrderBack orderBack) {
//                SpUtil.save(Constant.PAY_BY_ALI, orderBack.getAliPayQrcode());
//                SpUtil.save(Constant.PAY_BY_WX, orderBack.getWxPayQrcode());
//                SpUtil.save(Constant.PAY_BY_BANK, orderBack.getChinaUnionQrcode());
//            }
//
//            @Override
//            public void onHandleError(int code, String msg) {
//
//            }
//        });
//    }
//
//    /**
//     * 扫描枪
//     */
//    public static void payByNative(String orderNo, String payType, String authNo) {
////        NetTool.postOrder(orderNo, payType, authNo, new BaseObserver<OrderBack>() {
////            @Override
////            public void onSuccess(OrderBack orderBack) {
////
////            }
////
////            @Override
////            public void onHandleError(int code, String msg) {
////
////            }
////        });
//    }
//
//}
