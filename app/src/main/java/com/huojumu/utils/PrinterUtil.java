package com.huojumu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.huojumu.MyApplication;
import com.huojumu.R;
import com.huojumu.model.MatsBean;
import com.huojumu.model.OrderDetails;
import com.huojumu.model.Production;
import com.szsicod.print.escpos.PrinterAPI;
import com.szsicod.print.io.USBAPI;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
     * 四列时，第三列的中心线
     */
    private static final int THREE_LENGTH_80 = 36;
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

    /**
     * 清空打印缓存区
     */
    public static final byte[] CLEAR_TEMP = {0x1B, 0x40};

    private static int getBytesLength(String msg) {
        return msg.getBytes(Charset.forName("GB2312")).length;
    }

    public static PrinterAPI getmPrinter() {
        return mPrinter;
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

    private static String printThreeData80(String leftText, String middleText, String three, String rightText) {
        StringBuilder sb = new StringBuilder();
        // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        if (leftText.length() > LEFT_TEXT_MAX_LENGTH_80) {
            leftText = leftText.substring(0, LEFT_TEXT_MAX_LENGTH_80) + "..";
        }
        int leftTextLength = getBytesLength(leftText);
        int middleTextLength = getBytesLength(middleText);
        int threeTextLength = getBytesLength(three);
        int rightTextLength = getBytesLength(rightText);

        sb.append(leftText);
        // 计算左侧文字和中间文字的空格长度
        int marginBetweenLeftAndMiddle = LEFT_LENGTH_80 - leftTextLength - (middleTextLength % 2 == 1 ? (middleTextLength + 1) / 2 : middleTextLength / 2);

        for (int i = 0; i < marginBetweenLeftAndMiddle + 2; i++) {
            sb.append(" ");
        }
        sb.append(middleText);
        //计算中间文字和第三文字的空格长度
        int marginBetweenThreeAndFour = (RIGHT_LENGTH_80 - (middleTextLength % 2 == 1 ? (middleTextLength + 1) / 2 : middleTextLength / 2) / 2 - threeTextLength - rightTextLength) / 2;

        for (int i = 0; i < marginBetweenThreeAndFour + 2; i++) {
            sb.append(" ");
        }
        sb.append(three);
        // 计算第四文字和第三文字的空格长度
//        int marginBetweenMiddleAndRight = 12 - threeTextLength / 2 - rightTextLength;

        for (int i = 0; i < marginBetweenThreeAndFour + 2; i++) {
            sb.append(" ");
        }

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格.delete(sb.length() - 1, sb.length())
        sb.append(rightText);
        return sb.toString();
    }

    private static String printFourData80(String leftText, String middleText, String three, String rightText) {
        StringBuilder sb = new StringBuilder();
        // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        if (leftText.length() > LEFT_TEXT_MAX_LENGTH_80) {
            leftText = leftText.substring(0, LEFT_TEXT_MAX_LENGTH_80) + "..";
        }
        int leftTextLength = getBytesLength(leftText);
        int middleTextLength = getBytesLength(middleText);
        int threeTextLength = getBytesLength(three);
        int rightTextLength = getBytesLength(rightText);

        sb.append(leftText);
        // 计算左侧文字和中间文字的空格长度
        int marginBetweenLeftAndMiddle = LEFT_LENGTH_80 - leftTextLength - (middleTextLength % 2 == 1 ? (middleTextLength + 1) / 2 : middleTextLength / 2);

        for (int i = 0; i < marginBetweenLeftAndMiddle + 4; i++) {
            sb.append(" ");
        }
        sb.append(middleText);
        //计算中间文字和第三文字的空格长度
        int marginBetweenThreeAndFour = (RIGHT_LENGTH_80 - (middleTextLength % 2 == 1 ? (middleTextLength + 1) / 2 : middleTextLength / 2) / 2 - threeTextLength - rightTextLength) / 2;

        for (int i = 0; i < marginBetweenThreeAndFour + 4; i++) {
            sb.append(" ");
        }
        sb.append(three);
        // 计算第四文字和第三文字的空格长度
//        int marginBetweenMiddleAndRight = 12 - threeTextLength / 2 - rightTextLength;

        for (int i = 0; i < marginBetweenThreeAndFour + 4; i++) {
            sb.append(" ");
        }

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格.delete(sb.length() - 1, sb.length())
        sb.append(rightText);
        return sb.toString();
    }

    public static String toJson(Object obj) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(obj);
    }

    /**
     * 连接打印机
     *
     * @param context 上下文
     */
    public static void connectPrinter(Context context) {
        mPrinter = new PrinterAPI();
        io = new USBAPI(context);
        if (PrinterAPI.SUCCESS == mPrinter.connect(io)) {
            Log.e("SUCCESS", "SUCCESS");
            ToastUtils.showLong("打印机连接成功");
        } else {
            Log.e("SUCCESS1", "SUCCESS1");
        }
    }

    public static UsbDevice getUsbDeviceFromName(Context context, String usbName) {
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> usbDeviceList = usbManager.getDeviceList();
        return usbDeviceList.get(usbName);
    }

    /**
     * 断开打印机
     */
    public static void disconnectPrinter() {
        if (mPrinter != null && PrinterAPI.SUCCESS == mPrinter.disconnect()) {
            io.closeDevice();
            mPrinter = null;
            Log.d(TAG, "disconnectPrinter: finish");
        }
    }

    public static String getDate() {
        date = new Date(System.currentTimeMillis());
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    static String getTime() {
        date = new Date(System.currentTimeMillis());
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    public static String getTabTime() {
        date = new Date(System.currentTimeMillis());
        simpleDateFormat = new SimpleDateFormat("MMdd", Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    public static String getTabHour() {
        date = new Date(System.currentTimeMillis());
        simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    private static String getPrintDate() {
        date = new Date(System.currentTimeMillis());
        simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    public static String getCNDate() {
        date = new Date(System.currentTimeMillis());
        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        return simpleDateFormat.format(date);
    }


    /**
     * 打印文本58mm样式// 32 字节
     */
    public static void printString58(final List<Production> pList, final double totalPrice, final int totalNumber, final String orderNo, final String orderId, final String time) {
//        try {
//            if (PrinterAPI.SUCCESS == mPrinter.connect(io)) {
//                mPrinter.setCharSize(2, 2);
//                String s = SpUtil.getString(Constant.PINPAI_ID) + "\n";
//                mPrinter.printString(s, "GBK");
//
//                mPrinter.setCharSize(0, 0);
//                s = SpUtil.getString(Constant.STORE_NAME) + "\n";
//                mPrinter.printString(s, "GBK");
//                mPrinter.setAlignMode(0);
//                StringBuilder sb = new StringBuilder();
//                sb.append("门店编号：").append(SpUtil.getString(Constant.STORE_ID)).append("\n")
//                        .append("门店地址：").append(SpUtil.getString(Constant.STORE_ADDRESS)).append("\n")
//                        .append("服务专线：").append(SpUtil.getString(Constant.STORE_TEL)).append("\n")
//                        .append("订单编号：").append(orderId).append("\n")
//                        .append("付款时间：").append(time).append("\n")
//                        .append("打印时间：").append(simpleDateFormat.format(date)).append("\n\n");
//                sb.append(printThreeData58("名称", "数量", "单价")).append("\n");
//                for (Production p : pList) {
//                    sb.append(printThreeData58(p.getProName(), String.valueOf(p.getNumber()), String.valueOf(p.getPrice()))).append("\n");
//                }
//                sb.append(printThreeData58("合计：", String.valueOf(totalNumber), String.valueOf(totalPrice))).append("\n");
//                mPrinter.printString(sb.toString(), "GBK");
//                s = "取货码，请妥善保管\n";
//                mPrinter.setAlignMode(1);
//                mPrinter.printString(s, "GBK");
//                mPrinter.printAndFeedLine(1);
//                printQRCode(orderNo);
//                cutPaper(mPrinter);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 设置命令
     */
    private static void set(byte[] bytes) {
        mPrinter.writeIO(bytes, 0, bytes.length, 100);
    }

    //开钱箱
    public static void OpenMoneyBox(final Context context) {
        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                PrinterAPI mPrinter = new PrinterAPI();
                USBAPI io = new USBAPI(context);
                try {
                    if (PrinterAPI.SUCCESS == mPrinter.connect(io)) {
                        byte[] bytes = {0x1B, 0x70, 0x0, 0x3C, (byte) 0xFF};
                        mPrinter.writeIO(bytes, 0, bytes.length - 1, 1000);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "run: ");
                } finally {
                    if (PrinterAPI.SUCCESS == mPrinter.disconnect()) {
                        io.closeDevice();
                        mPrinter = null;
                        io = null;
                        Log.d(TAG, "disconnectPrinter: finish");
                    }
                }
            }
        });
    }

    /**
     * 打印文本80mm小票样式 48 字节
     */
    public static void printString80(final Context context, final List<Production> pList, final String orderNo, final String name, final String totalMoney,
                                     final String earn, final String cost, final String charge, final String cut, final String date, final String type) {
        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                PrinterAPI mPrinter = new PrinterAPI();
                USBAPI io = new USBAPI(context);
                if (PrinterAPI.SUCCESS == mPrinter.connect(io)) {
                    try {
                        //居左
//                        mPrinter.setAlignMode(0);
                        //字体变大
                        mPrinter.setCharSize(1, 1);
                        //订单流水号
                        String s = "N" + orderNo;
                        mPrinter.printString(s, "GBK");

                        //实线
                        mPrinter.printRasterBitmap(MyApplication.getLine1());

                        //员工名 + 时间
                        s = "收银员：" + name + "\n" + "下单时间：" + date;
                        mPrinter.setAlignMode(0);
                        mPrinter.setCharSize(0, 0);
                        mPrinter.printString(s, "GBK");

                        // 间隔大的虚线
                        mPrinter.printRasterBitmap(MyApplication.getLine2());

                        //商品信息
                        StringBuilder sb = new StringBuilder();
                        sb.append(printThreeData80("商品名称", "数量", "单价", "金额")).append("\n");
                        mPrinter.setAlignMode(0);
                        mPrinter.setFontStyle(1);
                        for (Production p : pList) {
                            int n = p.getNumber();
                            sb.append(printThreeData80(p.getProName(), String.valueOf(n), String.valueOf(p.getPrice()), String.valueOf(n * p.getPrice()))).append("\n");
                            if (p.getMats().size() > 0)
                                for (MatsBean bean : p.getMats()) {
                                    sb.append(printThreeData80(" " + bean.getMatName(), String.valueOf(n), String.valueOf(bean.getIngredientPrice()), String.valueOf(n * bean.getIngredientPrice()))).append("\n");
                                }
                        }
                        Log.e(TAG, "print order：" + sb.toString());
                        mPrinter.printString(sb.toString(), "GBK");

                        //间隔小的虚线
                        mPrinter.printRasterBitmap(MyApplication.getLine3());
                        //交易金额明细
                        mPrinter.setFontStyle(0);

                        s = "\n" + printTwoData80("消费金额", totalMoney)
                                + "\n" + printTwoData80("应收金额", earn)
                                + "\n" + printTwoData80("客户实付", cost)
                                + "\n" + printTwoData80("优    惠", cut)
                                + "\n" + printTwoData80("找    零", charge)
                                + "\n" + printTwoData80("支付方式", type) + "\n";
                        mPrinter.printString(s, "GBK");

                        //虚实线
                        mPrinter.printRasterBitmap(MyApplication.getLine3());
                        mPrinter.printRasterBitmap(MyApplication.getLine1());

                        //居中
                        mPrinter.setAlignMode(1);
                        //logo图片9
                        mPrinter.printRasterBitmap(MyApplication.getLogo());
                        mPrinter.setCharSize(1, 1);

                        //店铺名
                        mPrinter.setAlignMode(0);
                        s = SpUtil.getString(Constant.STORE_NAME) + "\n";
                        mPrinter.printString(s, "GBK");

                        mPrinter.setAlignMode(0);
                        mPrinter.setCharSize(0, 0);
                        //企业文化描述
                        s = "7港9欢迎您的到来。我们再次从香港出发，希望搜集到各地的特色食品，港印全国。能7(去)香港(港)的(9)九龙喝一杯正宗的港饮是我们对每一位顾客的愿景。几百年来，香港作为东方接触世界的窗口，找寻并创造了一款款独具特色又流传世界的高品饮品。我们在全国超过十年的专业服务与坚持，与97回归共享繁华，秉承独到的调制方法，期许再一次与亲爱的你能擦出下一个十年火花。\n";
                        mPrinter.printString(s, "GBK");

                        mPrinter.setAlignMode(1);

                        //品牌二维码
                        mPrinter.printRasterBitmap(MyApplication.getQrcode());

                        //投诉、加盟热线
                        s = "\n投诉、加盟热线：010-62655878";
                        mPrinter.printString(s, "GBK");

                        //公司
                        s = "\n技术支持 火炬木科技";
                        mPrinter.printString(s, "GBK");

                        mPrinter.cutPaper(66, 0);
                        //打开钱箱
                        byte[] bytes = {0x1B, 0x70, 0x0, 0x3C, (byte) 0xFF};
                        mPrinter.writeIO(bytes, 0, bytes.length - 1, 1000);
                    } catch (Exception e) {
                        Log.d(TAG, e.getMessage());
                    } finally {
                        if (PrinterAPI.SUCCESS == mPrinter.disconnect()) {
                            io.closeDevice();
                            mPrinter = null;
                            io = null;
                            Log.d(TAG, "disconnectPrinter: finish");
                        }
                    }
                }
            }
        });

    }

    /**
     * 打印交班、日结
     */
    public static void printDaily(final Context context, final int type, final String total, final String mobilePay, final String cash,
                                  final int orderNum, final String workerName, final String lastDate) {
        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                PrinterAPI mPrinter = new PrinterAPI();
                USBAPI io = new USBAPI(context);
                if (PrinterAPI.SUCCESS == mPrinter.connect(io)) {
                    try {
                        mPrinter.setAlignMode(1);
                        mPrinter.setCharSize(1, 1);
                        String t = type == 1 ? "交班" : "日结\n";
                        mPrinter.printString(t, "GBK");
                        mPrinter.printFeed();

                        //居左
                        mPrinter.setAlignMode(0);
                        String s = t + "日期：\n";
                        mPrinter.printString(s, "GBK");

                        mPrinter.setCharSize(0, 0);
                        s = "本次" + t + "时间：" + getDate() + "\n"
                                + "上次" + t + "时间" + lastDate + "\n"
                                + t + "人员：" + workerName + "\n";
                        mPrinter.printString(s, "GBK");

                        mPrinter.setCharSize(1, 1);
                        s = "交款信息：\n";
                        mPrinter.printString(s, "GBK");

                        mPrinter.setCharSize(0, 0);
                        s = "本次" + t + "销售总额：" + total + "\n"
                                + "总虚收金额：" + mobilePay + "\n"
                                + "总实收金额：" + cash + "\n"
                                + "总  单  数：" + orderNum + "\n";
                        mPrinter.printString(s, "GBK");

                        mPrinter.printFeed();
                        mPrinter.setAlignMode(1);
                        //公司
                        s = "\n技术支持 火炬木科技";
                        mPrinter.printString(s, "GBK");

                        mPrinter.cutPaper(66, 0);
                    } catch (Exception e) {
                        ToastUtils.showLong("打印机连接出错");
                    } finally {
                        if (PrinterAPI.SUCCESS == mPrinter.disconnect()) {
                            io.closeDevice();
                            mPrinter = null;
                            io = null;
                            Log.d(TAG, "disconnectPrinter: finish");
                        }
                    }
                }
            }
        });

    }

    /**
     * 打印退单
     */
    public static void printPayBack(final Context context, final OrderDetails details, final String date) {
        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                PrinterAPI mPrinter = new PrinterAPI();
                USBAPI io = new USBAPI(context);
                if (PrinterAPI.SUCCESS == mPrinter.connect(io)) {
                    try {
                        mPrinter.setAlignMode(1);
                        mPrinter.setCharSize(1, 1);
                        String s = "退账单";
                        mPrinter.printString(s, "GBK");
                        mPrinter.printFeed();

                        mPrinter.setAlignMode(0);
                        mPrinter.setCharSize(0, 0);
                        s = "操作人：" + SpUtil.getString(Constant.WORKER_NAME)
                                + "\n" + "下单时间：" + details.getOrderdetail().getCreateTime()
                                + "\n" + "退单时间：" + date
                                + "\n" + "原订单号：" + details.getOrderdetail().getOrdNo().substring(details.getOrderdetail().getOrdNo().length() - 4)
                                + "\n" + "操作员工：" + details.getOperator().getNickname()
                                + "\n" + "员工入职时间：" + details.getOperator().getJoinTime();
                        mPrinter.printString(s, "GBK");

                        s = "------------------------------------------------";
                        mPrinter.printString(s, "GBK");

                        //商品信息
                        StringBuilder sb = new StringBuilder();
                        sb.append(printFourData80("商品名称", "数量", "单价", "金额")).append("\n");

                        mPrinter.setFontStyle(1);
                        for (int i = 0; i < details.getOrderdetail().getPros().size(); i++) {
                            int n = details.getOrderdetail().getPros().get(i).getProCount();
                            sb.append(printFourData80(details.getOrderdetail().getPros().get(i).getProName(), String.valueOf(details.getOrderdetail().getPros().get(i).getCups().size()), String.valueOf(details.getOrderdetail().getPros().get(i).getPrice()),
                                    String.valueOf(details.getOrderdetail().getPros().get(i).getCups().size() * details.getOrderdetail().getPros().get(i).getPrice()))).append("\n");
                            if (!details.getOrderdetail().getPros().get(i).getMats().isEmpty()) {
                                for (OrderDetails.OrderdetailBean.ProsBean.MatsBean bean : details.getOrderdetail().getPros().get(i).getMats())
                                    sb.append(printFourData80("-" + bean.getMatName(), String.valueOf(n), String.valueOf(bean.getIngredientPrice()), String.valueOf(n * bean.getIngredientPrice()))).append("\n");
                            }
                        }
                        sb.append("\n").append(printTwoData80("订单总金额：", String.valueOf(details.getOrderdetail().getTotalPrice())));
                        mPrinter.printString(sb.toString(), "GBK");

                        //公司
                        mPrinter.setAlignMode(1);
                        s = "\n技术支持：火炬木科技";
                        mPrinter.printString(s, "GBK");
                        mPrinter.feedToStartPos();

                        mPrinter.cutPaper(66, 0);
                    } catch (Exception e) {
                        Log.e(TAG, "printPayBack: " + e.getMessage());
                    } finally {
                        if (PrinterAPI.SUCCESS == mPrinter.disconnect()) {
                            io.closeDevice();
                            mPrinter = null;
                            io = null;
                            Log.d(TAG, "disconnectPrinter: finish");
                        }
                    }
                }
            }
        });
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
    public static void printImage(PrinterAPI mPrinter, Bitmap bitmap) {
        set(CLEAR_TEMP);
        mPrinter.setAlignMode(1);
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
    private static void cutPaper(PrinterAPI mPrinter) {
        if (PrinterAPI.SUCCESS == mPrinter.cutPaper(66, 0)) {
            Log.d(TAG, "cutPaper: finish");
        }
    }

    public static void getStatus() {
        mPrinter.getStatus();
    }

    private static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
//            try {
//                Thread.sleep(50L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
//            try {
//                Thread.sleep(50L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return bitmap;
        } else {
            return null;
        }
    }

}
