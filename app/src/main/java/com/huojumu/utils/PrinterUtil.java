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
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.huojumu.R;
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
        int marginBetweenLeftAndMiddle = LEFT_LENGTH_80 - leftTextLength - middleTextLength / 2;

        for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
            sb.append(" ");
        }
        sb.append(middleText);
        //计算中间文字和第三文字的空格长度
        int marginBetweenThreeAndFour = 12 - middleTextLength / 2 - threeTextLength / 2;

        for (int i = 0; i < marginBetweenThreeAndFour; i++) {
            sb.append(" ");
        }

        // 计算第四文字和第三文字的空格长度
        int marginBetweenMiddleAndRight = 12 - threeTextLength / 2 - rightTextLength;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length() - 1, sb.length()).append(rightText);
        return sb.toString();
    }


    //    private static Thread thread;
    //开钱箱
    public static void OpenMoneyBox() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = {0x1B, 0x70, 0x0, 0x3C, (byte) 0xFF};
                mPrinter.writeIO(bytes, 0, bytes.length - 1, 1000);
            }
        }).start();
    }

    public static String toJson(Object orderInfo) {
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

    public static UsbDevice getUsbDeviceFromName(Context context, String usbName) {
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> usbDeviceList = usbManager.getDeviceList();
        return usbDeviceList.get(usbName);
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

    public static String getTabTime() {
        simpleDateFormat = new SimpleDateFormat("MMdd", Locale.CHINA);
        simpleDateFormat.format(date);
        return simpleDateFormat.format(date);
    }

    public static String getTabHour() {
        simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
        simpleDateFormat.format(date);
        return simpleDateFormat.format(date);
    }

    public static String getPrintDate() {
        simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA);
        simpleDateFormat.format(date);
        return simpleDateFormat.format(date);
    }

    public static String getCNDate() {
        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        simpleDateFormat.format(date);
        return simpleDateFormat.format(date);
    }

    public static String getOrderID() {
        simpleDateFormat = new SimpleDateFormat("yyMMdd", Locale.CHINA);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 打印文本58mm样式// 32 字节
     */
    public static void printString58(final List<Production> pList, final double totalPrice, final int totalNumber, final String orderNo, final String orderId, final String time) {
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
            for (Production p : pList) {
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
        int marginBetweenLeftAndMiddle = LEFT_LENGTH_80 - leftTextLength - middleTextLength / 2;

        for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
            sb.append(" ");
        }
        sb.append(middleText);

        //计算中间文字和第三文字的空格长度
        int marginBetweenThreeAndFour = (LEFT_LENGTH_80 - middleTextLength / 2 - threeTextLength - rightTextLength) / 2;

        for (int i = 0; i < marginBetweenThreeAndFour; i++) {
            sb.append(" ");
        }
        sb.append(three);

        // 计算第四文字和第三文字的空格长度
        for (int i = 0; i < marginBetweenThreeAndFour; i++) {
            sb.append(" ");
        }
//        sb.append(rightText);

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length() - 1, sb.length()).append(rightText);
        return sb.toString();
    }

    /**
     * 打印文本80mm小票样式 48 字节
     */
    public static void printString80(Context c, final List<Production> pList, final String orderNo, final String name, final String totalMoney,
                                     final String earn, final String cost, final String charge, final String cut) {
        try {
//            set(DOUBLE_HEIGHT_WIDTH);
            //居左
            mPrinter.setAlignMode(0);
            //字体变大
            mPrinter.setCharSize(1, 1);
            //订单流水号
            String s = orderNo.substring(orderNo.length() - 4);
            mPrinter.printString(s, "GBK");
            mPrinter.printFeed();

            //实线
            printImage(drawable2Bitmap(c.getResources().getDrawable(R.drawable.line1)));
            mPrinter.setCharSize(0, 0);

            //员工名 + 时间
            s = "\n收银员：" + name + "\n" + "时间：" + PrinterUtil.getPrintDate();
            mPrinter.printString(s, "GBK");

            //间隔大的虚线
            printImage(drawable2Bitmap(c.getResources().getDrawable(R.drawable.line2)));

            //商品信息
            StringBuilder sb = new StringBuilder();
            sb.append(printFourData80("商品名称", "数量", "单价", "金额")).append("\n");
//            mPrinter.printFeed();
            for (int i = 0; i < pList.size(); i++) {
                sb.append(printFourData80(pList.get(i).getProName(), String.valueOf(pList.get(i).getNumber()), String.valueOf(pList.get(i).getPrice()), String.valueOf(pList.get(i).getNumber() * pList.get(i).getPrice()))).append("\n");
                sb.append(printFourData80(pList.get(i).getAddon(), "", "", ""));
            }
            mPrinter.printString(sb.toString(), "GBK");
            //间隔小的虚线
            printImage(drawable2Bitmap(c.getResources().getDrawable(R.drawable.line3)));

            //交易金额明细
            s = "\n" + printTwoData80("消费金额", String.format("%.1s",totalMoney))
                    + "\n" + printTwoData80("应收金额", String.format("%.1s",earn))
                    + "\n" + printTwoData80("客户实付", cost)
                    + "\n" + printTwoData80("优    惠", cut)
                    + "\n" + printTwoData80("找    零", charge) + "\n";
            mPrinter.printString(s, "GBK");

            //虚实线
            printImage(drawable2Bitmap(c.getResources().getDrawable(R.drawable.line3)));
            printImage(drawable2Bitmap(c.getResources().getDrawable(R.drawable.line1)));

            //居中
            mPrinter.setAlignMode(1);

            //logo图片9
            printImage(drawable2Bitmap(c.getResources().getDrawable(R.drawable.logo)));
            mPrinter.setCharSize(1, 1);
            //店铺名
            s = SpUtil.getString(Constant.STORE_NAME) + "\n";
            mPrinter.printString(s, "GBK");

            mPrinter.setAlignMode(0);
            mPrinter.setCharSize(0, 0);
            //企业文化描述
            s = "7港9欢迎您的到来。我们再次从香港出发，希望搜集到各地的特色食品，港印全国。能7(去)香港(港)的(9)九龙喝一杯正宗的港饮是我们对每一位顾客的愿景。几百年来，香港作为东方接触世界的窗口，找寻并创造了一款款独具特色又流传世界的高品饮品。我们在全国超过十年的专业服务与坚持，与97回归共享繁华，秉承独到的调制方法，期许再一次与亲爱的你能擦出下一个十年火花。\n";
            mPrinter.printString(s, "GBK");
            mPrinter.setAlignMode(1);

            //品牌二维码
            printImage(drawable2Bitmap(c.getResources().getDrawable(R.drawable.qr_code)));

            //投诉、加盟热线
            s = "\n投诉、加盟热线：010-62655878";
            mPrinter.printString(s, "GBK");

            //公司
            s = "\n技术支持 火炬木科技";
            mPrinter.printString(s, "GBK");

            cutPaper();
        } catch (Exception e) {
            Log.d(TAG, "printDaily: error");
            ToastUtils.showLong("打印机连接出错");
        }
    }

    public static void printDaily(int type, String total, String mobilePay, String cash, int orderNum, String workerName) {
        try {
            String t = type == 1 ? "交班" : "日结";
            String s = t + "\n日期：\n";
            mPrinter.setCharSize(1, 1);
            mPrinter.printString(s, "GBK");

            mPrinter.setCharSize(0, 0);
            //居左
            mPrinter.setAlignMode(0);
            s = "本次" + t + "时间：" + getDate() + "\n" + "上次" + t + "时间" + SpUtil.getString("dailyTime") + "\n";
            mPrinter.printString(s, "GBK");

            mPrinter.setCharSize(1, 1);
            s = "交款信息：\n";
            mPrinter.printString(s, "GBK");

            mPrinter.setCharSize(0, 0);
            s = "总营收金额：" + total + "\n"
                    + "总虚收金额：" + mobilePay + "\n"
                    + "总实收金额：" + cash + "\n"
                    + "总  单  数：" + orderNum + "\n"
                    + t + "人员：" + workerName + "\n";
            mPrinter.printString(s, "GBK");

            Log.e(TAG, "printDaily: " + s);
            mPrinter.printFeed();
            mPrinter.setAlignMode(1);
            //公司
            s = "\n技术支持 火炬木科技";
            mPrinter.printString(s, "GBK");
            //保存本次时间
            SpUtil.save("dailyTime", getDate());
            cutPaper();
        } catch (Exception e) {
            Log.d(TAG, "printDaily: ");
            ToastUtils.showLong("打印机连接出错");
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

    private static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
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
            return bitmap;
        } else {
            return null;
        }
    }

}
