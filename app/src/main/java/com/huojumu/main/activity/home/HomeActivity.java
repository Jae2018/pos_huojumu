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
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.huojumu.adapter.HorizontalPageLayoutManager;
import com.huojumu.adapter.PagingScrollHelper;
import com.huojumu.adapter.PinYinAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.dialog.ScanWarnDialog;
import com.huojumu.main.activity.dialog.SingleProAddonDialog;
import com.huojumu.main.activity.function.PaymentActivity;
import com.huojumu.main.activity.login.LoginActivity;
import com.huojumu.main.dialogs.CashPayDialog;
import com.huojumu.main.dialogs.CertainDialog;
import com.huojumu.main.dialogs.DialogInterface;
import com.huojumu.main.dialogs.MoreFunctionDialog;
import com.huojumu.main.dialogs.QuickPayDialog;
import com.huojumu.main.dialogs.SingleProCallback;
import com.huojumu.model.ActivesBean;
import com.huojumu.model.AdsBean;
import com.huojumu.model.BaseBean;
import com.huojumu.model.BoxPay;
import com.huojumu.model.MatsBean;
import com.huojumu.model.NativeOrders;
import com.huojumu.model.NoNetPayBack;
import com.huojumu.model.OrderBack;
import com.huojumu.model.OrderInfo;
import com.huojumu.model.Production;
import com.huojumu.model.SmallType;
import com.huojumu.model.WorkBean;
import com.huojumu.utils.Constant;
import com.huojumu.utils.DeviceConnFactoryManager;
import com.huojumu.utils.H5Order;
import com.huojumu.utils.LogUtil;
import com.huojumu.utils.MyDividerDecoration;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PowerUtil;
import com.huojumu.utils.PrinterCommand;
import com.huojumu.utils.PrinterUtil;
import com.huojumu.utils.SingleClick;
import com.huojumu.utils.SocketBack;
import com.huojumu.utils.SpUtil;
import com.huojumu.utils.ThreadFactoryBuilder;
import com.huojumu.utils.ThreadPool;
import com.huojumu.utils.UsbUtil;
import com.tools.command.EscCommand;
import com.tools.command.LabelCommand;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

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
        SingleProCallback, PagingScrollHelper.onPageChangeListener {

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
    @BindView(R.id.parent_relative)
    RelativeLayout parent_relative;
    @BindView(R.id.web_order)
    WebView webView;
    @BindView(R.id.edit_search)
    TextView edit_search;
    @BindView(R.id.pinyin)
    RecyclerView pinyin;
    @BindView(R.id.worker_name)
    TextView workName;
    @BindView(R.id.order_num)
    TextView order_num;
    @BindView(R.id.worker_name1)
    TextView workName1;
    @BindView(R.id.order_num1)
    TextView order_num1;
//    @BindView(R.id.layer)
//    LinearLayout layer;

    private HomeSelectedAdapter selectedAdapter;//所选
    private HomeTypeAdapter typeAdapter;//类别
    private HomeProductAdapter productAdapter;//商品

    private List<Production> tempProduces = new ArrayList<>();//商品列表
    private ArrayList<ActivesBean> activeBeanList = new ArrayList<>();//活动列表
    private ArrayList<Production> productions = new ArrayList<>();//选择的奶茶
    private ArrayList<Production> printProducts = new ArrayList<>();//标签打印的产品
    private List<Production> typeList = new ArrayList<>();//选择小类后的商品集合
    private List<Production> searchList = new ArrayList<>();//搜索商品结果集合
    private List<Production> gTemp = new ArrayList<>();//挂单商品集合
    //点单商品信息集合
    private List<OrderInfo.DataBean> dataBeans = new ArrayList<>();

    private boolean hasHoldOn = false;//是否已有挂单
    private double totalPrice = 0, totalCut;
    private int totalCount = 0;//订单总价
    private QuickPayDialog quickPayDialog;//快捷支付弹窗
    private CashPayDialog cashPayDialog;//现金支付弹窗
    private SingleProAddonDialog addonDialog;//单品设置弹窗
    private MoreFunctionDialog functionDialog;//功能弹窗
    private CertainDialog certainDialog;//关机确认弹窗
    //商品recycler滑动翻页方法类
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
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

    int count = 0;//商品数量
    String name, taste;//名字、口味

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
    private float woeker_p = 0;//员工班次销售金额
    private int orderNum = 0;//员工班次销售订单数

    String authNo = "";//扫码后的内容
    //搜索栏
    private String[] py = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    //搜索字符串
    private String searchStr = "";
    //提交订单返回
    OrderBack orderBack;
    //优惠
    double change;
    //标签机连续打印次数
    private int printcount = 0;
    //倒计时
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日  hh : mm : ss", Locale.CHINA);
    Date curDate = new Date();
    //实收、消费金额
    double earn1, cost1;
    //滑动删除的位置
    int swipeNo;
    //支付方式说明
    String payType;
    private ScanWarnDialog warnDialog;

    @Override
    protected int setLayout() {
        return R.layout.activity_home;
    }

    @SuppressWarnings("setJavaScriptEnabled")
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        MyApplication.getSocketTool().sendHeart();

        //连接标签机
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        connectUsb(UsbUtil.getBQName(HomeActivity.this));
        //连接打印机
        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                PrinterUtil.connectPrinter(HomeActivity.this);
            }
        });

        webView.setWebChromeClient(new WebChromeClient());

        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        HorizontalPageLayoutManager horizontalPageLayoutManager = new HorizontalPageLayoutManager(3, 4);

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
                if (pos == -1) {
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
                typeList.clear();
                if (position != 0) {
                    m = 1;
                    for (Production p : tempProduces) {
                        if (typeAdapter.getData().get(position).getId() == p.getTypeId()) {
                            typeList.add(p);
                        }
                    }
                    productAdapter.setNewData(typeList);
                } else {
                    m = 0;
                    productAdapter.setNewData(tempProduces);
                }
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelected(position == i);
                }
                typeAdapter.setNewData(list);
            }
        });

        //商品列表
        productAdapter = new HomeProductAdapter(tempProduces);
        rBottom.setLayoutManager(horizontalPageLayoutManager);
        rBottom.setAdapter(productAdapter);

        productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick(1500)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showSpe(position);
            }
        });
        scrollHelper.setUpRecycleView(rBottom);

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
        MyHandler mHandler = new MyHandler(this);
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_CURRENT_TIME, 500);

        webView.addJavascriptInterface(new JsInterface(), "JSInterface");

        cashPayDialog = new CashPayDialog(this, this);

        warnDialog = new ScanWarnDialog(this, this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //扫码监听，系统的软键盘  按下去是 -1, 不管，不拦截
        if (event.getDeviceId() == -1) {
            return false;
        }
        if (canScan) {
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
        }
        return super.dispatchKeyEvent(event);
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
                if (activity != null) {
                    activity.updateCurrentTime();
                    sendEmptyMessageDelayed(MSG_UPDATE_CURRENT_TIME, 500);
                }
            }
        }
    }

    /**
     * 更新时间
     */
    private void updateCurrentTime() {
        curDate.setTime(System.currentTimeMillis());
        String time = simpleDateFormat.format(curDate);
        workName.setText(time);
    }

    /**
     * 重置搜索
     */
    @SingleClick
    @OnClick(R.id.input_clear_btn)
    void inputClear() {
        edit_search.setText("");
        searchStr = "";
        searchList.clear();
        if (typeList.isEmpty()) {
            m = 0;
            productAdapter.setNewData(tempProduces);
        } else {
            m = 1;
            productAdapter.setNewData(typeList);
        }
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

    /**
     * 显示单品规格
     */
    private void showSpe(final int position) {
        Production production;
        if (m == 0) {
            production = tempProduces.get(position);
        } else if (m == 1) {
            production = typeList.get(position);
        } else {
            production = searchList.get(position);
        }
        //点菜逻辑
        addonDialog = new SingleProAddonDialog(HomeActivity.this, HomeActivity.this, production, position);
        addonDialog.show();
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
        ld2 = new LoadingDialog(this);
        ld2.setLoadingText("加载中...")
                .setFailedText("加载失败，请重试");
        ld2.show();
        getAdsList();
        getTypeList();
        getProList("0");
        getActiveInfo();
    }

    /**
     * 并发请求结束loading
     */
    private void tryFinish() {
        if (b1 && b2 && b3 && b4) {
            ld2.close();
        }
        MyOkHttp.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ld2 != null) {
                    ld2.close();
                }
            }
        }, 5000);
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
                        productAdapter.setNewData(tempProduces);
                        b3 = true;
                        tryFinish();
                    }

                    @Override
                    public void onFailure(int statusCode, String code, String error_msg) {

                    }
                });
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
            getProList("1");
            isRecommend = false;
        } else {
            getProList("0");
            isRecommend = true;
        }
    }

    @Override
    public void callback(String s) {

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
            if (!p.getMats().isEmpty()) {
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
        total_price.setText(String.format(Locale.CHINA, "总价：%.1f 元", totalPrice));
        cut_number.setText(String.format(Locale.CHINA, "优惠：%.1f 元", totalCut));
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

        addonDialog = null;
        //刷新选择列表数据
        selectedAdapter.setNewData(productions);
        //计算副频金额
        checkPriceForDisplay();

        dataBean.setProId(productsBean.getProId());
        dataBean.setProType(productsBean.getProType());
        dataBeans.add(dataBean);
    }

    /**
     * 挂单
     */
    @SingleClick(500)
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
    @SingleClick(500)
    @OnClick(R.id.button2)
    void DeleteAll() {
        clear();
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
        Log.e("to payment", "goCheckOut: " + PrinterUtil.toJson(orderInfo));
        if (!productions.isEmpty()) {
            i.putParcelableArrayListExtra("proList", productions);
        }
        i.putExtra("totalCount", totalCount);
        i.putExtra("orderTotal", totalPrice);
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //重新登录后
        if (SpUtil.getBoolean("from_Login")) {
            SpUtil.save("from_Login", false);
            workName1.setText(SpUtil.getString(Constant.WORKER_NAME));
            order_num.setText(String.format(Locale.CHINA, "%.1f元", 0.0));
            order_num1.setText(String.format(Locale.CHINA, "%d单", 0));
            resetAllData();
        }
    }

    /**
     * 重置所有数据与UI、刷新或者重新登陆的情况
     */
    private void resetAllData() {
        //过滤
        typeList.clear();
        searchList.clear();
        searchStr = "";
        edit_search.setText("");
        //挂单
        gTemp.clear();
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
        productAdapter.setNewData(tempProduces);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.WORK_BACK_OVER:
                    SpUtil.save(Constant.WORK_P, (float) 0);
                    SpUtil.save(Constant.ORDER_NUM, 0);
                    clear();
                    order_num.setText(String.format(Locale.CHINA, "%.1f元", 0.0));
                    order_num1.setText(String.format(Locale.CHINA, "%d单", 0));
                    ToastUtils.showLong("已完成交班！即将退出登录！");
                    MyOkHttp.mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));

                        }
                    }, 1000);
                    break;
                case Constant.WORK_BACK_DAILY:
                    SpUtil.save(Constant.WORK_P, (float) 0);
                    SpUtil.save(Constant.ORDER_NUM, 0);
                    MyOkHttp.mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.exit(0);
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
        orderInfo.setDiscountsType(SpUtil.getString(Constant.ENT_DIS));
        orderInfo.setData(dataBeans);
        orderInfo.setOrdSource("3");
    }

    /**
     * 结账 dialog 按钮回调
     */
    @Override
    public void OnDialogOkClick(final int type, final double earn, final double cost, final double charge, String name) {
        change = charge;
        earn1 = earn;
        cost1 = cost;
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
                    //线上支付
                    NetTool.postOrder(PrinterUtil.toJson(orderInfo), new GsonResponseHandler<BaseBean<OrderBack>>() {
                        @Override
                        public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                            if (response.getCode().equals("0")) {
                                orderBack = response.getData();
                                orderNo = response.getData().getOrderNo();
                                warnDialog.show();
                                warnDialog.setTextView(totalPrice + "元");
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, String code, String error_msg) {

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
                NetTool.postOrder(PrinterUtil.toJson(orderInfo), new GsonResponseHandler<BaseBean<OrderBack>>() {
                    @Override
                    public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                        orderNo = response.getData().getOrderNo();
                        orderBack = response.getData();
                        PrintOrder(orderBack, charge < 0 ? 0 : charge, "现金支付", totalPrice, totalCut);
                    }

                    @Override
                    public void onFailure(int statusCode, String code, String error_msg) {
                        PrintOrder(orderBack, charge < 0 ? 0 : charge, "现金支付", totalPrice, totalCut);
                    }
                });
                MyOkHttp.mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clear();
                    }
                }, 1000);
                break;
            case "CertainDialog":
                //关机确认
                certainDialog.cancel();
                MyOkHttp.mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.exit(0);
                        PowerUtil.shutdown();
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
                    warnDialog.cancel();
                    if (response.getCode().equals("0")) {
                        PrintOrder(orderBack, change < 0 ? 0 : change, orderInfo.getPayType().equals("010") ? "微信支付" : "支付宝支付", totalPrice, totalCut);
                    } else {
                        ToastUtils.showLong(response.getMsg());
                    }
                }

                @Override
                public void onFailure(int statusCode, String code, String error_msg) {
                    warnDialog.cancel();
                    authNo = "";
                }
            });
        }
    }

    private boolean canScan = false;

    @Override
    public void OnUsbCallBack(String name) {
        authNo = "";
        time = 0;
        //
        canScan = name.equals("1");
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

    //请求USB权限
    protected void getPermission(UsbDevice usbDevice) {
        //请求权限
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        usbManager.requestPermission(usbDevice, mPermissionIntent);
    }

    /**
     * 网页排版
     */
    private void initWebOrder(String OrderNo, String date, String proList, String totalMoney,
                              String cost, String charge, String cut, String payType) {
        String html = H5Order.html;
        html = html.replace("{1}", OrderNo)
                .replace("{2}", SpUtil.getString(Constant.WORKER_NAME))
                .replace("{3}", date)
                .replace("{data}", proList)
                .replace("{4}", totalMoney)
                .replace("{5}", cost)
                .replace("{6}", cut)
                .replace("{7}", charge)
                .replace("{8}", payType)
                .replace("{9}", SpUtil.getString(Constant.STORE_NAME));

        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    /**
     * 打印订单小票，分 连接上服务器 与 未连接上服务器
     * charge:找零
     */
    private void PrintOrder(final OrderBack orderBack, final double charge, String type, double totalPrice, double totalCut) {
        woeker_p += totalPrice;
        orderNum++;
        String no = orderNum < 10 ? "N000" + orderNum : orderNum < 100 ? "N00" + orderNum : orderNum < 1000 ? "N0" + orderNum : "N" + orderNum;

        String proList = PrinterUtil.toJson(productions);

        //小票数据
        if (orderBack != null) {
            String str = orderBack.getTotalPrice().substring(0, orderBack.getTotalPrice().length() - 1);
            initWebOrder("N" + orderBack.getOrderNo().substring(orderBack.getOrderNo().length() - 4), orderBack.getCreatTime(), proList, str,
                    (Double.parseDouble(orderBack.getTotalPrice()) + charge) + "", String.valueOf(charge), String.valueOf(totalCut), type);

//            PrinterUtil.printString80(HomeActivity.this, productions, "C" + orderBack.getOrderNo().substring(orderBack.getOrderNo().length() - 4),
//                    SpUtil.getString(Constant.WORKER_NAME), orderBack.getTotalPrice(), orderBack.getTotalPrice(),
//                    orderBack.getTotalPrice(), String.valueOf(charge),
//                    String.valueOf(totalCut), orderBack.getCreatTime(), type);
        } else {
            initWebOrder(no, PrinterUtil.getDate(), proList, String.valueOf(totalPrice), String.valueOf(totalPrice), String.valueOf(charge), String.valueOf(totalCut), type);

//            PrinterUtil.printString80(HomeActivity.this, productions, no,
//                    SpUtil.getString(Constant.WORKER_NAME), String.valueOf(totalPrice), String.valueOf(totalPrice),
//                    earn1 == 0 ? String.valueOf(totalPrice) : String.valueOf(earn1), String.valueOf(charge),
//                    String.valueOf(totalCut), PrinterUtil.getDate(), type);

            //断网状态下保存订单信息json
            ((MyApplication) getApplication()).getDaoSession().getNativeOrdersDao().insert(new NativeOrders(System.currentTimeMillis(), PrinterUtil.toJson(orderInfo)));
        }
        //订单置空
        orderInfo = null;

        //标签机数据
        printcount = 0;
        printProducts.clear();

        for (int i = 0; i < productions.size(); i++) {
            name = productions.get(i).getProName();
            taste = productions.get(i).getTasteStr();
            count = productions.get(i).getNumber();
            for (int j = 0; j < productions.get(i).getNumber(); j++) {
                Production p = new Production();
                p.setProName(name);
                p.setTasteStr(taste);
                p.setScalePrice(productions.get(i).getScalePrice() + productions.get(i).getMateP());
                p.setNumber(count);
                p.setScaleStr(productions.get(i).getScaleStr());
                p.setMatStr(productions.get(i).getMatStr());
                printProducts.add(p);
                printcount++;
            }
        }
        printLabel();

        //工作信息更新
        order_num.setText(String.format(Locale.CHINA, "%.1f元", woeker_p));
        order_num1.setText(String.format(Locale.CHINA, "%d单", orderNum));
        //存储工作记录
        SpUtil.save(Constant.WORK_P, woeker_p);
        SpUtil.save(Constant.ORDER_NUM, orderNum);

        MyOkHttp.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clear();
            }
        }, 1000);

    }

    /**
     * 打印标签
     */
    private void printLabel() {
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
                            printcount--;
                            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getCurrentPrinterCommand() == PrinterCommand.TSC) {
                                String name = printProducts.get(printcount).getProName();
                                if (name.length() > 6) {
                                    name = name.substring(0, 6);
                                }
                                String mate = printProducts.get(printcount).getMatStr();
                                if (mate.length() > 8) {
                                    mate = mate.substring(0, 8);
                                }
                                sendLabel(name, printProducts.get(printcount).getTasteStr(), printProducts.get(printcount).getMinPrice() + "",
                                        printcount, printProducts.size(), mate, printProducts.get(printcount).getScaleStr());
                            }

                        }
                    }), 1000, TimeUnit.MILLISECONDS);
                }
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetPayBack(WorkBean workBean) {
        //退单回调
        order_num.setText(String.format(Locale.CHINA, "%.1f元", workBean.getPrice()));
        order_num1.setText(String.format(Locale.CHINA, "%d单", workBean.getNum()));
        SpUtil.save(Constant.WORK_P, workBean.getPrice());
        SpUtil.save(Constant.ORDER_NUM, workBean.getNum());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paymentBack(OrderBack orderBack) {
        LogUtil.E(PrinterUtil.toJson(orderBack));
        //结算回调，有网
        PrintOrder(orderBack, orderBack.getCharge(), orderBack.getPayType().equals("900") ? "现金支付" : orderBack.getPayType().equals("010") ? "微信支付" : "支付宝支付", orderBack.getTotal(), orderBack.getCut());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paymentNativeBack(NoNetPayBack noNetPayBack) {
        //结算回调，无网
        LogUtil.E(PrinterUtil.toJson(noNetPayBack));
        PrintOrder(null, noNetPayBack.getCharge(), noNetPayBack.getType(), noNetPayBack.getTotalPrice(), noNetPayBack.getCut());
    }

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
        EventBus.getDefault().unregister(this);
        DeviceConnFactoryManager.closeAllPort();
        if (ThreadPool.getInstantiation() != null) {
            ThreadPool.getInstantiation().stopThreadPool();
        }
        PrinterUtil.disconnectPrinter();
        System.exit(0);
    }

    /**
     * 发送标签
     */
    void sendLabel(String pName, String pContent, String price, int i, int number, String matStr, String scale) {
        i = i + 1;
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
        // 绘制简体中文
        tsc.addText(0, 3, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                SpUtil.getString(Constant.STORE_NAME) + "\n");
        tsc.addText(0, 33, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                pName + "\n");
        if (matStr == null || matStr.isEmpty()) {
            matStr = "不加料";
        }
        tsc.addText(0, 63, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                matStr);
        tsc.addText(0, 93, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                scale + "\n");
        tsc.addText(0, 123, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                pContent + " ￥" + price + " " + "\n");
        tsc.addText(0, 153, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                SpUtil.getString(Constant.WORKER_NAME) + " " + i + "/" + number + "\n");
        tsc.addText(0, 183, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                PrinterUtil.getTabTime() + orderNo.substring(orderNo.length() - 4) + "-" + PrinterUtil.getTabHour() + "\n");
        tsc.addText(0, 213, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                SpUtil.getString(Constant.STORE_ADDRESS));
        // 绘制图片
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.logo9);
        tsc.addBitmap(220, 40, LabelCommand.BITMAP_MODE.OVERWRITE, 80, b);
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

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
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
//                    Log.e(TAG, "onReceive: ACTION_USB_DEVICE_DETACHED");
                    break;
                //Usb连接断开、蓝牙连接广播
                case ACTION_USB_DEVICE_ATTACHED:
//                    Log.e(TAG, "onReceive: ACTION_USB_DEVICE_ATTACHED");
                    final String deviceName = UsbUtil.getBQName(HomeActivity.this);
                    final String xpName = UsbUtil.getXPName(HomeActivity.this);

                    if (!deviceName.isEmpty() || !xpName.isEmpty()) {
                        ld3 = new LoadingDialog(HomeActivity.this);
                        ld3.setLoadingText("正在连接外接设备，请等待");
                        ld3.show();
                        MyOkHttp.mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                connectUsb(deviceName);
//                                PrinterUtil.connectPrinter(HomeActivity.this);
                                try {
                                    Thread.sleep(1000);
                                } catch (Exception e) {
//                                    Log.e("thread", e.getMessage());
                                }
                                ld3.close();
                            }
                        }, 6000);
                    }
                    break;
                case DeviceConnFactoryManager.ACTION_CONN_STATE:
                    int state = intent.getIntExtra(DeviceConnFactoryManager.STATE, -1);
//                    int deviceId = intent.getIntExtra(DeviceConnFactoryManager.DEVICE_ID, -1);
                    switch (state) {
                        case DeviceConnFactoryManager.CONN_STATE_DISCONNECT:
//                            if (id == deviceId) {
//                                ToastUtils.showLong("标签打印机已断开连接");
//                            }
                            break;
                        case DeviceConnFactoryManager.CONN_STATE_CONNECTING:

                            break;
                        case DeviceConnFactoryManager.CONN_STATE_CONNECTED:
//                            ToastUtils.showLong("标签打印机已连接");
                            break;
                        case CONN_STATE_FAILED:
//                            ToastUtils.showLong("标签打印机连接失败");
                            break;
                        default:
                            break;
                    }
                    break;
                case ACTION_QUERY_PRINTER_STATE:
                    if (printcount >= 0) {
                        if (printcount != 0) {
                            printLabel();
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

    @Override
    public void onPageChange(int index) {

    }

    @SingleClick(500)
    @OnClick(R.id.btn_up)
    void btnUp() {
        pageNo--;
        if (pageNo < 0) {
            pageNo = 0;
        }
        scrollHelper.scrollToPosition(pageNo);
    }

    @SingleClick(500)
    @OnClick(R.id.btn_next)
    void btnNext() {
        pageNo++;
        if (pageNo > scrollHelper.getPageCount()) {
            pageNo = scrollHelper.getPageCount();
        }
        scrollHelper.scrollToPosition(pageNo);
    }

    public class JsInterface {
        Bitmap bitmap;

        @JavascriptInterface
        public void save(final String string) {
            ThreadPool.getInstantiation().addTask(new Runnable() {
                @Override
                public void run() {
                    bitmap = stringToBitmap(string);
                    PrinterUtil.printImage(bitmap);
                }
            });
            MyOkHttp.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bitmap.recycle();
                }
            }, 500);
        }
    }

    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
