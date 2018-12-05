package com.huojumu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.data.OrderSave;
import com.google.gson.Gson;
import com.huojumu.model.OrderInfo;
import com.huojumu.model.Products;
import com.szsicod.print.escpos.PrinterAPI;
import com.szsicod.print.io.USBAPI;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author : Jie
 * Date: 2018/5/25
 * Description: 打印机辅助类
 */
public class PrinterUtil {

    private static String TAG = "PrinterUtil";
    private static PrinterAPI mPrinter;
    private static USBAPI io;
    private static Date date;
    private static SimpleDateFormat simpleDateFormat;
    private static Gson gson;
    /**
     * 是否
     */
    private boolean cut_bitmap = false;

    //十六进制开钱箱
    public static void OpenMoneyBox() {
        byte[] bytes = {0x1B, 0x70, 0x0, 0x3C, (byte) 0xFF};
        try {
            mPrinter.writeIO(bytes, 0, bytes.length - 1, 1000);
        } catch (Exception e) {
            Log.d(TAG, "OpenMoneyBox: error");
        }
    }

    public static String toJson(OrderInfo orderInfo) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(orderInfo);
    }

    public static String toJson(List<OrderSave> orderInfo) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(orderInfo);
    }

    /**
     * 连接打印机
     *
     * @param context 上下文
     */
    public static void connectPrinter(Context context) {
        mPrinter = new PrinterAPI();
        io = new USBAPI(context);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        date = new Date(System.currentTimeMillis());
        if (PrinterAPI.SUCCESS == mPrinter.connect(io)) {
            ToastUtils.showLong("已连接打印机");
        } else {
            ToastUtils.showLong("未成功连接打印机");
        }
    }

    /**
     * 断开打印机
     */
    public static void disconnectPrinter() {
        if (PrinterAPI.SUCCESS == mPrinter.disconnect()) {
            io.closeDevice();
            Log.d(TAG, "disconnectPrinter: finish");
        }
    }

    public static String getDate() {
        return simpleDateFormat.format(date);
    }

    public static String getTime() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        simpleDateFormat.format(date);
        return simpleDateFormat.format(date);
    }

    /**
     * 打印文本
     */
    public static void printString(final List<Products.ProductsBean> pList, final double totalPrice, final int totalNumber, final String orderNo,final String orderId, final String time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mPrinter.setCharSize(2, 2);
                    mPrinter.setAlignMode(1);
                    StringBuilder sb = new StringBuilder();
                    String s = "小玎小珰\n";
                    mPrinter.printString(s, "GBK");
                    mPrinter.setAlignMode(0);
                    s = SpUtil.getString(Constant.STORE_NAME) + "\n";
                    mPrinter.setCharSize(0, 0);
                    mPrinter.printString(s, "GBK");
                    mPrinter.printAndFeedLine(1);
                    mPrinter.printAndBackToStd();
                    s = "";
                    s += "门店编号：" + SpUtil.getString(Constant.STORE_ID) + "\n"
                            + "门店地址：" + SpUtil.getString(Constant.STORE_ADDRESS) + "\n"
                            + "服务专线：" + SpUtil.getString(Constant.STORE_TEL) + "\n"
                            + "订单编号：" + orderId + "\n"
                            + "付款时间：" + time + "\n"
                            + "打印时间：" + simpleDateFormat.format(date) + "\n";
                    mPrinter.setCharSize(0, 0);
                    mPrinter.printString(s, "GBK");
                    mPrinter.printAndFeedLine(1);
                    sb.append("名称        数量   单价   金额\n ").append("----------------------------------\n");
                    for (Products.ProductsBean p : pList) {
                        sb.append(p.getProName())
                                .append("   ").append(p.getNumber())
                                .append("   ").append(p.getPrice())
                                .append("   ").append(p.getPrice() * p.getNumber()).append("\n");
                    }
                    sb.append("共计：").append("        ").append(totalNumber).append("           ").append(totalPrice).append("\n");
                    mPrinter.printAndFeedLine(1);
                    mPrinter.printString(sb.toString(), "GBK");
                    s = "取货码，请妥善保管";
                    mPrinter.setAlignMode(1);
                    mPrinter.printString(s, "GBK");
                    mPrinter.printAndFeedLine(1);
                    printQRCode(orderNo);
                    cutPaper();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 打印条形码
     *
     * @param s 条形码内容
     */
    public static void printBarCode(String s) {
        if (PrinterAPI.SUCCESS == mPrinter.printBarCode(69, 10, s)) {
            Log.d(TAG, "printBarCode: finish");
        }
    }

    /**
     * 打印二维码
     *
     * @param s 二维码内容
     */
    private static void printQRCode(String s) {
        if (PrinterAPI.SUCCESS == mPrinter.printQRCode(s)) {
            Log.d(TAG, "printQRCode: finish");
        }
    }

    /**
     * 打印图片
     *
     * @param view 显示图片的view
     */
    public static void printImage(ImageView view) {
        view.setDrawingCacheEnabled(true);
        try {
            if (PrinterAPI.SUCCESS == mPrinter.printRasterBitmap(view.getDrawingCache())) {
                view.setDrawingCacheEnabled(false);
                Log.d(TAG, "printImage: finish");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印图片
     *
     * @param bitmap 要打印的图片
     */
    public static void printImage(Bitmap bitmap) {
        try {
            if (PrinterAPI.SUCCESS == mPrinter.printRasterBitmap(bitmap)) {
                Log.d(TAG, "printImage: finish");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 切纸
     */
    private static void cutPaper() {
        if (PrinterAPI.SUCCESS == mPrinter.cutPaper(66, 0)) {
            Log.d(TAG, "cutPaper: finish");
        }
    }

    public static void getStatus() {
        mPrinter.getStatus();
    }

}
