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
import java.nio.charset.Charset;
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
     * 打印纸一行最大的字节
     */
    private static final int LINE_BYTE_SIZE_58 = 32;
    private static final int LINE_BYTE_SIZE_80 = 48;
    /**
     * 打印三列时，中间一列的中心线距离打印纸左侧的距离
     */
    private static final int LEFT_LENGTH_58 = 16;
    private static final int LEFT_LENGTH_80 = 24;
    /**
     * 打印三列时，中间一列的中心线距离打印纸右侧的距离
     */
    private static final int RIGHT_LENGTH_58 = 16;
    private static final int RIGHT_LENGTH_80 = 24;
    /**
     * 打印三列时，第一列汉字最多显示几个文字
     */
    private static final int LEFT_TEXT_MAX_LENGTH_58 = 5;
    private static final int LEFT_TEXT_MAX_LENGTH_80 = 7;

    /**
     * 复位打印机
     */
    public static final byte[] RESET = {0x1b, 0x40};

    /**
     * 左对齐
     */
    public static final byte[] ALIGN_LEFT = {0x1b, 0x61, 0x00};

    /**
     * 中间对齐
     */
    public static final byte[] ALIGN_CENTER = {0x1b, 0x61, 0x01};

    /**
     * 右对齐
     */
    public static final byte[] ALIGN_RIGHT = {0x1b, 0x61, 0x02};

    /**
     * 选择加粗模式
     */
    public static final byte[] BOLD = {0x1b, 0x45, 0x01};

    /**
     * 取消加粗模式
     */
    public static final byte[] BOLD_CANCEL = {0x1b, 0x45, 0x00};

    /**
     * 宽高加倍
     */
    public static final byte[] DOUBLE_HEIGHT_WIDTH = {0x1d, 0x21, 0x11};

    /**
     * 宽加倍
     */
    public static final byte[] DOUBLE_WIDTH = {0x1d, 0x21, 0x10};

    /**
     * 高加倍
     */
    public static final byte[] DOUBLE_HEIGHT = {0x1d, 0x21, 0x01};

    /**
     * 字体不放大
     */
    public static final byte[] NORMAL = {0x1d, 0x21, 0x00};

    /**
     * 设置默认行间距
     */
    public static final byte[] LINE_SPACING_DEFAULT = {0x1b, 0x32};

    private static int getBytesLength(String msg) {
        return msg.getBytes(Charset.forName("GB2312")).length;
    }

    /**
     * 打印两列
     *
     * @param leftText  左侧文字
     * @param rightText 右侧文字
     */
    private static String printTwoData58(String leftText, String rightText) {
        StringBuilder sb = new StringBuilder();
        int leftTextLength = getBytesLength(leftText);
        int rightTextLength = getBytesLength(rightText);
        sb.append(leftText);

        // 计算两侧文字中间的空格
        int marginBetweenMiddleAndRight = LINE_BYTE_SIZE_58 - leftTextLength - rightTextLength;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }
        sb.append(rightText);
        return sb.toString();
    }

    /**
     * 打印两列
     *
     * @param leftText  左侧文字
     * @param rightText 右侧文字
     */
    private static String printTwoData80(String leftText, String rightText) {
        StringBuilder sb = new StringBuilder();
        int leftTextLength = getBytesLength(leftText);
        int rightTextLength = getBytesLength(rightText);
        sb.append(leftText);

        // 计算两侧文字中间的空格
        int marginBetweenMiddleAndRight = LINE_BYTE_SIZE_80 - leftTextLength - rightTextLength;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }
        sb.append(rightText);
        return sb.toString();
    }

    /**
     * 打印三列
     *
     * @param leftText   左侧文字
     * @param middleText 中间文字
     * @param rightText  右侧文字
     */
    private static String printThreeData58(String leftText, String middleText, String rightText) {
        StringBuilder sb = new StringBuilder();
        // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        if (leftText.length() > LEFT_TEXT_MAX_LENGTH_58) {
            leftText = leftText.substring(0, LEFT_TEXT_MAX_LENGTH_58) + "..";
        }
        int leftTextLength = getBytesLength(leftText);
        int middleTextLength = getBytesLength(middleText);
        int rightTextLength = getBytesLength(rightText);

        sb.append(leftText);
        // 计算左侧文字和中间文字的空格长度
        int marginBetweenLeftAndMiddle = LEFT_LENGTH_58 - leftTextLength - middleTextLength / 2;

        for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
            sb.append(" ");
        }
        sb.append(middleText);

        // 计算右侧文字和中间文字的空格长度
        int marginBetweenMiddleAndRight = RIGHT_LENGTH_58 - middleTextLength / 2 - rightTextLength;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length() - 1, sb.length()).append(rightText);
        return sb.toString();
    }

    private static String printThreeData80(String leftText, String middleText, String rightText) {
        StringBuilder sb = new StringBuilder();
        // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        if (leftText.length() > LEFT_TEXT_MAX_LENGTH_80) {
            leftText = leftText.substring(0, LEFT_TEXT_MAX_LENGTH_80) + "..";
        }
        int leftTextLength = getBytesLength(leftText);
        int middleTextLength = getBytesLength(middleText);
        int rightTextLength = getBytesLength(rightText);

        sb.append(leftText);
        // 计算左侧文字和中间文字的空格长度
        int marginBetweenLeftAndMiddle = LEFT_LENGTH_80 - leftTextLength - middleTextLength / 2;

        for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
            sb.append(" ");
        }
        sb.append(middleText);

        // 计算右侧文字和中间文字的空格长度
        int marginBetweenMiddleAndRight = RIGHT_LENGTH_80 - middleTextLength / 2 - rightTextLength;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length() - 1, sb.length()).append(rightText);
        return sb.toString();
    }

    //开钱箱
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
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    public static String getTime() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        simpleDateFormat.format(date);
        return simpleDateFormat.format(date);
    }

    public static String getCNDate(){
        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        simpleDateFormat.format(date);
        return simpleDateFormat.format(date);
    }

    public static String getOrderNo() {
        simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss", Locale.CHINA);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 打印文本58mm样式// 32 字节
     */
    public static void printString58(final List<Products.ProductsBean> pList, final double totalPrice, final int totalNumber, final String orderNo, final String orderId, final String time) {
        try {
//            set(DOUBLE_HEIGHT_WIDTH);
            mPrinter.setCharSize(2, 2);
            String s = SpUtil.getString(Constant.PINPAI_ID) + "\n";
            mPrinter.printString(s, "GBK");
//            set(NORMAL);
            mPrinter.setCharSize(0, 0);
            s = SpUtil.getString(Constant.STORE_NAME) + "\n";
            mPrinter.printString(s, "GBK");
            mPrinter.setAlignMode(0);
            StringBuilder sb = new StringBuilder();
            sb.append("门店编号：").append(SpUtil.getString(Constant.STORE_ID)).append("\n")
                    .append("门店地址：").append(SpUtil.getString(Constant.STORE_ADDRESS)).append("\n")
                    .append("服务专线：").append(SpUtil.getString(Constant.STORE_TEL)).append("\n")
                    .append("订单编号：").append(orderId).append("\n")
                    .append("付款时间：").append(time).append("\n")
                    .append("打印时间：").append(simpleDateFormat.format(date)).append("\n\n");
            sb.append(printThreeData58("名称", "数量", "单价")).append("\n");
            for (Products.ProductsBean p : pList) {
                sb.append(printThreeData58(p.getProName(), String.valueOf(p.getNumber()), String.valueOf(p.getPrice()))).append("\n");
            }
            sb.append(printThreeData58("合计：", String.valueOf(totalNumber), String.valueOf(totalPrice))).append("\n");
            mPrinter.printString(sb.toString(), "GBK");
            s = "取货码，请妥善保管\n";
            mPrinter.setAlignMode(1);
            mPrinter.printString(s, "GBK");
            mPrinter.printAndFeedLine(1);
            printQRCode(orderNo);
            cutPaper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置命令
     */
    private static void set(byte[] bytes) {
        mPrinter.writeIO(bytes, 0, bytes.length, 100);
    }

    /**
     * 打印文本80mm小票样式 48 字节
     */
    public static void printString80(final List<Products.ProductsBean> pList, final double totalPrice, final int totalNumber, final String orderNo, final String orderId, final String time) {
        try {
//            set(DOUBLE_HEIGHT_WIDTH);
            mPrinter.setCharSize(2, 2);
            String s = SpUtil.getString(Constant.ENT_NAME) + "\n";
            mPrinter.printString(s, "GBK");
            set(NORMAL);
            mPrinter.setCharSize(0, 0);
            s = SpUtil.getString(Constant.STORE_NAME) + "\n";
            mPrinter.printString(s, "GBK");
            mPrinter.setAlignMode(0);
            StringBuilder sb = new StringBuilder();
            StringBuilder addon = new StringBuilder();
            sb.append("门店编号：").append(SpUtil.getInt(Constant.STORE_ID)).append("\n")
                    .append("门店地址：").append(SpUtil.getString(Constant.STORE_ADDRESS)).append("\n")
                    .append("服务专线：").append(SpUtil.getString(Constant.STORE_TEL)).append("\n")
                    .append("订单编号：").append(orderId).append("\n")
                    .append("付款时间：").append(time).append("\n")
                    .append("打印时间：").append(simpleDateFormat.format(date)).append("\n\n");
            sb.append(printThreeData80("名称", "数量", "单价")).append("\n");
            sb.append("------------------------------------------------").append("\n");
            addon.append("备注：");
            for (Products.ProductsBean p : pList) {
                sb.append(printThreeData58(p.getProName(), String.valueOf(p.getNumber()), String.valueOf(p.getPrice()))).append("\n");
                addon.append(p.getAddon()).append("\n");
            }
            sb.append(printThreeData80("合计：", String.valueOf(totalNumber), String.valueOf(totalPrice))).append("\n\n");
            mPrinter.printString(sb.toString(), "GBK");
            mPrinter.printString(addon.toString(), "GBK");
            s = "取货码，请妥善保管";
            mPrinter.setAlignMode(1);
            mPrinter.printString(s, "GBK");
            mPrinter.printAndFeedLine(1);
            printQRCode(orderNo);
            cutPaper();
            Log.e(TAG, "printString80: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
