package com.huojumu.utils;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.huojumu.MyApplication;
import com.huojumu.model.MatsBean;
import com.huojumu.model.OrderDetails;
import com.huojumu.model.Production;
import com.szsicod.print.escpos.PrinterAPI;
import com.szsicod.print.io.USBAPI;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author : Jie
 * Date: 2018/5/25
 * Description: 打印机辅助类，内置打印机SDK有问题，使用stringbuidler()会出现问题
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
    private static final byte[] RESET = {0x1b, 0x40};

    /**
     * 左对齐
     */
    private static final byte[] ALIGN_LEFT = {0x1b, 0x61, 0x00};

    /**
     * 中间对齐
     */
    private static final byte[] ALIGN_CENTER = {0x1b, 0x61, 0x01};

    /**
     * 右对齐
     */
    public static final byte[] ALIGN_RIGHT = {0x1b, 0x61, 0x02};

    /**
     * 选择加粗模式
     */
    private static final byte[] BOLD = {0x1b, 0x45, 0x01};

    /**
     * 取消加粗模式
     */
    private static final byte[] BOLD_CANCEL = {0x1b, 0x45, 0x00};

    /**
     * 宽高加倍
     */
    private static final byte[] DOUBLE_HEIGHT_WIDTH = {0x1d, 0x21, 0x11};

    /**
     * 开钱箱
     */
    private static final byte[] OPEN_BOX = {0x1B, 0x70, 0x0, 0x3C, (byte) 0xFF};

    /**
     * 宽加倍
     */
    private static final byte[] DOUBLE_WIDTH = {0x1d, 0x21, 0x10};

    /**
     * 高加倍
     */
    private static final byte[] DOUBLE_HEIGHT = {0x1d, 0x21, 0x01};

    /**
     * 字体不放大
     */
    private static final byte[] NORMAL = {0x1d, 0x21, 0x00};

    /**
     * 设置默认行间距
     */
    private static final byte[] LINE_SPACING_DEFAULT = {0x1b, 0x32};

    /**
     * 清空打印缓存区
     */
    private static final byte[] CLEAR_TEMP = {0x1B, 0x40};

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
        int marginBetweenMiddleAndRight = 46 - leftTextLength - rightTextLength;

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

    private static String settSubTitle() {
        StringBuilder sb = new StringBuilder();
        String first = "商品名称";
        String second = "数量";
        String third = "单价";
        String fourth = "金额";

        sb.append(first);

        for (int i = 0; i < 14; i++) {
            sb.append(" ");//14 bytes
        }
        sb.append(second);

        //计算中间文字和第三文字的空格长度
        for (int i = 0; i < 7; i++) {
            sb.append(" ");//7 bytes
        }
        sb.append(third);
        for (int i = 0; i < 7; i++) {
            sb.append(" ");//7 bytes
        }
        sb.append(fourth);

        return sb.toString();
    }

    private static String printFourData80(String first, String second, String third, String fourth) {
        StringBuilder sb = new StringBuilder();
        int leftTextLength = getBytesLength(first);
        int middleTextLength = getBytesLength(second);
        int threeTextLength = getBytesLength(third);
        int rightTextLength = getBytesLength(fourth);

        sb.append(first);
        // 计算左侧文字和中间文字的空格长度
        int marginBetweenLeftAndMiddle = LEFT_LENGTH_80 - leftTextLength - middleTextLength / 2;
        for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
            sb.append(" ");
        }
        sb.append(second);

        //计算中间文字和第三文字的空格长度
        int marginBetweenMiddleAndThird = (RIGHT_LENGTH_80 - middleTextLength - threeTextLength) / 2;
        for (int i = 0; i < marginBetweenMiddleAndThird; i++) {
            sb.append(" ");
        }
        sb.append(third);
        // 计算第四文字和第三文字的空格长度
        int marginBetweenThreeAndFour = 12 - threeTextLength / 2 - rightTextLength;
        for (int i = 0; i < marginBetweenThreeAndFour; i++) {
            sb.append(" ");
        }
        sb.append(fourth);

        return sb.toString();
    }

    public static String toJson(Object obj) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(obj);
    }

    private static boolean isConnected = false;

    /**
     * 连接打印机
     */
    public static void connectPrinter() {
        mPrinter = PrinterAPI.getInstance();
        io = new USBAPI(MyApplication.getContext());
        if (PrinterAPI.SUCCESS == mPrinter.connect(io)) {
            isConnected = true;
            Log.e("connectPrinter", "init SUCCESS");
        } else {
            isConnected = false;
            Log.e("connectPrinter", "init failed");
        }
    }

    /**
     * @return 是否成功实例化print sdk
     */
    public static boolean isIsConnected() {
        return isConnected;
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
            mPrinter = null;
            isConnected = false;
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

    public static String getCNDate() {
        date = new Date(System.currentTimeMillis());
        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    /**
     * 设置命令
     */
    private static void set(byte[] bytes) {
        mPrinter.writeIO(bytes, 0, bytes.length, 100);
    }

    /**
     * 开钱箱
     */
    public static void OpenMoneyBox() {
        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                try {
                    if (PrinterAPI.SUCCESS == mPrinter.connect(io)) {
                        set(OPEN_BOX);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "open money box error: ");
                }
            }
        });
    }

    /**
     * @param pList        本地订单数据
     * @param prosBeanList 小程序订单数据
     * @param orderNo      订单id
     * @param name         员工姓名
     * @param totalMoney   消费金额
     * @param ssPrice      实收金额
     * @param charge       找零
     * @param cut          优惠
     * @param date         日期
     * @param type         支付方式
     */
    public static void printString80(final List<Production> pList, final List<OrderDetails.OrderdetailBean.ProsBean> prosBeanList, final String orderNo, final String name, final String totalMoney, final String money,
                                     final String ssPrice, final String charge, final String cut, final String date, final String type, final String source) {
        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                try {
                    mPrinter.set80mm();
                    mPrinter.setAlignMode(1);
                    //字体变大
                    mPrinter.setCharSize(1, 1);
                    //订单流水号
                    String s = orderNo + "\n";
                    mPrinter.printString(s);
                    mPrinter.printFeed();

                    //实线
                    mPrinter.printRasterBitmap(MyApplication.getLine3());

                    //员工名 + 时间
                    s = "收银员：" + name + "\n" + "下单时间：" + date + "\n";
                    mPrinter.setAlignMode(0);
                    mPrinter.setCharSize(0, 0);
                    mPrinter.printString(s);

                    // 间隔大的虚线
                    mPrinter.setAlignMode(1);
                    mPrinter.printRasterBitmap(MyApplication.getLine2());
                    //商品信息
                    mPrinter.setAlignMode(0);
                    String ss = settSubTitle();
                    mPrinter.printString(ss, "GBK", true);

                    if (pList != null) {
                        for (Production p : pList) {
                            int n = p.getNumber();
                            String s1 = printFourData80(p.getProName(), "x" + n, String.valueOf(p.getScalePrice()), String.valueOf(n * p.getScalePrice()));
                            mPrinter.printString(s1, "GBK", true);
                            if (p.getProNameEn() != null && !p.getProNameEn().isEmpty()) {
                                String s2 = p.getProNameEn();
                                mPrinter.printString(s2, "GBK", true);
                            }
                            if (p.getMats() != null && p.getMats().size() > 0) {
                                for (MatsBean bean : p.getMats()) {
                                    String s3 = printFourData80("--" + bean.getMatName(), "x" + n, String.valueOf(bean.getIngredientPrice()), String.valueOf(n * bean.getIngredientPrice()));
                                    mPrinter.printString(s3, "GBK", true);
                                }
                            }
                        }
                    } else if (prosBeanList != null) {
                        for (OrderDetails.OrderdetailBean.ProsBean p : prosBeanList) {
                            int n = p.getProCount();
                            String s1 = printFourData80(p.getProName(), "x" + n, String.valueOf(p.getPrice()), String.valueOf(n * p.getPrice()));
                            mPrinter.printString(s1, "GBK", true);
                            if (p.getProNameEn() != null && !p.getProNameEn().isEmpty()) {
                                String s2 = p.getProNameEn();
                                mPrinter.printString(s2, "GBK", true);
                            }
                            if (p.getMats() != null && p.getMats().size() > 0) {
                                for (OrderDetails.OrderdetailBean.ProsBean.MatsBean bean : p.getMats()) {
                                    String s3 = printFourData80("--" + bean.getMatName(), "x" + n, String.valueOf(bean.getIngredientPrice()), String.valueOf(n * bean.getIngredientPrice()));
                                    mPrinter.printString(s3, "GBK", true);
                                }
                            }
                        }
                    }

                    //间隔小的虚线
                    mPrinter.setAlignMode(1);
                    mPrinter.printRasterBitmap(MyApplication.getLine3());

                    //交易金额明细
                    mPrinter.setFontStyle(0);

                    String list = printTwoData80("订单原价", totalMoney);
                    mPrinter.printString(list, "GBK", true);

                    list = printTwoData80("优惠金额", cut);
                    mPrinter.printString(list, "GBK", true);

                    list = printTwoData80("订单现价", money);
                    mPrinter.printString(list, "GBK", true);

                    list = printTwoData80("客户实付", ssPrice);
                    mPrinter.printString(list, "GBK", true);

                    list = printTwoData80("找    零", charge);
                    mPrinter.printString(list, "GBK", true);

                    list = printTwoData80("支付方式", type);
                    mPrinter.printString(list, "GBK", true);

                    list = printTwoData80("订单来源", source);
                    mPrinter.printString(list, "GBK", true);

                    //居中
                    mPrinter.setAlignMode(1);
                    //虚实线
                    mPrinter.printRasterBitmap(MyApplication.getLine3());

                    //logo图片9
                    mPrinter.printRasterBitmap(MyApplication.getLogo());

                    //店铺名
                    mPrinter.setCharSize(1, 1);
                    mPrinter.setAlignMode(1);
                    String[] ns = SpUtil.getString(Constant.STORE_NAME).split("：");
                    s = ns[0] + "\n" + ns[1] + "\n";
                    mPrinter.printString(s, "GBK", true);

                    //企业文化描述
                    mPrinter.setCharSize(0, 0);
                    mPrinter.setAlignMode(0);
                    s = "7港9欢迎您的到来。我们再次从香港出发，希望搜集到各地的特色食品，港印全国。能7(去)香港(港)的(9)九龙喝一杯正宗的港饮是我们对每一位顾客的愿景。几百年来，香港作为东方接触世界的窗口，找寻并创造了一款款独具特色又流传世界的高品饮品。我们在全国超过十年的专业服务与坚持，与97回归共享繁华，秉承独到的调制方法，期许再一次与亲爱的你能擦出下一个十年火花。\n";
                    mPrinter.printString(s);

                    //品牌二维码
                    mPrinter.setAlignMode(1);
                    mPrinter.printRasterBitmap(MyApplication.getQrcode());
                    mPrinter.printFeed();

                    //投诉、加盟热线
                    s = "投诉、加盟热线：010-62655878";
                    mPrinter.printString(s, "GBK", true);

                    //公司
                    s = "技术支持 火炬木科技";
                    mPrinter.printString(s, "GBK", true);

                    mPrinter.cutPaper(66, 0);

                    //打开钱箱
                    set(OPEN_BOX);

                    //清空缓存命令
                    set(CLEAR_TEMP);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        });

    }

    /**
     * 打印交班、日结
     */
    public static void printDaily(final int type, final String total, final String mobilePay, final String cash,
                                  final int orderNum, final String workerName, final String lastDate) {
        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                if (PrinterAPI.SUCCESS == mPrinter.connect(io)) {
                    try {
                        mPrinter.set80mm();
                        mPrinter.setAlignMode(1);
                        mPrinter.setCharSize(1, 1);
                        String t = type == 1 ? "交班" : "日结";
                        mPrinter.printString(t, "GBK", true);
                        mPrinter.printFeed();

                        //居左
                        mPrinter.setAlignMode(0);
                        String s = t + "日期：";
                        mPrinter.printString(s, "GBK", true);

                        mPrinter.setCharSize(0, 0);
                        s = "本次" + t + "时间：" + "\n    " + getDate();
                        mPrinter.printString(s, "GBK", true);
                        s = "上次" + t + "时间：" + "\n    " + lastDate;
                        mPrinter.printString(s, "GBK", true);
                        s = t + "人员：" + workerName;
                        mPrinter.printString(s, "GBK", true);

                        mPrinter.setAlignMode(1);
                        mPrinter.printRasterBitmap(MyApplication.getLine3());
                        mPrinter.printFeed();

                        mPrinter.setAlignMode(0);
                        s = "交款信息：";
                        mPrinter.printString(s, "GBK", true);

                        mPrinter.setCharSize(0, 0);
                        s = "本次" + t + "销售总额：" + total;
                        mPrinter.printString(s, "GBK", true);
                        s = "总虚收金额：" + mobilePay;
                        mPrinter.printString(s, "GBK", true);
                        s = "总实收金额：" + cash;
                        mPrinter.printString(s, "GBK", true);
                        s = "总  单  数：" + orderNum;
                        mPrinter.printString(s, "GBK", true);

                        mPrinter.printFeed();
                        mPrinter.printFeed();

                        //公司
                        mPrinter.setAlignMode(1);
                        s = "技术支持 火炬木科技";
                        mPrinter.printString(s, "GBK", true);

                        mPrinter.printFeed();

                        mPrinter.cutPaper(66, 0);

                        //清空缓存命令
                        set(CLEAR_TEMP);
                    } catch (Exception e) {
                        ToastUtils.showLong("打印机连接出错");
                    }
                }
            }
        });

    }

    /**
     * 打印退单
     */
    public static void printPayBack(final OrderDetails details, final String date) {
        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                if (PrinterAPI.SUCCESS == mPrinter.connect(io)) {
                    try {
                        mPrinter.set80mm();
                        mPrinter.setAlignMode(1);
                        String s = "退账单";
                        mPrinter.printString(s, "GBK", true);

                        mPrinter.setAlignMode(0);

                        s = "操作人：" + SpUtil.getString(Constant.WORKER_NAME);
                        mPrinter.printString(s, "GBK", true);
                        s = "下单时间：" + details.getOrderdetail().getCreateTime();
                        mPrinter.printString(s, "GBK", true);
                        s = "退单时间：" + date;
                        mPrinter.printString(s, "GBK", true);
                        s = "原订单号：" + details.getOrderdetail().getOrdNo().substring(details.getOrderdetail().getOrdNo().length() - 4);
                        mPrinter.printString(s, "GBK", true);
                        s = "操作员工：" + SpUtil.getString(Constant.WORKER_NAME);
                        mPrinter.printString(s, "GBK", true);
                        s = "员工入职时间：" + (details.getMember() == null ? details.getOperator().getJoinTime() : details.getMember().getJoinTime());
                        mPrinter.printString(s, "GBK", true);

                        //线
                        mPrinter.setAlignMode(1);
                        mPrinter.printRasterBitmap(MyApplication.getLine3());
                        mPrinter.printFeed();

                        //商品信息
                        mPrinter.setFontStyle(1);
                        s = printFourData80("商品名称", "数量", "单价", "金额");
                        mPrinter.printString(s, "GBK", true);

                        for (int i = 0; i < details.getOrderdetail().getPros().size(); i++) {
                            int n = details.getOrderdetail().getPros().get(i).getProCount();
                            String s1 = printFourData80(details.getOrderdetail().getPros().get(i).getProName(), String.valueOf(details.getOrderdetail().getPros().get(i).getCups().size()), String.valueOf(details.getOrderdetail().getPros().get(i).getPrice()),
                                    String.valueOf(details.getOrderdetail().getPros().get(i).getCups().size() * details.getOrderdetail().getPros().get(i).getPrice()));
                            mPrinter.printString(s1, "GBK", true);
                            if (!details.getOrderdetail().getPros().get(i).getMats().isEmpty()) {
                                for (OrderDetails.OrderdetailBean.ProsBean.MatsBean bean : details.getOrderdetail().getPros().get(i).getMats()) {
                                    String s2 = printFourData80("-" + bean.getMatName(), String.valueOf(n), String.valueOf(bean.getIngredientPrice()), String.valueOf(n * bean.getIngredientPrice()));
                                    mPrinter.printString(s2, "GBK", true);
                                }
                            }
                        }

                        mPrinter.printFeed();
                        s = printTwoData80("订单总金额：", String.valueOf(details.getOrderdetail().getTotalPrice()));
                        mPrinter.printString(s, "GBK", true);
                        mPrinter.printFeed();

                        //公司
                        mPrinter.setAlignMode(1);
                        s = "技术支持：火炬木科技";
                        mPrinter.printString(s, "GBK", true);

                        mPrinter.cutPaper(66, 0);

                        //清空缓存命令
                        set(CLEAR_TEMP);
                    } catch (Exception e) {
                        Log.e(TAG, "printPayBack: " + e.getMessage());
                    }
                }
            }
        });
    }


}
