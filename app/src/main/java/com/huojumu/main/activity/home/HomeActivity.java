package com.huojumu.main.activity.home;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Handler;
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
import com.huojumu.R;
import com.huojumu.adapter.HomeProductAdapter;
import com.huojumu.adapter.HomeSelectedAdapter;
import com.huojumu.adapter.HomeTypeAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.dialog.SingleProAddonDialog;
import com.huojumu.main.activity.work.DailyTakeOverActivity;
import com.huojumu.main.dialogs.CashPayDialog;
import com.huojumu.main.dialogs.CertainDialog;
import com.huojumu.main.dialogs.DialogInterface;
import com.huojumu.main.dialogs.MoreFunctionDialog;
import com.huojumu.main.dialogs.QuickPayDialog;
import com.huojumu.main.dialogs.SingleProCallback;
import com.huojumu.model.ActiveBean;
import com.huojumu.model.BaseBean;
import com.huojumu.model.EventHandler;
import com.huojumu.model.OrderBack;
import com.huojumu.model.OrderInfo;
import com.huojumu.model.Products;
import com.huojumu.model.SmallType;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PowerUtil;
import com.huojumu.utils.PrinterUtil;
import com.huojumu.utils.QrUtil;
import com.huojumu.utils.SocketBack;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity implements DialogInterface, SocketBack,
        SingleProCallback, MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {

    @BindView(R.id.recyclerView)
    RecyclerView left;
    @BindView(R.id.recyclerView2)
    RecyclerView rTop;
    @BindView(R.id.recyclerView3)
    RecyclerView rBottom;
    //textViews
    @BindView(R.id.total_number)
    TextView total_number;
    @BindView(R.id.cut_number)
    TextView cut_number;
    @BindView(R.id.total_price)
    TextView total_price;
    @BindView(R.id.iv_has_gua_dan)
    ImageView hasGua;//挂单

    private HomeSelectedAdapter selectedAdapter;//所选
    private HomeTypeAdapter typeAdapter;//类别
    private HomeProductAdapter productAdapter;//商品

    private List<Products.ProductsBean> tempProduces;//商品列表
    private SparseArray<List<Products.ProductsBean>> map = new SparseArray<>();//分类切换
    private ArrayList<Products.ProductsBean> productions = new ArrayList<>();//选择的奶茶
    private double totalPrice = 0;//订单总价

    private List<Products.ProductsBean> gTemp = new ArrayList<>();//挂单
    private boolean hasHoldOn = false;//是否已有挂单

    private QuickPayDialog quickPayDialog;//快捷支付弹窗
    private CashPayDialog cashPayDialog;//现金支付弹窗
    private SingleProAddonDialog addonDialog;//单品设置弹窗
    private SingleProAddonDialog addonDialog2;//单品修改弹窗
    private MoreFunctionDialog functionDialog;//功能弹窗
    private CertainDialog certainDialog;//关机

    //订单数据
    OrderInfo orderInfo;
    private Handler handler = new Handler();
    //是否修改
    private boolean ok = false;
    private LoadingDialog ld;

    @Override
    protected int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        //左侧点单列表
        selectedAdapter = new HomeSelectedAdapter(productions);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        left.setLayoutManager(linearLayoutManager);
        left.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
                        List<Products.ProductsBean> temp = new ArrayList<>();
                        for (Products.ProductsBean p : tempProduces) {
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
//                .setInterceptBack(intercept_back_event)
//                .setLoadSpeed(speed)
//                .setRepeatCount(repeatTime)
//                .setDrawColor(color)
//                .show();
    }

    @OnClick(R.id.button5)
    void refresh() {
        getTypeList();
        getProList();
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
        getProList();
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
    private void getProList() {
        NetTool.getStoreProduces(SpUtil.getInt(Constant.STORE_ID), SpUtil.getInt(Constant.ENT_ID), SpUtil.getInt(Constant.PINPAI_ID),
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
        NetTool.getActiveInfo(SpUtil.getInt(Constant.STORE_ID), SpUtil.getInt(Constant.ENT_ID), SpUtil.getInt(Constant.PINPAI_ID), new GsonResponseHandler<BaseBean<List<ActiveBean>>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<List<ActiveBean>> response) {

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }

    @Override
    public void callback(String s) {

    }

    @Override
    public void sendMsg(String s) {
        if ("0".equals(s)) {
            //线上付款完成
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    productions.clear();
                }
            }, 2000);
            selectedAdapter.setNewData(productions);
        }
    }

    private void checkPriceForDisplay() {
        double totalPrice = 0.0, totalCut = 0.0;
        int totalCount = 0;
        for (Products.ProductsBean p : productions) {
            totalCount += p.getNumber();
            if (p.getIsBargain().equals("1")) {
                totalPrice += p.getPrice() * p.getNumber();
                totalCut += (p.getOrigionPrice() - p.getPrice()) * p.getNumber();
            } else {
                if (p.getIsPresented().equals("1")) {
                    totalPrice += p.getOrigionPrice() * (p.getNumber() > 1 ? p.getNumber() - 1 : 1);
                    totalCut += p.getOrigionPrice() * (p.getNumber() > 1 ? 1 : 0);
                } else {
                    totalPrice += p.getOrigionPrice() * p.getNumber();
                    totalCut += 0.0;
                }
            }


        }
        total_number.setText(String.format("数量：%s 份", totalCount));
        total_price.setText(String.format("总价：%.2s 元", totalPrice));
        cut_number.setText(String.format("优惠：%.2s 元", totalCut));
        this.totalPrice = totalPrice;
        //副屏刷新
        if (engine != null) {
            engine.refresh(productions);
            engine.setPrice(totalPrice, totalCut);
        }
    }

    /**
     * 单品详情dialog配置回调
     */
    List<OrderInfo.DataBean> dataBeans = new ArrayList<>();
    int n = 0;

    @Override
    public void onSingleCallBack(int proId, int number, Products.ProductsBean productsBean, OrderInfo.DataBean dataBean, int position) {
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
                        break;
                    }
                }
            if (!isAdd) {
                dataBean.setNum(number);
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
        orderInfo.setOrderID(PrinterUtil.getOrderNo());
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
            Toast.makeText(this, "已有挂已单！", Toast.LENGTH_LONG).show();
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
        //清空当前选择list
        productions.clear();
        //刷新列表
        selectedAdapter.setNewData(productions);
        engine.clear();
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
     * 日结、交班
     */
    @OnClick(R.id.btn_home_hand_over)
    void Daily() {
        Intent intent = new Intent(HomeActivity.this, DailyTakeOverActivity.class);
        startActivity(intent);
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

    String TAG = "home";
    private OrderBack orderBack;
    private double change;

    /**
     * 结账 dialog 按钮回调
     */
    @Override
    public void OnDialogOkClick(final int type, final double earn, final double cost, final double charge, String name) {
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
                    Log.e(TAG, "OnDialogOkClick: " + PrinterUtil.toJson(orderInfo));
                    //线上支付
                    NetTool.postOrder(PrinterUtil.toJson(orderInfo), new GsonResponseHandler<BaseBean<OrderBack>>() {
                        @Override
                        public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                            if (type == 2) {
                                engine.getAliIV().setImageBitmap(QrUtil.createQRCodeWithLogo(HomeActivity.this, response.getData().getAliPayQrcode(), BitmapFactory.decodeResource(getResources(), R.drawable.zhifubao_normal)));
                            } else if (type == 3) {
                                engine.getWxIV().setImageBitmap(QrUtil.createQRCodeWithLogo(HomeActivity.this, response.getData().getWxPayQrcode(), BitmapFactory.decodeResource(getResources(), R.drawable.weixin_normal)));
                            }

                            socketTool.sendMsg("{\"task\": \"pay\",\"data\":{\"orderCode\":\"" + response.getData().getOrderNo() + "\",\"payTime\":\"" + orderInfo.getCreateTime() + "\",\"state\": \"1\",\"leftCupCnt\":1}}");
                            ld.show();
                        }

                        @Override
                        public void onFailure(int statusCode, String error_msg) {

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
                NetTool.postOrder(PrinterUtil.toJson(orderInfo), new GsonResponseHandler<BaseBean<OrderBack>>() {
                    @Override
                    public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                        PrintOrder(response.getData(), charge);
                        selectedAdapter.setNewData(null);
                        productions.clear();
                        dataBeans.clear();
                        orderInfo = null;
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                    }
                });
                break;
            case "CertainDialog":
                //关机确认
                certainDialog.cancel();
                PowerUtil.shutdown();
                break;
        }
    }

    //是否是现金支付
    boolean isCash = false;

    /**
     * 打印订单小票
     */
    private void PrintOrder(OrderBack orderBack, double charge) {
        if (isCash) {
            PrinterUtil.OpenMoneyBox();
            isCash = false;
        }
        PrinterUtil.printString80(this,productions, orderBack.getOrderNo(), SpUtil.getString(Constant.WORKER_NAME), orderBack.getTotalPrice(), orderBack.getTotalPrice(), "" + (Double.parseDouble(orderBack.getTotalPrice()) + charge), charge + "");

        total_number.setText("数量：");
        total_price.setText("总价：");
        cut_number.setText("优惠：");
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
//        mMediaPlayer.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        try {
//            mMediaPlayer.setDataSource("/Movies/mv.wmv");
//            mMediaPlayer.setDisplay(mSurfaceView.getHolder());
//            mMediaPlayer.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetPayBack(EventHandler eventHandler) {
        //socket支付回调
        Log.e(TAG, "GetPayBack: ");
        if (eventHandler.getType() == 1) {//用户支付完成
            ld.loadSuccess();
            PrintOrder(orderBack, change);
            selectedAdapter.setNewData(null);
            productions.clear();
            dataBeans.clear();
            orderInfo = null;
//            PrintOrder(response.getData(), totalPrice - Double.parseDouble(orderBack.getTotalPrice()));
        } else {
            ld.loadFailed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
