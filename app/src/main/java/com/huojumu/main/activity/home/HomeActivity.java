package com.huojumu.main.activity.home;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.huojumu.adapter.PinYinAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.listeners.onPointerMoveListener;
import com.huojumu.main.activity.dialog.SingleProAddonDialog;
import com.huojumu.main.activity.login.LoginActivity;
import com.huojumu.main.dialogs.CashPayDialog;
import com.huojumu.main.dialogs.CertainDialog;
import com.huojumu.listeners.DialogInterface;
import com.huojumu.main.dialogs.MoreFunctionDialog;
import com.huojumu.main.dialogs.QuickPayDialog;
import com.huojumu.listeners.SingleProCallback;
import com.huojumu.model.ActivesBean;
import com.huojumu.model.AdsBean;
import com.huojumu.model.BaseBean;
import com.huojumu.model.BoxPay;
import com.huojumu.model.H5TaskBean;
import com.huojumu.model.MatsBean;
import com.huojumu.model.NativeOrders;
import com.huojumu.model.NetErrorHandler;
import com.huojumu.model.NoNetPayBack;
import com.huojumu.model.OrderBack;
import com.huojumu.model.OrderDetails;
import com.huojumu.model.OrderInfo;
import com.huojumu.model.Production;
import com.huojumu.model.ScaleBean;
import com.huojumu.model.SmallType;
import com.huojumu.model.WorkInfo;
import com.huojumu.utils.Constant;
import com.huojumu.utils.DeviceConnFactoryManager;
import com.huojumu.utils.MyDividerDecoration;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PowerUtil;
import com.huojumu.utils.PrinterCommand;
import com.huojumu.utils.PrinterUtil;
import com.huojumu.utils.SingleClick;
import com.huojumu.utils.SocketTool;
import com.huojumu.utils.SpUtil;
import com.huojumu.utils.ThreadFactoryBuilder;
import com.huojumu.utils.ThreadPool;
import com.huojumu.utils.UsbUtil;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.tools.command.EscCommand;
import com.tools.command.LabelCommand;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
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


public class HomeActivity extends BaseActivity implements DialogInterface,
        SingleProCallback, onPointerMoveListener {

    String TAG = HomeActivity.class.getSimpleName();

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
    @BindView(R.id.edit_search)
    TextView edit_search;
    @BindView(R.id.pinyin)
    RecyclerView pinyin;
    @BindView(R.id.tv_date)
    TextView dateTv;
    @BindView(R.id.tv_earn)
    TextView earnTv;
    @BindView(R.id.tv_worker_name)
    TextView workNameTv;
    @BindView(R.id.tv_order_num)
    TextView orderNumTv;
    @BindView(R.id.image_net_state)
    ImageView netStateIv;
    @BindView(R.id.scan_layer)
    LinearLayout scanLayer;
    @BindView(R.id.textView)
    TextView pText;
    @BindView(R.id.tv_store_name)
    TextView tv_store_name;

    private HomeSelectedAdapter selectedAdapter;//所选
    private HomeTypeAdapter typeAdapter;//类别
    private HomeProductAdapter productAdapter;//商品

    private List<Production> tempProduces = new CopyOnWriteArrayList<>();//商品初始列表
    private ArrayList<ActivesBean> activeBeanList = new ArrayList<>();//活动列表
    private ArrayList<Production> productions = new ArrayList<>();//选择的奶茶
    private Vector<Production> printProducts = new Vector<>();//标签打印的产品
    private List<Production> gTemp = new ArrayList<>();//挂单商品集合

    //分页数据列表
    private List<Production> typeList = new ArrayList<>();//选择小类后的商品集合
    private List<Production> searchList = new ArrayList<>();//搜索商品结果集合
    private List<List<Production>> pLists = new CopyOnWriteArrayList<>();//分页显示的商品数据结构

    //点单商品信息集合
    private List<OrderInfo.DataBean> dataBeans = new ArrayList<>();
    private List<OrderInfo.DataBean> dataTemp = new ArrayList<>();
    //是否已有挂单
    private boolean hasHoldOn = false;
    private double totalPrice = 0, totalCut;
    private int totalCount = 0;//订单总价
    private QuickPayDialog quickPayDialog;//快捷支付弹窗
    private CashPayDialog cashPayDialog;//现金支付弹窗
    private SingleProAddonDialog addonDialog;//单品设置弹窗
    private MoreFunctionDialog functionDialog;//功能弹窗
    private CertainDialog certainDialog;//关机确认弹窗
    //商品recycler滑动翻页方法类
    int pageNo = 0;//当前页数

    //订单数据
    private OrderInfo orderInfo;
    //流水号
    private String orderNo = "";
    //是否是现金支付
    boolean isCash = false;

    private UsbManager usbManager;
    private int id = 0;//usb端口id
    private boolean b1, b2, b3, b4;//结束加载

    //是否推荐
    private boolean isRecommend = false;
    //商品过滤状态
    int m = 0;
    //小类
    List<SmallType> list = new ArrayList<>();
    //扫码回调方法次数，19次获取完
    private int time = 0;
    //定时更新时间flag
    private static final int MSG_UPDATE_CURRENT_TIME = 1;
    //重连socket消息flag
    private static final int RECONNECT_SOCKET = 2;
    //重连次数
    private static int RECONNECT_TIME = 1;
    //员工班次销售金额
    private float woeker_p = 0;
    //员工班次销售订单数
    private int orderNum = 0;
    //扫码后的内容
    String authNo = "";
    //搜索栏
    private String[] py = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    //搜索字符串
    private String searchStr = "";
    //提交订单返回
    OrderBack orderBack;
    //优惠
    double change;
    //倒计时
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日  HH : mm : ss", Locale.CHINA);
    Date curDate = new Date();
    //实收金额
    double earn1;
    //滑动删除的位置
    int swipeNo;
    //支付方式说明
    String payType, oldOrderId;
    //轮询handler
    MyHandler mHandler;
    // 语音合成对象
    private SpeechSynthesizer mTts;
    //是否先请求过扫码支付
    private boolean hasRequestedOrder = false;
    //小类位置
    int typePosition = 0;

    @Override
    protected int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        sendSocket();

        //连接标签机
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        connectUsb(UsbUtil.getBQName(HomeActivity.this));
        //连接打印机
        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                PrinterUtil.connectPrinter();
            }
        });

        //左侧点单列表
        selectedAdapter = new HomeSelectedAdapter(null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        left.setLayoutManager(linearLayoutManager);
        DividerItemDecoration d = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        d.setDrawable(getResources().getDrawable(R.drawable.divider_n, null));
        left.addItemDecoration(d);

        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(selectedAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(left);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(16);
        paint.setColor(Color.WHITE);

        selectedAdapter.enableSwipeItem();
        selectedAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                swipeNo = pos;
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                checkPriceForDisplay();
                if (!dataBeans.isEmpty() && pos == -1) {
                    dataBeans.remove(swipeNo);
                }
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(HomeActivity.this, R.color.red_delete));
                canvas.drawText("删除", 20, 35, paint);
            }
        });
        left.setAdapter(selectedAdapter);

        //分类列表
        typeAdapter = new HomeTypeAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rTop.setLayoutManager(manager);
        rTop.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                typePosition = position;
                typeList.clear();
                if (position != 0) {
                    m = 1;
                    for (Production p : tempProduces) {
                        if (typeAdapter.getData().get(position).getId() == p.getTypeId()) {
                            if (searchStr.isEmpty()) {
                                typeList.add(p);
                            } else {
                                if (p.getProAlsname().contains(searchStr)) {
                                    typeList.add(p);
                                }
                            }
                        }
                    }
                } else {
                    m = 0;
                }
                resetData(typePosition);
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelected(position == i);
                }
                typeAdapter.setNewData(list);
            }
        });

        //商品列表
        productAdapter = new HomeProductAdapter(tempProduces, this);
        rBottom.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rBottom.setAdapter(productAdapter);

        productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showSpe(position);
            }
        });

        //搜索栏
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        pinyin.setLayoutManager(gridLayoutManager);

        PinYinAdapter pinYinAdapter = new PinYinAdapter(Arrays.asList(py));
        MyDividerDecoration dividerDecoration = new MyDividerDecoration(this);
        pinyin.addItemDecoration(dividerDecoration);

        pinyin.setAdapter(pinYinAdapter);
        pinYinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick(500)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                searchStr += py[position];
                edit_search.setText(searchStr);
                search(searchStr);
            }
        });

        //计时
        mHandler = new MyHandler(this);
        mHandler.sendEmptyMessage(MSG_UPDATE_CURRENT_TIME);

        cashPayDialog = new CashPayDialog(this, this);
        initSpeaker();

        String name = SpUtil.getString(Constant.STORE_NAME).split("：")[1];
        tv_store_name.setText(name);
    }

    /**
     * 手势回调
     *
     * @param production 选择的商品
     * @param direction  位置
     * @param scaleBean  规格
     */
    @Override
    public void onPointerMoved(Production production, int direction, ScaleBean scaleBean) {
        if (scaleBean != null) {
            OrderInfo.DataBean dataBean = new OrderInfo.DataBean();
            dataBean.setProType(production.getProType());
            dataBean.setProId(production.getProId());
            dataBean.setMats(new ArrayList<MatsBean>());
            dataBean.setTastes(production.getTastes().subList(0, 1));
            //switch
            List<ScaleBean> s = new ArrayList<>();
            s.add(scaleBean);
            dataBean.setScales(s);

            //加入订单数据
            dataBean.setNum(1);
            dataBeans.add(dataBean);

            Production p = new Production();
            //左侧点单列表
            p.setProName(production.getProName());
            p.setProNameEn(production.getProNameEn());
            p.setNumber(1);
            p.setMatStr("默认加料");
            p.setMateP(0);
            p.setScaleStr(scaleBean.getScaName());
            p.setScalePrice(scaleBean.getPrice());
            p.setOrigionPrice(scaleBean.getOrigionPrice());
            p.setMats(null);
            productions.add(p);
            selectedAdapter.setNewData(productions);
            checkPriceForDisplay();
        }
    }

    /**
     * 外界设备扫码回调（扫描枪）
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //扫码监听，系统的软键盘  按下去是 -1, 不管，不拦截
        if (event.getDeviceId() == -1) {
            return false;
        }
        //按下弹起，识别到弹起的话算一次 有效输入
        //只要是 扫码枪的事件  都要把他消费掉 不然会被editText 显示出
        if (event.getAction() == KeyEvent.ACTION_UP) {
            time++;
            //只要数字，一维码里面没有 字母
            int code = event.getKeyCode();
            if (code >= KeyEvent.KEYCODE_0 && code <= KeyEvent.KEYCODE_9) {
                authNo += String.valueOf(code - KeyEvent.KEYCODE_0);
            }
            //无订单不走接口
            if (orderInfo != null) {
                if (time == 19) {
                    payByBox();
                }
            }
        }
        return true;
    }

    private static class MyHandler extends Handler {
        private WeakReference<HomeActivity> mActivity;

        MyHandler(HomeActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HomeActivity activity = mActivity.get();
            if (msg.what == MSG_UPDATE_CURRENT_TIME) {
                //更新时间
                if (activity != null) {
                    activity.updateCurrentTime();
                    sendEmptyMessageDelayed(MSG_UPDATE_CURRENT_TIME, 500);
                }
            } else if (msg.what == RECONNECT_SOCKET) {
                //尝试重连
                if (activity != null && !MyApplication.getSocketTool().isAlive()) {
                    sendEmptyMessageDelayed(RECONNECT_SOCKET, (10 * 1000) * RECONNECT_TIME);
                    activity.reconnectSocket();
                }
            }
        }
    }

    /**
     * 重启socket
     */
    private void reconnectSocket() {
        RECONNECT_TIME++;
        MyApplication.initSocket();
        sendSocket();
    }

    private void sendSocket() {
        //定时发送心跳
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    MyApplication.getSocketTool().sendHeart();
                }
            }, 200, 60 * 1000);
        }
    }

    /**
     * 更新时间
     */
    private void updateCurrentTime() {
        curDate.setTime(System.currentTimeMillis());
        String time = simpleDateFormat.format(curDate);
        dateTv.setText(time);
    }

    /**
     * 重置搜索
     */
    @SingleClick
    @OnClick(R.id.input_clear_btn)
    void inputClear() {
        if (searchStr.isEmpty()) {
            return;
        }
        edit_search.setText("");
        searchStr = "";
        searchList.clear();
        if (typeList.isEmpty()) {
            m = 0;
        } else {
            m = 1;
        }
        resetData(typePosition);
    }

    /**
     * 搜索商品
     */
    private void search(String searchStr) {
        m = 2;
        searchList.clear();
        if (!typeList.isEmpty()) {
            for (Production p : typeList) {
                if (!p.getProAlsname().isEmpty() && p.getProAlsname().contains(searchStr)) {
                    searchList.add(p);
                }
            }
        } else {
            for (Production p : tempProduces) {
                if (!p.getProAlsname().isEmpty() && p.getProAlsname().contains(searchStr)) {
                    searchList.add(p);
                }
            }
        }
        productAdapter.setNewData(searchList);
    }

    private boolean isShow = false;

    /**
     * 显示单品规格
     */
    private void showSpe(final int position) {
        Production production;
        if (m == 0) {
            production = pLists.get(pageNo).get(position);
        } else if (m == 1) {
            production = typeList.get(position);
        } else {
            production = searchList.get(position);
        }
        //点菜逻辑
        if (!isShow) {
            isShow = true;
            addonDialog = new SingleProAddonDialog(HomeActivity.this, HomeActivity.this, production);
            addonDialog.show();
        }
    }

    /**
     * 刷新页面
     */
    @SingleClick(1500)
    @OnClick(R.id.button5)
    void refresh() {
        resetAllData();
        loadData();
    }

    @Override
    protected void initData() {
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        progressDialog.show();
        getAdsList();
        getTypeList();
        getProList(Constant.NORECOMMEND);
        getActiveInfo();
    }

    /**
     * 并发请求结束loading
     */
    private void tryFinish() {
        if (b1 && b2 && b3 && b4) {
            MyOkHttp.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }, 2000);
        }
        MyOkHttp.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }, 5000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        workNameTv.setText(SpUtil.getString(Constant.WORKER_NAME));
        syncData();
        //重新登录后
        if (SpUtil.getBoolean("from_Login") && !SpUtil.getBoolean("firstRun")) {
            SpUtil.save("from_Login", false);
            resetAllData();
            //挂单
            gTemp.clear();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SpUtil.save("firstRun", false);
    }

    /**
     * 同步工作信息
     */
    private void syncData() {
        NetTool.getSyncData(new GsonResponseHandler<BaseBean<WorkInfo>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<WorkInfo> response) {
                if (response.getData() != null) {
                    woeker_p = (float) response.getData().getPushMoneyData().getTotalMoney();
                    orderNum = response.getData().getOrderCount();
                    SpUtil.save(Constant.ORDER_NUM, orderNum);
                    SpUtil.save(Constant.WORK_P, woeker_p);
                    earnTv.setText(String.format(Locale.CHINA, "%.2f元", response.getData().getPushMoneyData().getTotalMoney()));
                    orderNumTv.setText(String.format(Locale.CHINA, "%d单", response.getData().getOrderCount()));
                }
            }
        });
    }

    /**
     * 获取广告列表
     */
    private void getAdsList() {
        b1 = false;
        NetTool.getAdsList(SpUtil.getInt(Constant.STORE_ID), new GsonResponseHandler<BaseBean<List<AdsBean>>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<List<AdsBean>> response) {
                if (engine != null && engine.getBanner() != null) {
                    engine.getBanner().setImages(response.getData())
                            .setImageLoader(new MyLoader())
                            .isAutoPlay(true)
                            .setDelayTime(30000)
                            .start();
                }
                b1 = true;
                tryFinish();
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {

            }
        });
    }

    /**
     * 获取小类列表
     */
    private void getTypeList() {
        b2 = false;
        NetTool.getSmallType(SpUtil.getInt(Constant.STORE_ID), SpUtil.getInt(Constant.ENT_ID),
                SpUtil.getInt(Constant.PINPAI_ID), new GsonResponseHandler<BaseBean<List<SmallType>>>() {
                    @Override
                    public void onSuccess(int statusCode, BaseBean<List<SmallType>> response) {
                        list.clear();
                        list.add(new SmallType("全部", 0, 0, true));
                        list.addAll(response.getData());
                        typeAdapter.setNewData(list);
                        b2 = true;
                        tryFinish();
                    }

                    @Override
                    public void onFailure(int statusCode, String code, String error_msg) {

                    }
                });
    }

    /**
     * 重置小类选择为全部
     */
    private void resetTypeData() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelected(i == 0);
        }
        typeAdapter.setNewData(list);
    }

    int page1, leftNum;

    /**
     * 获取商品列表
     */
    private void getProList(String isRecommend) {
        b3 = false;
        NetTool.getStoreProduces(SpUtil.getInt(Constant.STORE_ID), isRecommend,
                new GsonResponseHandler<BaseBean<List<Production>>>() {
                    @Override
                    public void onSuccess(int statusCode, BaseBean<List<Production>> response) {
                        tempProduces.clear();
                        tempProduces.addAll(response.getData());
                        b3 = true;
                        page1 = response.getData().size() / 12;
                        leftNum = response.getData().size() % 12;
                        totalPage = page1 + (leftNum > 0 ? 1 : 0);
                        resetData(typePosition);
                        tryFinish();
                    }

                    @Override
                    public void onFailure(int statusCode, String code, String error_msg) {

                    }
                });
    }

    private void resetData(int position) {
        pLists.clear();
        if (position == 0) {
            for (int i = 0; i < page1; i++) {
                pLists.add(tempProduces.subList(i * 12, 12 * (i + 1)));
            }
            if (leftNum > 0) {
                pLists.add(tempProduces.subList(page1 * 12, tempProduces.size()));
            }
        } else {
            int page = typeList.size() / 12;
            int left = typeList.size() % 12;
            for (int i = 0; i < page; i++) {
                pLists.add(typeList.subList(i * 12, 12 * (i + 1)));
            }
            if (left > 0) {
                pLists.add(typeList.subList(page * 12, typeList.size()));
            }
        }
        if (!pLists.isEmpty()) {
            productAdapter.setNewData(pLists.get(0));
        }
    }

    /**
     * 获取活动列表
     */
    private void getActiveInfo() {
        b4 = false;
        NetTool.getActiveInfo(SpUtil.getInt(Constant.STORE_ID), SpUtil.getInt(Constant.ENT_ID), SpUtil.getInt(Constant.PINPAI_ID), new GsonResponseHandler<BaseBean<List<ActivesBean>>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<List<ActivesBean>> response) {
                activeBeanList.clear();
                activeBeanList.addAll(response.getData());
                b4 = true;
                tryFinish();
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {

            }
        });
    }

    @SingleClick(1500)
    @OnClick(R.id.button4)
    void getRecommend() {
        //是否推荐
        if (isRecommend) {
            getProList(Constant.RECOMMEND);
            isRecommend = false;
        } else {
            getProList(Constant.NORECOMMEND);
            isRecommend = true;
        }
    }

    /**
     * 计算价格
     */
    private void checkPriceForDisplay() {
        totalPrice = 0.0;
        totalCut = 0.0;
        totalCount = 0;

        for (Production p : productions) {
            //商品数量
            //计算原价，特价除外
            double c = 0;
            if (p.getMats() != null && !p.getMats().isEmpty()) {
                for (MatsBean m : p.getMats()) {
                    c += m.getIngredientPrice();
                }
            }
            //总价
            totalPrice += p.getScalePrice() * p.getNumber() + c * p.getNumber();
            //总数量
            totalCount += p.getNumber();
            //总优惠
            totalCut += (p.getOrigionPrice() - p.getScalePrice()) * p.getNumber();
        }

        total_number.setText(String.format(Locale.CHINA, "数量：%d 份", totalCount));
        total_price.setText(String.format(Locale.CHINA, "总价：%.2f 元", totalPrice));
        cut_number.setText(String.format(Locale.CHINA, "优惠：%.2f 元", totalCut));
        //副屏刷新
        if (engine != null) {
            engine.refresh(productions);
            engine.setPrice(totalPrice, totalCut);
        }
    }

    /**
     * 单品详情dialog配置回调
     */
    @Override
    public void onSingleCallBack(int proId, int number, Production productsBean, OrderInfo.DataBean dataBean, double origionPrice, double price) {
        //加入订单数据
        dataBean.setNum(number);
        //左侧点单列表
        productsBean.setNumber(number);
        productsBean.setScalePrice(price);
        productsBean.setOrigionPrice(origionPrice);
        productions.add(productsBean);
        addonDialog.cancel();
        addonDialog = null;
        isShow = false;
        //刷新选择列表数据
        selectedAdapter.setNewData(productions);
        //计算副频金额
        checkPriceForDisplay();

        dataBean.setProId(productsBean.getProId());
        dataBean.setProType(productsBean.getProType());
        dataBeans.add(dataBean);
        inputClear();
    }

    @Override
    public void onCancelCallBack() {
        this.isShow = false;
    }

    /**
     * 挂单
     */
    @SingleClick
    @OnClick(R.id.button1)
    void OrderHoldOn() {
        //无选择数据 且 无挂单数据，按钮无响应
        if (productions.isEmpty() && gTemp.isEmpty()) {
            return;
        }
        if (hasHoldOn) {
            Toast.makeText(this, "已显示挂单！", Toast.LENGTH_LONG).show();
            productions.addAll(gTemp);
            dataBeans.addAll(dataTemp);
            gTemp.clear();
            dataTemp.clear();
            selectedAdapter.setNewData(productions);//显示挂单数据
            hasHoldOn = false;
            hasGua.setVisibility(View.INVISIBLE);
            checkPriceForDisplay();
        } else {
            //将当前数据放入挂单list中
            gTemp.addAll(productions);
            dataTemp.addAll(dataBeans);
            DeleteAll();
            hasHoldOn = true;
            hasGua.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 删除当前单子列表
     */
    @SingleClick(1500)
    @OnClick(R.id.button2)
    void DeleteAll() {
        clear();
    }

    /**
     * 扫码取消
     */
    @SingleClick
    @OnClick({R.id.button, R.id.imageView2})
    void disapper() {
        authNo = "";
        time = 0;
        scanLayer.setVisibility(View.GONE);
    }

    /**
     * 快捷支付弹窗
     */
    @SingleClick(1500)
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
     * 功能按钮
     */
    @SingleClick(1500)
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
    @SingleClick(1500)
    @OnClick(R.id.btn_home_shutdown)
    void ShutDown() {
        if (certainDialog == null) {
            certainDialog = new CertainDialog(this, this, "注意！", "确认要关机吗？");
            certainDialog.show();
        }
    }


    /**
     * 结算
     */
    @SingleClick(1500)
    @OnClick(R.id.btn_home_check)
    void goCheckOut() {
        if (productions.isEmpty()) {
            ToastUtils.showLong("还未点餐！");
            return;
        }
        Intent i = new Intent(HomeActivity.this, PaymentActivity.class);
        if (!activeBeanList.isEmpty()) {
            i.putParcelableArrayListExtra("actives", activeBeanList);
        }
        initOrder();
        i.putExtra("orderInfo", orderInfo);

        if (!productions.isEmpty()) {
            i.putParcelableArrayListExtra("proList", productions);
        }
        i.putExtra("totalCount", totalCount);
        i.putExtra("orderTotal", totalPrice);
        startActivity(i);
    }

    /**
     * 重置所有数据与UI，仅限 刷新页面 或者  重新登陆 的情况
     */
    private void resetAllData() {
        //过滤
        typeList.clear();
        searchList.clear();
        searchStr = "";
        edit_search.setText("");
        pageNo = 0;
        //订单
        orderInfo = null;
        dataBeans.clear();
        //主页显示
        total_number.setText("数量：");
        cut_number.setText("优惠：");
        total_price.setText("金额：");
        productions.clear();
        m = 0;
        //左侧置空
        selectedAdapter.setNewData(null);
        //副屏
        engine.clear();
        //小类
        resetTypeData();
        //总价
        totalPrice = 0;
        //总优惠
        totalCut = 0;
        //商品显示列表
        if (!pLists.isEmpty()) {
            productAdapter.setNewData(pLists.get(0));
        }
    }

    /**
     * 下完单结账后重置订单所有数据、UI显示
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
        totalPrice = 0;
        totalCut = 0;
        orderInfo = null;
        //左侧置空
        selectedAdapter.setNewData(null);
        //下完单还原商品显示状态为全部、清空搜索记录
        inputClear();
    }

    /**
     * 交班、日结回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.WORK_BACK_OVER:
                    SpUtil.save(Constant.WORK_P, (float) 0);
                    SpUtil.save(Constant.ORDER_NUM, 0);
                    clear();
                    woeker_p = 0;
                    orderNum = 0;
                    ToastUtils.showLong("已完成交班！即将返回登录页面！");
                    MyOkHttp.mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        }
                    }, 5000);
                    break;
                case Constant.WORK_BACK_DAILY:
                    SpUtil.save(Constant.WORK_P, (float) 0);
                    SpUtil.save(Constant.ORDER_NUM, 0);
                    ToastUtils.showLong("系统将于30秒之后关机!");
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    MyOkHttp.mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            PowerUtil.shutdown();
                            System.exit(0);
                        }
                    }, 30 * 1000);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 初始化订单信息
     */
    private void initOrder() {
        orderInfo = new OrderInfo();
        orderInfo.setOrderID(UUID.randomUUID().toString().replace("-", ""));
        orderInfo.setShopID(SpUtil.getInt(Constant.STORE_ID));
        orderInfo.setCreateTime(PrinterUtil.getDate());
        orderInfo.setEnterpriseID(SpUtil.getInt(Constant.ENT_ID));
        orderInfo.setPinpaiID(SpUtil.getInt(Constant.PINPAI_ID));
        orderInfo.setQuanIds(new ArrayList<Integer>());
        orderInfo.setDiscountsType("2");
        if (!dataBeans.isEmpty()) {
            orderInfo.setData(dataBeans);
        }
        orderInfo.setOrdSource("3");
    }

    /**
     * 结账 dialog 按钮回调
     */
    @Override
    public void OnDialogOkClick(final int type, final double earn, final double cost, final double charge, String name) {
        change = charge;
        earn1 = earn;
        switch (name) {
            case "QuickPayDialog":
                quickPayDialog.dismiss();
                if (1 == type) {
                    cashPayDialog.show();
                    cashPayDialog.setTotalMoney(totalPrice);
                } else {
                    initOrder();
                    orderInfo.setPayType(type == 2 ? "020" : "010");
                    payType = type == 2 ? "支付宝支付" : "微信支付";
                    orderBack = null;
                    setLabelData();
                    //线上支付
                    if (!hasRequestedOrder) {
                        hasRequestedOrder = true;
                    } else {
                        orderInfo.setOldOrderId(oldOrderId);
                    }
                    NetTool.postOrder(PrinterUtil.toJson(orderInfo), new GsonResponseHandler<BaseBean<OrderBack>>() {
                        @Override
                        public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                            if (response.getCode().equals("0")) {
                                orderBack = response.getData();
                                orderNo = response.getData().getOrderNo();
                                oldOrderId = response.getData().getOrderId();
                                scanLayer.setVisibility(View.VISIBLE);
                                pText.setText(String.format(Locale.CHINA, "%.2f元", totalPrice));

                            }
                        }

                        @Override
                        public void onFailure(int statusCode, String code, String error_msg) {
                            ToastUtils.showLong("网络连接已断开");
                        }
                    });

                }
                break;
            //现金支付回调
            case "CashPayDialog":
                //弹钱箱，打印小票
                isCash = true;
                initOrder();
                orderInfo.setPayType("900");
                cashPayDialog.cancel();
                payType = "现金支付";
                setLabelData();
                if (MyApplication.getSocketTool().isAlive()) {
                    if (hasRequestedOrder) {
                        orderInfo.setOldOrderId(oldOrderId);
                    }
                    //网络正常走接口
                    NetTool.postOrder(PrinterUtil.toJson(orderInfo), new GsonResponseHandler<BaseBean<OrderBack>>() {
                        @Override
                        public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                            orderNo = response.getData().getOrderNo();
                            orderBack = response.getData();
                            PrintOrder(orderBack, null, charge < 0 ? 0 : charge, "现金支付", earn == 0 ? totalPrice : earn, totalCut, "门店点单");
                        }

                        @Override
                        public void onFailure(int statusCode, String code, String error_msg) {
                            SocketTool.getInstance().setAlive(false);
                            netStateIv.setImageResource(R.drawable.wifi_error);
                            PrintOrder(orderBack, null, charge < 0 ? 0 : charge, "现金支付", earn == 0 ? totalPrice : earn, totalCut, "门店点单");
                        }
                    });
                } else {
                    //网络不正常走本地
                    PrintOrder(orderBack, null, charge < 0 ? 0 : charge, "现金支付", earn == 0 ? totalPrice : earn, totalCut, "门店点单");
                }
                break;
            case "CertainDialog":
                //关机确认
                certainDialog.cancel();
                MyOkHttp.mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PowerUtil.shutdown();
                        System.exit(0);
                    }
                }, 2000);
                break;
        }
    }

    /**
     * 盒子支付接口
     */
    private void payByBox() {
        time = 0;
        if (orderInfo != null) {
            NetTool.payByBox(orderNo, orderInfo.getPayType(), authNo, new GsonResponseHandler<BaseBean<BoxPay>>() {
                @Override
                public void onSuccess(int statusCode, BaseBean<BoxPay> response) {
                    authNo = "";
                    scanLayer.setVisibility(View.GONE);
                    if (response.getCode().equals("0")) {
                        //网络正常清空下，播放语音提示
                        if (MyApplication.getSocketTool().isAlive()) {
                            String text = orderInfo.getPayType().equals("010") ? "微信到账" + totalPrice : "支付宝到账" + totalPrice;
                            if (mTts != null) {
                                int code = mTts.startSpeaking(text, mTtsListener);
                                Log.e("home:", code != 0 ? "语音合成失败,错误码: " + code : "ok");
                            }
                        }
                        PrintOrder(orderBack, null, change < 0 ? 0 : change, orderInfo.getPayType().equals("010") ? "微信支付" : "支付宝支付", totalPrice, totalCut, "门店点单");
                    } else {
                        ToastUtils.showLong(response.getMsg());
                    }
                }

                @Override
                public void onFailure(int statusCode, String code, String error_msg) {
                    scanLayer.setVisibility(View.GONE);
                    authNo = "";
                }
            });
        }
    }


    /**
     * 扫码完毕回调
     */
    @Override
    public void OnUsbCallBack(String name) {
        authNo = "";
        time = 0;
        scanLayer.setVisibility(View.GONE);
        clear();
    }

    /**
     * 连接USB标签机
     */
    private void connectUsb(String name) {
        //通过USB设备名找到USB设备
        UsbDevice usbDevice = PrinterUtil.getUsbDeviceFromName(HomeActivity.this, name);

        //判断USB设备是否有权限
        if (usbDevice != null) {
            if (usbManager.hasPermission(usbDevice)) {
                usbConn(usbDevice);
            } else {
                getPermission(usbDevice);
            }
        }
    }

    /**
     * 请求USB权限，（这个可以通过修改framework代码绕过，不用每次连接POS都弹窗）
     * @param usbDevice printer
     */
    protected void getPermission(UsbDevice usbDevice) {
        //请求权限
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        usbManager.requestPermission(usbDevice, mPermissionIntent);
    }

    /**
     * 标签机数据
     */
    private void setLabelData() {
        printProducts.clear();

        for (int i = 0; i < productions.size(); i++) {
            String name = productions.get(i).getProName();
            String nameEn = productions.get(i).getProNameEn();
            String taste = productions.get(i).getTasteStr();
            int count = productions.get(i).getNumber();
            for (int j = 0; j < productions.get(i).getNumber(); j++) {
                Production p = new Production();
                p.setProName(name);
                p.setProNameEn(nameEn);
                p.setTasteStr(taste);
                p.setScalePrice(productions.get(i).getScalePrice() + productions.get(i).getMateP());
                p.setNumber(count);
                p.setScaleStr(productions.get(i).getScaleStr());
                p.setMatStr(productions.get(i).getMatStr());
                printProducts.add(p);
            }
        }
    }

    /**
     * 打印订单小票，分 连接上服务器 与 未连接上服务器
     * charge:找零
     */
    private void PrintOrder(final OrderBack orderBack, final OrderDetails.OrderdetailBean orderdetailBean, final double charge, String type, double ssPrice, double totalCut, String source) {
        progressDialog.show();
        hasRequestedOrder = false;
        woeker_p += totalPrice;

        orderNum++;
        countsTemp = printProducts.size();
        counts = printProducts.size();

        //小票数据
        if (orderBack != null) {
            //正常情况
            PrinterUtil.printString80(productions, null, "C" + orderBack.getOrderNo().substring(orderBack.getOrderNo().length() - 4),
                    SpUtil.getString(Constant.WORKER_NAME), orderBack.getOrigionTotalPrice(), orderBack.getTotalPrice(), String.valueOf(ssPrice), String.valueOf(charge),
                    String.valueOf(totalCut), orderBack.getCreatTime(), type, source);
        } else if (orderdetailBean != null) {
            //微信小程序
            PrinterUtil.printString80(null, orderdetailBean.getPros(), "W" + orderdetailBean.getOrdNo().substring(orderdetailBean.getOrdNo().length() - 4),
                    SpUtil.getString(Constant.WORKER_NAME), String.valueOf(orderdetailBean.getTotalPrice()), String.valueOf(orderdetailBean.getTotalPrice()), String.valueOf(ssPrice), String.valueOf(charge),
                    String.valueOf(totalCut), orderdetailBean.getCreateTime(), type, source);
        } else {
            //断网状态
            orderNo = orderNum < 10 ? "N000" + orderNum : orderNum < 100 ? "N00" + orderNum : orderNum < 1000 ? "N0" + orderNum : "N" + orderNum;
            //保存订单信息json
            ((MyApplication) getApplication()).getDaoSession().getNativeOrdersDao().insert(new NativeOrders(System.currentTimeMillis(), PrinterUtil.toJson(orderInfo)));

            PrinterUtil.printString80(productions, null, orderNo, SpUtil.getString(Constant.WORKER_NAME), String.valueOf(totalPrice), String.valueOf(totalPrice),
                    earn1 == 0 ? String.valueOf(ssPrice) : String.valueOf(earn1), String.valueOf(charge),
                    String.valueOf(totalCut), PrinterUtil.getDate(), type, source);
        }
        //订单置空
        orderInfo = null;

        //工作信息更新
        earnTv.setText(String.format(Locale.CHINA, "%.2f元", woeker_p));
        orderNumTv.setText(String.format(Locale.CHINA, "%d单", orderNum));
        //存储工作记录
        SpUtil.save(Constant.WORK_P, woeker_p);
        SpUtil.save(Constant.ORDER_NUM, orderNum);

        printLabel();

        MyOkHttp.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                clear();
            }
        }, 1500);
    }

    /**
     * 单次打印标签的数量
     */
    private int counts;
    private int countsTemp;

    /**
     * 打印标签
     */
    private void printLabel() {
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null ||
                !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
            ToastUtils.showLong("标签打印机未连接");
            return;
        }

        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] != null
                        && DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
                    ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder("MainActivity_sendContinuity_Timer");
                    ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, threadFactoryBuilder);
                    scheduledExecutorService.schedule(threadFactoryBuilder.newThread(new Runnable() {
                        @Override
                        public void run() {
                            counts--;
                            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getCurrentPrinterCommand() == PrinterCommand.TSC) {
                                sendLabel(printProducts.get(counts).getProName(), printProducts.get(counts).getTasteStr(), String.valueOf(printProducts.get(counts).getScalePrice()),
                                        counts, countsTemp, printProducts.get(counts).getMatStr(), printProducts.get(counts).getScaleStr(), printProducts.get(counts).getProNameEn());
                            }
                        }
                    }), 500, TimeUnit.MILLISECONDS);
                }
            }
        });
    }

    /**
     * 结算页面回调（正常状态）
     * @param orderBack 回调订单信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paymentBack(OrderBack orderBack) {
        this.orderBack = orderBack;
        orderNo = orderBack.getOrderNo();
        setLabelData();
        PrintOrder(orderBack, null, orderBack.getCharge() < 0 ? 0 : orderBack.getCharge(), orderBack.getPayType().equals("900") ? "现金支付" : orderBack.getPayType().equals("010") ? "微信支付" : "支付宝支付", orderBack.getTotal(), orderBack.getCut() < 0 ? 0 : orderBack.getCut(), "门店点单");
    }

    /**
     * 结算页面回调（断网、无法连上server状态）
     * @param noNetPayBack 回调订单信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paymentNativeBack(NoNetPayBack noNetPayBack) {
        //结算回调，无网
        PrintOrder(null, null, noNetPayBack.getCharge() < 0 ? 0 : orderBack.getCharge(), noNetPayBack.getType(), noNetPayBack.getTotalPrice(), noNetPayBack.getCut() < 0 ? 0 : noNetPayBack.getCut(), "门店点单");
    }

    /**
     * 哦安短是否正常连上server
     * @param netErrorHandler 断网回调信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void netState(NetErrorHandler netErrorHandler) {
        if (netErrorHandler.isConnected()) {
            //网络正常
            netStateIv.setImageResource(R.drawable.wifi_ok);
            RECONNECT_TIME = 1;
        } else {
            //无法正常连接到服务器
            netStateIv.setImageResource(R.drawable.wifi_error);
            MyApplication.getSocketTool().stopSocket();
            //关闭心跳，不继续发送，轮询时间段重启websocket，首次延迟10秒
            if (timer != null) {
                timer.cancel();
                timer.purge();
                timer = null;
                if (RECONNECT_TIME == 1) {
                    mHandler.sendEmptyMessageDelayed(RECONNECT_SOCKET, 10000);
                }
            }
        }
    }

    /**
     * 小程序、公众号下单信息
     * @param h5TaskBean 小程序下单信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void H5Payment(H5TaskBean h5TaskBean) {
        //来自小程序点单，加入线程池中
        String s = (h5TaskBean.getData().getOrderdetail().getOrdSource().equals("1") ? "微信" : "支付宝") + "小程序";
        if (mTts != null) {
            String ss = "有来自" + s + "的订单，请查看";
            int code = mTts.startSpeaking(ss, mTtsListener);
            Log.e("home:", code != 0 ? "语音合成失败,错误码: " + code : "ok");
        }
        setLabelDataFromH5(h5TaskBean);

        //营业金额增加
        woeker_p = woeker_p + (float) h5TaskBean.getData().getOrderdetail().getTotalPrice();
        SpUtil.save(Constant.WORK_P, woeker_p);
        orderNum++;
        SpUtil.save(Constant.ORDER_NUM, orderNum);

        PrintOrder(null, h5TaskBean.getData().getOrderdetail(), 0, "微信支付", h5TaskBean.getData().getOrderdetail().getTotalPrice(), 0, s);
    }

    private void setLabelDataFromH5(H5TaskBean h5TaskBean) {
        //标签机数据
        printProducts.clear();
        for (int i = 0; i < h5TaskBean.getData().getOrderdetail().getPros().size(); i++) {
            String name = h5TaskBean.getData().getOrderdetail().getPros().get(i).getProName();
            String taste = h5TaskBean.getData().getOrderdetail().getPros().size() > 0 ? "默认口味" : h5TaskBean.getData().getOrderdetail().getPros().get(i).getTastes().get(i).getTasteName();
            int count = h5TaskBean.getData().getOrderdetail().getPros().get(i).getProCount();
            String nameEn = h5TaskBean.getData().getOrderdetail().getPros().get(i).getProNameEn();
            for (int j = 0; j < count; j++) {
                Production p = new Production();
                p.setProName(name);
                p.setProNameEn(nameEn);
                p.setTasteStr(taste);
                p.setScalePrice(h5TaskBean.getData().getOrderdetail().getPros().get(i).getSumPrice() == null ? h5TaskBean.getData().getOrderdetail().getPros().get(i).getPrice() : Double.parseDouble(h5TaskBean.getData().getOrderdetail().getPros().get(i).getSumPrice()));
                p.setNumber(count);
                p.setScaleStr(h5TaskBean.getData().getOrderdetail().getPros().get(i).getScaleName());
                p.setMatStr(h5TaskBean.getData().getOrderdetail().getPros().get(i).getMatStr());
                printProducts.add(p);
            }
        }
    }

    /**
     * 连接标签机
     * @param usbDevice POS printer
     */
    private void usbConn(UsbDevice usbDevice) {
        new DeviceConnFactoryManager.Build()
                .setId(id)
                .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.USB)
                .setUsbDevice(usbDevice)
                .setContext(this)
                .build();
        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].openPort();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        EventBus.getDefault().unregister(this);
        DeviceConnFactoryManager.closeAllPort();
        if (ThreadPool.getInstantiation() != null) {
            ThreadPool.getInstantiation().stopThreadPool();
        }
        PrinterUtil.disconnectPrinter();
    }

    /**
     * 发送标签
     */
    void sendLabel(String pName, String pContent, String price, int i, int number, String matStr, String scale, String proNameEn) {
        try {
            i = i + 1;
            LabelCommand tsc = new LabelCommand();
            // 设置标签尺寸，按照实际尺寸设置
            tsc.addSize(40, 30);
            // 设置标签间隙，按照实际尺寸设置，如果为无间隙纸则设置为0
            tsc.addGap(2);
            // 设置打印方向
            tsc.addDirection(LabelCommand.DIRECTION.FORWARD, LabelCommand.MIRROR.NORMAL);
            // 开启带Response的打印，用于连续打印
            tsc.addQueryPrinterStatus(LabelCommand.RESPONSE_MODE.ON);
            // 设置原点坐标
            tsc.addReference(10, 3);
            // 撕纸模式开启
            tsc.addTear(EscCommand.ENABLE.ON);
            // 清除打印缓冲区
            tsc.addCls();
            // 绘制简体中文
            //店名
            tsc.addText(0, 0, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    SpUtil.getString(Constant.STORE_NAME) + "\n");
            //商品中文名
            tsc.addText(50, 32, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    pName + "\n");
            //商品英文名
            tsc.addText(50, 62, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    proNameEn + "\n");
            //加料
            if (matStr == null || matStr.isEmpty()) {
                matStr = "默认加料";
            }
            tsc.addText(0, 90, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    matStr);
            //规格
            tsc.addText(0, 120, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    scale + "\n");
            //数量、价格
            tsc.addText(0, 150, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    pContent + " ￥" + price + " " + "\n");
            //工作人员信息，标签index
            tsc.addText(0, 180, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    SpUtil.getString(Constant.WORKER_NAME) + " " + i + "/" + number + "\n");
            //时间
            tsc.addText(0, 210, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    PrinterUtil.getTabTime() + orderNo.substring(orderNo.length() - 4) + "-" + PrinterUtil.getTabHour() + "\n");
            // 绘制logo图片
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.logo9);
            tsc.addBitmap(235, 40, LabelCommand.BITMAP_MODE.OVERWRITE, 70, b);
            // 打印标签
            tsc.addPrint(1, 1);
            // 打印标签后 蜂鸣器响
            tsc.addSound(2, 100);
            //命令bytes
            Vector<Byte> datas = tsc.getCommand();
            // 发送数据
            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null) {
                return;
            }
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].sendDataImmediately(datas);
        } catch (Exception e) {
            Log.e(TAG, "print label error");
        }
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


    /**
     * 广播
     */
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ACTION_USB_PERMISSION:
                        synchronized (this) {
                            if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                                if (device != null) {
                                    usbConn(device);
                                }
                            }
                        }
                        break;
                    //Usb连接断开、蓝牙连接断开广播
                    case ACTION_USB_DEVICE_DETACHED:

                        break;
                    //Usb连接断开、蓝牙连接广播
                    case ACTION_USB_DEVICE_ATTACHED:
                        final String deviceName = UsbUtil.getBQName(HomeActivity.this);
                        final String xpName = UsbUtil.getXPName(HomeActivity.this);
                        if (!deviceName.isEmpty() || !xpName.isEmpty()) {
                            if (!progressDialog.isShowing()) {
                                progressDialog.show();
                            }

                            //先主动调用断开连接，释放端口
                            PrinterUtil.disconnectPrinter();
                            MyOkHttp.mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    connectUsb(deviceName);
                                    PrinterUtil.connectPrinter();
                                    try {
                                        Thread.sleep(1000);
                                    } catch (Exception e) {
//                                    Log.e("thread", e.getMessage());
                                    }
                                    progressDialog.dismiss();
                                }
                            }, 6000);
                            MyOkHttp.mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }, 7000);
                        }
                        break;
                    case DeviceConnFactoryManager.ACTION_CONN_STATE:
                        int state = intent.getIntExtra(DeviceConnFactoryManager.STATE, -1);
                        int deviceId = intent.getIntExtra(DeviceConnFactoryManager.DEVICE_ID, -1);
                        switch (state) {
                            case DeviceConnFactoryManager.CONN_STATE_DISCONNECT:
                                if (id == deviceId) {
                                    ToastUtils.showShort("标签打印机已断开连接");
                                }
                                //还剩余的标签打印数量 5 2
                                for (int i = countsTemp; i > counts + 1; i--) {
                                    printProducts.remove(i - 1);
                                }
                                break;
                            case DeviceConnFactoryManager.CONN_STATE_CONNECTING:

                                break;
                            case DeviceConnFactoryManager.CONN_STATE_CONNECTED:
                                counts += 1;
                                if (!printProducts.isEmpty()) {
                                    printLabel();
                                }
                                break;
                            case CONN_STATE_FAILED:

                                break;
                            default:
                                break;
                        }
                        break;
                    case Constant.SERVICE_ACTION:
                        //重启service
                        startService(intent);
                        break;
                    case ACTION_QUERY_PRINTER_STATE:
                        if (counts > 0) {
                            printLabel();
                        } else {
                            printProducts.clear();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    @SingleClick
    @OnClick(R.id.btn_up)
    void btnUp() {
        pageNo--;
        if (pageNo < 0) {
            pageNo = 0;
            return;
        }
        if (pLists.size() > 1) {
            productAdapter.setNewData(pLists.get(pageNo));
        } else {
            productAdapter.setNewData(pLists.get(0));
        }
    }

    int totalPage = 1;

    @SingleClick
    @OnClick(R.id.btn_next)
    void btnNext() {
        pageNo++;
        if (pageNo >= totalPage) {
            pageNo = totalPage - 1;
            return;
        }
        if (pLists.size() > 1) {
            productAdapter.setNewData(pLists.get(pageNo));
        } else {
            productAdapter.setNewData(pLists.get(0));
        }
    }

    /**
     * 参数设置 speak
     */
    private void initSpeaker() {
        mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);
        if (mTts != null) {
            // 清空参数
            mTts.setParameter(SpeechConstant.PARAMS, null);
            //设置合成
            //设置使用云端引擎
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            //设置发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoqi");
        }
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {

        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度

        }

        @Override
        public void onCompleted(SpeechError error) {

        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        }
    };

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {

        }
    };
}
