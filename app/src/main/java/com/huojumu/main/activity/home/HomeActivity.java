package com.huojumu.main.activity.home;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.huojumu.MyApplication;
import com.huojumu.R;
import com.huojumu.adapter.HomeProductAdapter;
import com.huojumu.adapter.HomeSelectedAdapter;
import com.huojumu.adapter.HomeTypeAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.dialog.SingleProAddonDialog;
import com.huojumu.main.activity.login.LoginActivity;
import com.huojumu.main.activity.work.DailyTakeOverActivity;
import com.huojumu.main.dialogs.CashPayDialog;
import com.huojumu.main.dialogs.CertainDialog;
import com.huojumu.main.dialogs.DialogInterface;
import com.huojumu.main.dialogs.MoreFunctionDialog;
import com.huojumu.main.dialogs.QuickPayDialog;
import com.huojumu.main.dialogs.SingleProCallback;
import com.huojumu.main.dialogs.UsbDeviceList;
import com.huojumu.model.BaseBean;
import com.huojumu.model.MatsBean;
import com.huojumu.model.OrderBack;
import com.huojumu.model.OrderInfo;
import com.huojumu.model.Production;
import com.huojumu.model.Products;
import com.huojumu.model.SmallType;
import com.huojumu.model.TaskBean;
import com.huojumu.model.VipListBean;
import com.huojumu.utils.Constant;
import com.huojumu.utils.DeviceConnFactoryManager;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PowerUtil;
import com.huojumu.utils.PrinterCommand;
import com.huojumu.utils.PrinterUtil;
import com.huojumu.utils.QrUtil;
import com.huojumu.utils.SocketBack;
import com.huojumu.utils.SpUtil;
import com.huojumu.utils.ThreadFactoryBuilder;
import com.huojumu.utils.ThreadPool;
import com.huojumu.utils.UsbUtil;
import com.tools.command.EscCommand;
import com.tools.command.LabelCommand;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_ATTACHED;
import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;
import static com.huojumu.utils.Constant.ACTION_USB_PERMISSION;
import static com.huojumu.utils.DeviceConnFactoryManager.ACTION_QUERY_PRINTER_STATE;
import static com.huojumu.utils.DeviceConnFactoryManager.CONN_STATE_FAILED;


public class HomeActivity extends BaseActivity implements DialogInterface, SocketBack,
        SingleProCallback, MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {

    //下单列表
    @BindView(R.id.recyclerView)
    RecyclerView left;
    //分类列表
    @BindView(R.id.recyclerView2)
    RecyclerView rTop;
    //商品列表
    @BindView(R.id.recyclerView3)
    RecyclerView rBottom;
    //总数量
    @BindView(R.id.total_number)
    TextView total_number;
    //优惠总额
    @BindView(R.id.cut_number)
    TextView cut_number;
    //订单总额
    @BindView(R.id.total_price)
    TextView total_price;
    //有无挂单
    @BindView(R.id.iv_has_gua_dan)
    ImageView hasGua;

    private HomeSelectedAdapter selectedAdapter;//所选
    private HomeTypeAdapter typeAdapter;//类别
    private HomeProductAdapter productAdapter;//商品

    private List<Production> tempProduces;//商品列表
    private List<VipListBean> activeBeanList;//活动列表
    private SparseArray<List<Production>> map = new SparseArray<>();//分类切换
    private ArrayList<Production> productions = new ArrayList<>();//选择的奶茶
    private ArrayList<Production> printProducts = new ArrayList<>();//标签打印的产品
    private double totalPrice = 0, totalCut;//订单总价

    private List<Production> gTemp = new ArrayList<>();//挂单
    private boolean hasHoldOn = false;//是否已有挂单

    private QuickPayDialog quickPayDialog;//快捷支付弹窗
    private CashPayDialog cashPayDialog;//现金支付弹窗
    private SingleProAddonDialog addonDialog;//单品设置弹窗
    private SingleProAddonDialog addonDialog2;//单品修改弹窗
    private MoreFunctionDialog functionDialog;//功能弹窗
    private CertainDialog certainDialog;//关机确认弹窗

    //订单数据
    private OrderInfo orderInfo;
    //    private Handler handler = new Handler();
    //是否修改
    private boolean ok = false;
    //流水号
    private String orderNo;

    //是否是现金支付
    boolean isCash = false;
    //
    private String isRecommend = "0";

    private Handler handler = new Handler();

    private UsbManager usbManager;
    private int id = 0;
    UsbDeviceList usbDeviceList;
    /**
     * 连接状态断开
     */
    private static final int CONN_STATE_DISCONN = 0x007;
    /**
     * 使用打印机指令错误
     */
    private static final int PRINTER_COMMAND_ERROR = 0x008;

    int count = 0;
    String name, taste;
    double price;
    private ThreadPool threadPool;

    String TAG = "home";
    private OrderBack orderBack;
    private double change;

    @Override
    protected int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        MyApplication.getSocketTool().sendHeart();
        EventBus.getDefault().register(this);

        //正常进入系统主页，默认值
        SpUtil.save("Daily_success", false);
        threadPool = ThreadPool.getInstantiation();
        //链接标签机
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        getUsb(UsbUtil.getUsbDeviceList(this));

        //左侧点单列表
        selectedAdapter = new HomeSelectedAdapter(productions);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        left.setLayoutManager(linearLayoutManager);
        DividerItemDecoration d = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        d.setDrawable(getResources().getDrawable(R.drawable.divider_n));
        left.addItemDecoration(d);
        selectedAdapter.enableSwipeItem();
        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(selectedAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(left);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        selectedAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                productions.remove(pos);
                selectedAdapter.notifyItemRemoved(pos);
                checkPriceForDisplay();
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
        selectedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //显示单品附加信息
                ok = true;
                addonDialog2 = new SingleProAddonDialog(HomeActivity.this, HomeActivity.this, productions.get(position), productions.get(position).getProId(), true, position);
                addonDialog2.show();
            }
        });
        left.setAdapter(selectedAdapter);

        //分类列表
        typeAdapter = new HomeTypeAdapter(null);
        GridLayoutManager grid1 = new GridLayoutManager(this, 6);
        rTop.setLayoutManager(grid1);
        rTop.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position != 0) {
                    if (map.get(position) == null) {
                        List<Production> temp = new ArrayList<>();
                        for (Production p : tempProduces) {
                            if (typeAdapter.getData().get(position).getTypeNo().equals(p.getProType())) {
                                temp.add(p);
                            }
                        }
                        map.put(position, temp);
                    }
                    productAdapter.setNewData(map.get(position));
                } else {
                    productAdapter.setNewData(tempProduces);
                }
                for (int i = 0; i < typeAdapter.getData().size(); i++) {
                    typeAdapter.getData().get(i).setSelected(position == i);
                }
                typeAdapter.notifyDataSetChanged();
            }
        });

        //商品列表
        productAdapter = new HomeProductAdapter(null);
        GridLayoutManager grid2 = new GridLayoutManager(this, 5);
        rBottom.setLayoutManager(grid2);
        rBottom.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点菜逻辑
                if ("1".equals(tempProduces.get(position).getIsSaled())) {
                    ToastUtils.showLong("该商品已售罄");
                    return;
                }
                addonDialog = new SingleProAddonDialog(HomeActivity.this, HomeActivity.this, tempProduces.get(position), tempProduces.get(position).getProId(), false, position);
                addonDialog.show();
            }
        });

        ld = new LoadingDialog(this);
        ld.setLoadingText("支付中")
                .setSuccessText("支付成功")//显示加载成功时的文字
                .setFailedText("支付失败");

        if (!SpUtil.getBoolean("Daily_success")) {
            //非正常日结情况
            daily();
        }
    }

    @OnClick(R.id.button5)
    void refresh() {
        getTypeList();
        getProList(isRecommend);
        getActiveInfo();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level >= ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            //低内存情况下释放资源
            quickPayDialog = null;
            cashPayDialog = null;
            functionDialog = null;
        }
    }

    @Override
    protected void initData() {

        getTypeList();
        getProList(isRecommend);
        getActiveInfo();

    }

    private void getTypeList() {
        //小类
        NetTool.getSmallType(SpUtil.getInt(Constant.STORE_ID), SpUtil.getInt(Constant.ENT_ID),
                SpUtil.getInt(Constant.PINPAI_ID), new GsonResponseHandler<BaseBean<List<SmallType>>>() {
                    @Override
                    public void onSuccess(int statusCode, BaseBean<List<SmallType>> response) {
                        List<SmallType> list = new ArrayList<>();
                        list.add(new SmallType("全部", 0, 0, true));
                        list.addAll(response.getData());
                        typeAdapter.setNewData(list);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                    }
                });
    }

    //商品列表
    private void getProList(String isRecommend) {
        NetTool.getStoreProduces(SpUtil.getInt(Constant.STORE_ID), SpUtil.getInt(Constant.ENT_ID), SpUtil.getInt(Constant.PINPAI_ID), isRecommend,
                new GsonResponseHandler<BaseBean<Products>>() {
                    @Override
                    public void onSuccess(int statusCode, BaseBean<Products> response) {
                        tempProduces = response.getData().getProducts();
                        productAdapter.setNewData(response.getData().getProducts());
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        ToastUtils.showLong("网络出错，点击刷新");
                    }
                });
    }

    //活动列表
    private void getActiveInfo() {
        NetTool.getActiveInfo(SpUtil.getInt(Constant.STORE_ID), SpUtil.getInt(Constant.ENT_ID), SpUtil.getInt(Constant.PINPAI_ID), new GsonResponseHandler<BaseBean<List<VipListBean>>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<List<VipListBean>> response) {
                activeBeanList = response.getData();
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }

    @OnClick(R.id.button4)
    void getRecommend() {
        isRecommend = isRecommend.equals("") ? "1" : "";
        getProList(isRecommend);
    }

    @Override
    public void callback(String s) {

    }

//    @Override
//    public void sendMsg(String s) {
//        if ("0".equals(s)) {
//            //线上付款完成
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    productions.clear();
//                }
//            }, 2000);
//            selectedAdapter.setNewData(productions);
//        }
//    }

    private void checkPriceForDisplay() {
        double totalPrice = 0.0, totalCut = 0.0;
        int totalCount = 0;
        for (Production p : productions) {
            totalCount += p.getNumber();
            if (activeBeanList == null || activeBeanList.isEmpty()) {
                double c = 0;
                if (!p.getMats().isEmpty()) {
                    for (MatsBean m : p.getMats()) {
                        c += m.getIngredientPrice();
                    }
                }
                totalPrice += p.getOrigionPrice() * p.getNumber() + c * p.getNumber();
                totalCut = 0;

            } else {
                if (p.getIsBargain() != null && p.getIsBargain().equals("1")) {
                    double c = 0;
                    if (!p.getMats().isEmpty()) {
                        for (MatsBean m : p.getMats()) {
                            c += m.getIngredientPrice();
                        }
                    }
                    totalPrice += p.getPrice() * p.getNumber() + c * p.getNumber();
                    totalCut += (p.getOrigionPrice() - p.getPrice()) * p.getNumber();

                } else if (p.getIsPresented() != null && p.getIsPresented().equals("1")) {
                    double c = 0;
                    if (!p.getMats().isEmpty()) {
                        for (MatsBean m : p.getMats()) {
                            c += m.getIngredientPrice();
                        }
                    }
                    totalPrice += p.getOrigionPrice() * (p.getNumber() > 1 ? p.getNumber() - 1 : 1) + c * (p.getNumber() > 1 ? p.getNumber() - 1 : 1);
                    totalCut += p.getOrigionPrice() * (p.getNumber() > 1 ? 1 : 0);

                }
            }
        }
        total_number.setText(String.format("数量：%s 份", totalCount));
        total_price.setText(String.format("总价：%s 元", totalPrice));
        cut_number.setText(String.format("优惠：%s 元", totalCut));
        //副屏刷新
        if (engine != null) {
            engine.refresh(productions);
            engine.setPrice(totalPrice, totalCut);
        }
        this.totalPrice = totalPrice;
        this.totalCut = totalCut;

    }

    List<OrderInfo.DataBean> dataBeans = new ArrayList<>();
    int n = 0;

    /**
     * 单品详情dialog配置回调
     */
    @Override
    public void onSingleCallBack(int proId, int number, Production productsBean, OrderInfo.DataBean dataBean, int position) {
        //加入已选清单中
        if (ok) {
            productions.get(position).setNumber(number);
            ok = false;
        } else {
            boolean isAdd = false;
            n = 0;
            if (!productions.isEmpty())
                for (int i = 0; i < productions.size(); i++) {
                    if (productions.get(i).getProId() == proId) {
                        isAdd = true;
                        n = productions.get(i).getNumber() + number;
                        productions.get(i).setNumber(n);
                        dataBean.setNum(n);
                        productsBean.setNumber(n);
                        break;
                    }
                }
            if (!isAdd) {
                dataBean.setNum(number);
                productsBean.setNumber(number);
                productions.add(productsBean);
            }
        }

        addonDialog = null;
        addonDialog2 = null;
        //刷新选择列表数据
        selectedAdapter.setNewData(productions);
        //计算副频金额
        checkPriceForDisplay();

        orderInfo = new OrderInfo();
        orderInfo.setOrderID(UUID.randomUUID().toString().replace("-", ""));
        orderInfo.setShopID(SpUtil.getInt(Constant.STORE_ID));
        orderInfo.setCreateTime(PrinterUtil.getDate());
        orderInfo.setEnterpriseID(SpUtil.getInt(Constant.ENT_ID));
        orderInfo.setPinpaiID(SpUtil.getInt(Constant.PINPAI_ID));
        orderInfo.setQuanIds(new ArrayList<Integer>());
        orderInfo.setDiscountsType(SpUtil.getString(Constant.ENT_DIS));

        dataBean.setProId(productsBean.getProId());
        dataBean.setProType(productsBean.getProType());
        dataBeans.add(dataBean);
        orderInfo.setData(dataBeans);
        orderInfo.setOrdSource("3");
    }

    /**
     * 挂单
     */
    @OnClick(R.id.button1)
    void OrderHoldOn() {
        //无选择数据 且 无挂单数据，按钮无响应
        if (productions.isEmpty() && gTemp.isEmpty()) {
            return;
        }
        if (hasHoldOn) {
            Toast.makeText(this, "已显示挂单！", Toast.LENGTH_LONG).show();
            productions.addAll(gTemp);
            gTemp.clear();
            selectedAdapter.setNewData(productions);//显示挂单数据
            hasHoldOn = false;
            hasGua.setVisibility(View.INVISIBLE);
        } else {
            //将当前数据放入挂单list中
            gTemp.addAll(productions);
            DeleteAll();
            hasHoldOn = true;
            hasGua.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 删除当前单子列表
     */
    @OnClick(R.id.button2)
    void DeleteAll() {
        clear();
    }

    /**
     * 快捷支付弹窗
     */
    @OnClick(R.id.btn_home_quick_pay)
    void QuickPay() {
        if (productions.isEmpty()) {
            ToastUtils.showLong("还未点餐！");
            return;
        }
        if (quickPayDialog == null) {
            quickPayDialog = new QuickPayDialog(this, this);
        }
        quickPayDialog.show();
    }

    /**
     * 功能
     */
    @OnClick(R.id.btn_home_more_function)
    void More() {
        if (functionDialog == null) {
            functionDialog = new MoreFunctionDialog(this);
        }
        functionDialog.show();
    }

    /**
     * 关机
     */
    @OnClick(R.id.btn_home_shutdown)
    void ShutDown() {
        if (certainDialog == null) {
            certainDialog = new CertainDialog(this, this, "注意！", "确认要关机吗？");
            certainDialog.show();
        }
    }

    /**
     * 交班
     */
    @OnClick(R.id.btn_home_hand_over)
    void takeover() {
        Intent intent = new Intent(HomeActivity.this, DailyTakeOverActivity.class);
        intent.putExtra("type", 1);
        startActivityForResult(intent, Constant.WORK_BACK_OVER);
    }

    /**
     * 日结
     */
    @OnClick(R.id.btn_home_daily)
    void Daily() {
        Intent intent = new Intent(HomeActivity.this, DailyTakeOverActivity.class);
        intent.putExtra("type", 2);
        startActivityForResult(intent, Constant.WORK_BACK_DAILY);
    }

//    /**
//     * 结账
//     */
//    @OnClick(R.id.btn_home_check_out)
//    void goCheckOut() {
//        if (productions.isEmpty()) {
//            ToastUtils.showLong("还未点餐！");
//            return;
//        }
////        Intent i = new Intent(HomeActivity.this, PaymentActivity.class);
////        if (orderInfo != null) {//
////            i.putExtra("orderJson", PrinterUtil.toJson(orderInfo));
////        }
////        i.putExtra("orderTotal", totalPrice);
////        startActivity(i);
//        if (quickPayDialog == null) {
//            quickPayDialog = new QuickPayDialog(this, this);
//        }
//        quickPayDialog.show();
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.WORK_BACK_OVER:
                    Log.e(TAG, "onActivityResult: over");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                            finish();
                        }
                    }, 10 * 1000);
                    break;
                case Constant.WORK_BACK_DAILY:
                    Log.e(TAG, "onActivityResult: daily");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            PowerUtil.shutdown();
                        }
                    }, 30 * 1000);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 结账 dialog 按钮回调
     */
    @Override
    public void OnDialogOkClick(final int type, final double earn, final double cost, final double charge, String name) {
        change = charge;
        switch (name) {
            case "QuickPayDialog":
                quickPayDialog.dismiss();
                if (1 == type) {
                    if (cashPayDialog == null) {
                        cashPayDialog = new CashPayDialog(this, totalPrice, this);
                    }
                    cashPayDialog.show();
                } else {
                    orderInfo.setPayType(type == 2 ? "020" : "010");

                    //线上支付
                    NetTool.postOrder(PrinterUtil.toJson(orderInfo), new GsonResponseHandler<BaseBean<OrderBack>>() {
                        @Override
                        public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                            orderBack = response.getData();
                            orderNo = response.getData().getOrderNo();
                            if (type == 2) {
                                engine.getAliIV().setImageBitmap(QrUtil.createQRCodeWithLogo(HomeActivity.this, response.getData().getAliPayQrcode(), BitmapFactory.decodeResource(getResources(), R.drawable.zhifubao_normal)));
                            } else if (type == 3) {
                                engine.getWxIV().setImageBitmap(QrUtil.createQRCodeWithLogo(HomeActivity.this, response.getData().getWxPayQrcode(), BitmapFactory.decodeResource(getResources(), R.drawable.weixin_normal)));
                            }

                            ld.show();
                            payOutTime();
                        }

                        @Override
                        public void onFailure(int statusCode, String error_msg) {
                            ToastUtils.showLong(error_msg);
                        }
                    });
                }
                break;
            //现金支付回调
            case "CashPayDialog":
                //弹钱箱，打印小票
                isCash = true;
                orderInfo.setPayType("900");
                cashPayDialog.cancel();
                cashPayDialog = null;
                NetTool.postOrder(PrinterUtil.toJson(orderInfo), new GsonResponseHandler<BaseBean<OrderBack>>() {
                    @Override
                    public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                        orderBack = response.getData();
                        orderNo = response.getData().getOrderNo();
                        PrintOrder(response.getData(), charge < 0 ? 0 : charge);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                clear();
                            }
                        }, 1500);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        ToastUtils.showLong(error_msg);
                    }
                });
                break;
            case "CertainDialog":
                //关机确认
                certainDialog.cancel();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PowerUtil.shutdown();
                    }
                }, 2000);
                break;
        }
    }

    @Override
    public void OnUsbCallBack(String name) {
        closeport();
        Log.e(TAG, "OnUsbCallBack: ");
        getUsb(name);
    }

    private void getUsb(String name) {
        Log.e(TAG, "getUsb: ");
        //获取USB设备名
        //通过USB设备名找到USB设备
        UsbDevice usbDevice = PrinterUtil.getUsbDeviceFromName(HomeActivity.this, name);
        //判断USB设备是否有权限
        if (usbDevice != null)
            if (usbManager.hasPermission(usbDevice)) {
                Log.e(TAG, "getUsb: 1");
                usbConn(usbDevice);
            } else {//请求权限
                Log.e(TAG, "getUsb: 2");
                PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
                usbManager.requestPermission(usbDevice, mPermissionIntent);
            }
    }

    /**
     * 支付超时
     */
    private void payOutTime() {
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                ld.loadFailed();
                clear();
            }
        };
        timer.start();
    }


    /**
     * 打印订单小票
     */
    private void PrintOrder(final OrderBack orderBack, final double charge) {
        threadPool.addTask(new Runnable() {
            @Override
            public void run() {
                if (isCash) {
                    isCash = false;
                    PrinterUtil.OpenMoneyBox();
                }
                PrinterUtil.printString80(HomeActivity.this, productions, orderBack.getOrderNo(), SpUtil.getString(Constant.WORKER_NAME), orderBack.getTotalPrice(), orderBack.getTotalPrice(), "" + (Double.parseDouble(orderBack.getTotalPrice()) + charge), charge + "", totalCut + "");
            }
        });

        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null ||
                !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
            ToastUtils.showLong("未连接标签打印机");
            return;
        }

        printcount = 0;
        continuityprint = true;
        printProducts.clear();
        for (int i = 0; i < productions.size(); i++) {
            name = productions.get(i).getProName();
            taste = productions.get(i).getTasteStr();
            price = productions.get(i).getPrice();
            count = productions.get(i).getNumber();
            for (int j = 0; j < productions.get(i).getNumber(); j++) {
                Production p = new Production();
                p.setProName(name);
                p.setTasteStr(taste);
                p.setPrice(price);
                p.setNumber(count);
                printProducts.add(p);
                printcount++;
            }
        }

        Log.e(TAG, "PrintOrder: " + printcount);
        printLabel();
    }

    private void printLabel() {
        threadPool.addTask(new Runnable() {
            @Override
            public void run() {
                if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] != null
                        && DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
                    ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder("MainActivity_sendContinuity_Timer");
                    ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, threadFactoryBuilder);
                    scheduledExecutorService.schedule(threadFactoryBuilder.newThread(new Runnable() {
                        @Override
                        public void run() {
                            printcount--;
                            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getCurrentPrinterCommand() == PrinterCommand.TSC) {
                                //标签模式可直接使用LabelCommand.addPrint()方法进行打印
                                Log.e(TAG, "printLabel run: " + printcount);
                                sendLabel(printProducts.get(printcount).getProName(), printProducts.get(printcount).getTasteStr(), printProducts.get(printcount).getPrice(), printcount, printProducts.size());
                            }
                        }
                    }), 1000, TimeUnit.MILLISECONDS);
                }
            }
        });
    }

    /**
     * 重置所有数据
     */
    private void clear() {
        //清空当前选择list
        productions.clear();
        //副频置空
        engine.clear();
        //订单置空
        dataBeans.clear();
        //左下角计算置空
        total_number.setText("数量：");
        total_price.setText("总价：");
        cut_number.setText("优惠：");
        //订单置空
        orderInfo = null;
        //左侧置空
        selectedAdapter.setNewData(null);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetPayBack(TaskBean taskBean) {
        //socket支付回调
        if (taskBean.getData().getState().equals("01")) {//用户支付完成
            ld.loadSuccess();
            PrintOrder(orderBack, change);
        } else {
            ld.loadFailed();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clear();
            }
        }, 1500);
    }

    private void usbConn(UsbDevice usbDevice) {
        Log.e(TAG, "usbConn: ");
        new DeviceConnFactoryManager.Build()
                .setId(id)
                .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.USB)
                .setUsbDevice(usbDevice)
                .setContext(this)
                .build();
        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].openPort();
    }

    public void btnUsbConn(View view) {
        if (usbDeviceList == null) {
            usbDeviceList = new UsbDeviceList(this, this);
        }
        if (!usbDeviceList.isShowing()) {
            usbDeviceList.show();
        }
    }

    private void closeport() {
        Log.e(TAG, "closeport: ");
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] != null && DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].mPort != null) {
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].reader.cancel();
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].mPort.closePort();
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].mPort = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        DeviceConnFactoryManager.closeAllPort();
        if (threadPool != null) {
            threadPool.stopThreadPool();
        }
    }

    /**
     * 发送标签
     */
    void sendLabel(String pName, String pContent, double price, int i, int number) {
        i = i + 1;
        Log.e(TAG, "sendLabel: ");
        LabelCommand tsc = new LabelCommand();
        // 设置标签尺寸，按照实际尺寸设置
        tsc.addSize(45, 30);
        // 设置标签间隙，按照实际尺寸设置，如果为无间隙纸则设置为0
        tsc.addGap(2);
        // 设置打印方向
        tsc.addDirection(LabelCommand.DIRECTION.FORWARD, LabelCommand.MIRROR.NORMAL);
        // 开启带Response的打印，用于连续打印
        tsc.addQueryPrinterStatus(LabelCommand.RESPONSE_MODE.ON);
        // 设置原点坐标
        tsc.addReference(0, 0);
        // 撕纸模式开启
        tsc.addTear(EscCommand.ENABLE.ON);
        // 清除打印缓冲区
        tsc.addCls();
        int x = (300 - pName.length() * 30) / 2;
        // 绘制简体中文
        Log.e(TAG, "PrintOrder: print 1");
        tsc.addText(x, 13, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                SpUtil.getString(Constant.STORE_NAME) + "\n");
        Log.e(TAG, "PrintOrder: print 2");
        tsc.addText(0, 43, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                pName + "\n");
        Log.e(TAG, "PrintOrder: print 3");
        tsc.addText(0, 78, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                pContent + "\n");
        Log.e(TAG, "PrintOrder: print 4");
        tsc.addText(0, 110, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                "￥" + price + "    " + i + "/" + number);
        Log.e(TAG, "PrintOrder: print 5");
        tsc.addText(0, 140, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                SpUtil.getString(Constant.WORKER_NAME) + "\n");
        Log.e(TAG, "PrintOrder: print 6");
        tsc.addText(0, 170, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                PrinterUtil.getTabTime() + orderNo.substring(orderNo.length() - 4) + "-" + PrinterUtil.getTabHour() + "\n");
        Log.e(TAG, "PrintOrder: print 7");
        tsc.addText(0, 200, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                SpUtil.getString(Constant.STORE_ADDRESS));
        Log.e(TAG, "PrintOrder: print 8");
        // 绘制图片
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.logo9);
        tsc.addBitmap(200, 35, LabelCommand.BITMAP_MODE.OVERWRITE, 90, b);
        Log.e(TAG, "PrintOrder: print 9");
        // 打印标签
        tsc.addPrint(1, 1);
        // 打印标签后 蜂鸣器响

        tsc.addSound(2, 100);
        tsc.addCashdrwer(LabelCommand.FOOT.F5, 255, 255);
        Vector<Byte> datas = tsc.getCommand();
        // 发送数据
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null) {
            return;
        }
        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].sendDataImmediately(datas);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        filter.addAction(ACTION_USB_DEVICE_DETACHED);
        filter.addAction(ACTION_QUERY_PRINTER_STATE);
        filter.addAction(DeviceConnFactoryManager.ACTION_CONN_STATE);
        filter.addAction(ACTION_USB_DEVICE_ATTACHED);
        registerReceiver(receiver, filter);
    }

    private boolean continuityprint = false;
    private int printcount = 0;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_USB_PERMISSION:
                    synchronized (this) {
                        UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            if (device != null) {
                                System.out.println("permission ok for device " + device);
                                usbConn(device);
                            }
                        } else {
                            System.out.println("permission denied for device " + device);
                        }
                    }
                    break;
                //Usb连接断开、蓝牙连接断开广播
                case ACTION_USB_DEVICE_DETACHED:
                    handler.obtainMessage(CONN_STATE_DISCONN).sendToTarget();
                    break;
                case DeviceConnFactoryManager.ACTION_CONN_STATE:
                    int state = intent.getIntExtra(DeviceConnFactoryManager.STATE, -1);
                    int deviceId = intent.getIntExtra(DeviceConnFactoryManager.DEVICE_ID, -1);
                    switch (state) {
                        case DeviceConnFactoryManager.CONN_STATE_DISCONNECT:
                            if (id == deviceId) {
                                ToastUtils.showLong("标签打印机已断开");
                            }
                            break;
                        case DeviceConnFactoryManager.CONN_STATE_CONNECTING:

                            break;
                        case DeviceConnFactoryManager.CONN_STATE_CONNECTED:
                            ToastUtils.showLong("标签打印机已连接");
//                            if(DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].connMethod== DeviceConnFactoryManager.CONN_METHOD.WIFI){
//                                wificonn=true;
//                                if(keepConn==null) {
//                                    keepConn = new KeepConn();
//                                    keepConn.start();
//                                }
//                            }
                            break;
                        case CONN_STATE_FAILED:
//                            Utils.toast(MainActivity.this, getString(R.string.str_conn_fail));
                            ToastUtils.showLong("标签打印机连接失败");
                            //wificonn=false;
                            break;
                        default:
                            break;
                    }
                    break;
                case ACTION_QUERY_PRINTER_STATE:
                    Log.e(TAG, "onReceive print: 0");
                    if (printcount >= 0) {
                        if (continuityprint) {
//                            printcount++;
                            Log.e(TAG, "onReceive print: 1");
//                            Utils.toast(MainActivity.this, getString(R.string.str_continuityprinter) + " " + printcount);
                        }
                        if (printcount != 0) {
                            printLabel();
                            Log.e(TAG, "onReceive print: 2");
                        } else {
                            continuityprint = false;
                            Log.e(TAG, "onReceive print: 3");
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }


    private void daily() {
        NetTool.settlement(System.currentTimeMillis(), new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<String> response) {

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
}
