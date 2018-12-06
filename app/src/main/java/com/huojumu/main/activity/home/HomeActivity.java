package com.huojumu.main.activity.home;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.data.DetailDao;
import com.data.OrderDao;
import com.data.OrderDetail;
import com.data.OrderSave;
import com.huojumu.MyApplication;
import com.huojumu.R;
import com.huojumu.adapter.HomeProductAdapter;
import com.huojumu.adapter.HomeSelectedAdapter;
import com.huojumu.adapter.HomeTypeAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.dialog.SingleProAddonDialog;
import com.huojumu.main.activity.function.PaymentActivity;
import com.huojumu.main.activity.work.DailyTakeOverActivity;
import com.huojumu.main.dialogs.CashPayDialog;
import com.huojumu.main.dialogs.CertainDialog;
import com.huojumu.main.dialogs.DialogInterface;
import com.huojumu.main.dialogs.MoreFunctionDialog;
import com.huojumu.main.dialogs.QuickPayDialog;
import com.huojumu.main.dialogs.SingleProCallback;
import com.huojumu.model.BaseBean;
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
import com.huojumu.utils.SocketTool;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;


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
    ImageView hasGua;

    private HomeSelectedAdapter selectedAdapter;//所选
    private HomeTypeAdapter typeAdapter;//类别
    private HomeProductAdapter productAdapter;//商品

    private List<Products.ProductsBean> tempProduces;
    private List<Products.ScalesBean> scalesBeans;//杯子
    private List<Products.TastesBean> tastesBeans;//口味
    private SparseArray<List<Products.ProductsBean>> map = new SparseArray<>();//分类切换
    private ArrayList<Products.ProductsBean> productions = new ArrayList<>();//选择的奶茶
    List<OrderInfo.DataBean.TastesBean> list = new ArrayList<>();//口味
    private double totalPrice = 0;//订单总价
    private int totalCount = 0;//订单所有单品数量

    private List<Products.ProductsBean> gTemp = new ArrayList<>();//挂单
    private boolean hasHoldOn = false;//是否已有挂单

    private QuickPayDialog quickPayDialog;//快捷支付弹窗
    private CashPayDialog cashPayDialog;//现金支付弹窗
    private SingleProAddonDialog addonDialog;//单品设置弹窗
    private SingleProAddonDialog addonDialog2;//单品修改弹窗
    private MoreFunctionDialog functionDialog;//功能弹窗
    private CertainDialog certainDialog;//关机

    //数据库引用
    private OrderDao orderDao;
    private DetailDao detailDao;
//    SocketTool socketTool;
    OrderInfo orderInfo;
    OkHttpClient client;
    Request request;

    @Override
    protected int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        orderDao = MyApplication.getDb().getOrderDao();
        detailDao = MyApplication.getDb().getDetailDao();
        //左侧点单列表
        selectedAdapter = new HomeSelectedAdapter(productions);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        left.setLayoutManager(linearLayoutManager);
        left.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        selectedAdapter.enableSwipeItem();
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
                engine.refresh(productions);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
        selectedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //显示单品附加信息
                addonDialog2 = new SingleProAddonDialog(HomeActivity.this, HomeActivity.this, scalesBeans, tastesBeans, productions.get(position), true);
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
        productAdapter = new HomeProductAdapter(tempProduces);
        GridLayoutManager grid2 = new GridLayoutManager(this, 5);
        rBottom.setLayoutManager(grid2);
        rBottom.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点菜逻辑
                addonDialog = new SingleProAddonDialog(HomeActivity.this, HomeActivity.this, scalesBeans, tastesBeans, tempProduces.get(position), false);
                addonDialog.show();
            }
        });

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

        //商品
        NetTool.getStoreProduces(SpUtil.getInt(Constant.STORE_ID), SpUtil.getInt(Constant.ENT_ID),
                SpUtil.getInt(Constant.PINPAI_ID), new GsonResponseHandler<BaseBean<Products>>() {
                    //        NetTool.getStoreProduces(1, 1, 1, new GsonResponseHandler<BaseBean<Products>>() {
                    @Override
                    public void onSuccess(int statusCode, BaseBean<Products> response) {
                        tempProduces = response.getData().getProducts();
                        scalesBeans = response.getData().getScales();
                        tastesBeans = response.getData().getTastes();
                        productAdapter.setNewData(response.getData().getProducts());
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                    }
                });
        request = new Request.Builder()
                .url(Constant.SOCKET)
                .build();
        client = new OkHttpClient();

    }

    @Override
    public void callback(String s) {

    }

    @Override
    public void sendMsg(String s) {
        if ("0".equals(s)) {
            //付款完成
            PrintOrder();
            productions.clear();
            selectedAdapter.setNewData(productions);
        }
    }

    /**
     * 单品详情dialog配置回调
     */
    @Override
    public void onSingleCallBack(int scaleId, int value, Products.ProductsBean productsBean, OrderInfo.DataBean.TastesBean tastesBean, OrderInfo.DataBean dataBean) {
        //加入已选清单中
        productions.add(productsBean);
        addonDialog = null;
        addonDialog2 = null;
        //刷新选择列表数据
        selectedAdapter.setNewData(productions);
        //计算金额
        double totalPrice = 0.0, totalCut = 0.0;
        int totalCount = 0;
        for (Products.ProductsBean p : productions) {
            totalPrice += p.getPrice() * p.getNumber();
            totalCount += p.getNumber();
            totalCut += (p.getOrigionPrice() - p.getPrice()) * p.getNumber();
        }
        total_number.setText(String.format("数量：%s 杯", totalCount));
        total_price.setText(String.format("总价：%s 元", totalPrice));
        cut_number.setText(String.format("优惠：%s 元", totalCut));
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
        //副屏刷新
        if (engine != null) {
            engine.refresh(productions);
            engine.setPrice(totalPrice, totalCut);
        }

        orderInfo = new OrderInfo();
        orderInfo.setOrderID(UUID.randomUUID().toString().replace("-", ""));
        orderInfo.setShopID(SpUtil.getInt(Constant.STORE_ID));
        orderInfo.setCreateTime(PrinterUtil.getDate());
        orderInfo.setEnterpriseID(SpUtil.getInt(Constant.ENT_ID));
        orderInfo.setPinpaiID(SpUtil.getInt(Constant.PINPAI_ID));
        orderInfo.setQuanIds(new ArrayList<Integer>());
        List<OrderInfo.DataBean> dataBeans = new ArrayList<>();
        list.add(tastesBean);
        dataBean.setTastes(list);
        dataBeans.add(dataBean);
        orderInfo.setData(dataBeans);

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
    @OnClick({R.id.btn_home_daily, R.id.btn_home_hand_over})
    void Daily(View view) {
        Intent intent = new Intent(HomeActivity.this, DailyTakeOverActivity.class);
        switch (view.getId()) {
            case R.id.btn_home_daily:
                intent.putExtra(Constant.TYPE, 1);
                break;
            case R.id.btn_home_hand_over:
                intent.putExtra(Constant.TYPE, 2);
                break;
        }
        startActivity(intent);
    }


    /**
     * 结账
     */
    @OnClick(R.id.btn_home_check_out)
    void goCheckOut() {
        Intent i = new Intent(HomeActivity.this, PaymentActivity.class);
        if (orderInfo != null) {
            i.putExtra("orderJson", PrinterUtil.toJson(orderInfo));
        }
        i.putExtra("orderTotal", totalPrice);
        startActivity(i);
    }

    /**
     * dialog 按钮回调
     */
    @Override
    public void OnDialogOkClick(final double value, String name) {
        switch (name) {
            case "QuickPayDialog":
                quickPayDialog.dismiss();
                if (1 == value) {
                    if (cashPayDialog == null) {
                        cashPayDialog = new CashPayDialog(this, totalPrice, this);
                    }
                    cashPayDialog.show();
                } else {
                    //线上支付
                    NetTool.postOrder(PrinterUtil.toJson(orderInfo), new GsonResponseHandler<BaseBean<OrderBack>>() {
                        @Override
                        public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                            if (response.getData().getAliPayQrcode() != null) {
                                engine.getAliIV().setImageBitmap(QrUtil.createQRCodeWithLogo(HomeActivity.this, response.getData().getAliPayQrcode(), BitmapFactory.decodeResource(getResources(), R.drawable.zhifubao_normal)));//
                                engine.getWxIV().setImageBitmap(QrUtil.createQRCodeWithLogo(HomeActivity.this, response.getData().getWxPayQrcode(), BitmapFactory.decodeResource(getResources(), R.drawable.weixin_normal)));//
                            }
                            pickUpCode = response.getData().getPickUpCode();
                            client.newWebSocket(request, new SocketTool(HomeActivity.this, String.format(Constant.PMENT, response.getData().getOrderId(), SpUtil.getString(Constant.TOKEN))));
                            client.dispatcher().executorService().shutdown();
                            saveOrder(response.getData(), 2);
                            saveOrder2();
                            productions.clear();
                            list.clear();
                            orderInfo = null;
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
                cashPayDialog.cancel();
                cashPayDialog = null;

                NetTool.postOrder(PrinterUtil.toJson(orderInfo), new GsonResponseHandler<BaseBean<OrderBack>>() {
                    @Override
                    public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                        pickUpCode = response.getData().getPickUpCode();
                        PrintOrder();
                        productions.clear();
                        list.clear();
                        selectedAdapter.setNewData(null);
                        saveOrder(response.getData(), 1);
                        saveOrder2();
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

    private void saveOrder(OrderBack p, int type) {
        //新插入
        OrderSave orderSave = new OrderSave();
        orderSave.setOrderNo(p.getOrderId());
        orderSave.setPrice(p.getTotalPrice());
        if (type == 1) {
            orderSave.setEarn1(p.getTotalPrice());
        } else {
            orderSave.setEarn2(p.getTotalPrice());
        }
        orderSave.setTime(orderInfo.getCreateTime());
        orderDao.save(orderSave);
    }

    private void saveOrder2() {
        for (Products.ProductsBean p: productions) {
            OrderDetail detail = new OrderDetail();
            detail.setProName(p.getProName());
            detail.setNumber(p.getNumber());
            detail.setSell(p.getPrice());
            if (detailDao.getSingleOrder(p.getProName()) == null) {
                detailDao.save(detail);
            } else {
                detailDao.updateOrder(detail);
            }
        }
    }


    /**
     * 口味、备注回调
     */
    @Override
    public void OnTasteClick(int type, int id, int number, String name) {

    }

    @Override
    public void OnDialogCancelClick(int value) {

    }

    String pickUpCode;

    /**
     * 打印订单小票、本地生成 "订单号" 与 "取货码"
     */
    private void PrintOrder() {
        PrinterUtil.OpenMoneyBox();
        PrinterUtil.printString(productions, totalPrice, totalCount, pickUpCode, orderInfo.getOrderID(), orderInfo.getCreateTime());
        total_number.setText("");
        total_price.setText("");
        cut_number.setText("");
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
    void getPayCallBack(BaseBean<OrderBack> response) {
        if (response.getData().getAliPayQrcode() != null) {
            engine.getAliIV().setImageBitmap(QrUtil.createQRCodeWithLogo(HomeActivity.this, response.getData().getAliPayQrcode(), BitmapFactory.decodeResource(getResources(), R.drawable.zhifubao_normal)));//
        }
        if (response.getData().getWxPayQrcode() != null) {
            engine.getWxIV().setImageBitmap(QrUtil.createQRCodeWithLogo(HomeActivity.this, response.getData().getWxPayQrcode(), BitmapFactory.decodeResource(getResources(), R.drawable.weixin_normal)));//
        }

        pickUpCode = response.getData().getPickUpCode();
        client.newWebSocket(request, new SocketTool(HomeActivity.this, String.format(Constant.PMENT, response.getData().getOrderId(), SpUtil.getString(Constant.TOKEN))));
        client.dispatcher().executorService().shutdown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
