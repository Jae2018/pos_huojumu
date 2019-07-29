package com.huojumu.main.activity.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.base.BaseDialog;
import com.huojumu.listeners.SingleProCallback;
import com.huojumu.model.MakesBean;
import com.huojumu.model.MatsBean;
import com.huojumu.model.OrderInfo;
import com.huojumu.model.Production;
import com.huojumu.model.ScaleBean;
import com.huojumu.model.TastesBean;
import com.huojumu.utils.GlideApp;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/10/17
 * Description: 单一商品点单设置，数量为1时，口味、做法只能一种，数量大于1时，可以多选；配料选择数量
 * 杯型选择根据单品绑定的scaleID来判断是单还是多
 */
public class SingleProAddonDialog extends BaseDialog {

    @BindView(R.id.id_flowlayout1)
    TagFlowLayout flowLayout1;
    @BindView(R.id.id_flowlayout2)
    TagFlowLayout flowLayout2;
    @BindView(R.id.id_flowlayout3)
    TagFlowLayout flowLayout3;
    //    @BindView(R.id.id_flowlayout4)
//    TagFlowLayout flowLayout4;
    @BindView(R.id.textView3)
    TextView t3;
    @BindView(R.id.textView5)
    TextView t4;
    @BindView(R.id.textView16)
    TextView t5;
    //    @BindView(R.id.textView17)
//    TextView t6;
    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.tv_home_addon_number)
    TextView numTV;//数量
//    @BindView(R.id.et_addon)
//    EditText addOnET;//备注

    @BindView(R.id.linear_keyboard)
    LinearLayout linear_keyboard;

    @BindView(R.id.tv_product_name)
    TextView name;

    private Production productsBean, current;//单品

    private List<TastesBean> tastesBeans = new ArrayList<>();//口味
    private List<MatsBean> matsBeans = new ArrayList<>();//加料
    private List<MakesBean> makesBeans = new ArrayList<>();//做法
    private List<ScaleBean> scaleBeans = new ArrayList<>();//规格

    private SingleProCallback callback;//dialog回调

    private TastesBean tastesBean;
    private MatsBean matsBean;
    private ScaleBean scaleBean;
    private int p = -1;
    private int number = 1;

    public SingleProAddonDialog(@NonNull Context context, SingleProCallback callback,
                                Production productsBean) {
        super(context);
        this.productsBean = productsBean;
        this.callback = callback;
    }

    @Override
    public int setLayout() {
        return R.layout.dialog_for_home_detail;
    }

    @Override
    public void initView() {
        current = new Production();
        name.setText(productsBean.getProName());

        //显示商品图片
        if (productsBean.getImgs().size() > 0) {
            GlideApp.with(getContext()).load(productsBean.getImgs().get(0).getPath()).into(image);
        }
        matsBeans.clear();

        final LayoutInflater mInflater = LayoutInflater.from(getContext());

        //规格
        if (productsBean.getScales() == null || productsBean.getScales().isEmpty()) {
            t3.setVisibility(View.GONE);
            flowLayout1.setVisibility(View.GONE);
        } else {
            TagAdapter makesAdapter = new TagAdapter<ScaleBean>(productsBean.getScales()) {
                @Override
                public View getView(FlowLayout parent, int position, ScaleBean o) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv,
                            flowLayout1, false);
                    tv.setText(String.format("%s  ￥ %s", o.getScaName(), o.getPrice()));
                    return tv;
                }
            };
            makesAdapter.setSelectedList(0);
            scaleBean = productsBean.getScales().get(0);
            flowLayout1.setMaxSelectCount(1);

            flowLayout1.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    scaleBean = productsBean.getScales().get(position);
                    return true;
                }
            });
            flowLayout1.setAdapter(makesAdapter);
        }

        //口味
        if (productsBean.getTastes() == null || productsBean.getTastes().isEmpty()) {
            t4.setVisibility(View.GONE);
            flowLayout2.setVisibility(View.GONE);
        } else {
            TagAdapter tastesAdapter = new TagAdapter<TastesBean>(productsBean.getTastes()) {
                @Override
                public View getView(FlowLayout parent, int position, TastesBean o) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv,
                            flowLayout2, false);
                    tv.setText(o.getTasteName());
                    return tv;
                }

                @Override
                public void onSelected(int position, View view) {
                    super.onSelected(position, view);

                }
            };
            tastesAdapter.setSelectedList(0);
            tastesBean = productsBean.getTastes().get(0);
            flowLayout2.setMaxSelectCount(1);
            flowLayout2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    tastesBean = productsBean.getTastes().get(position);
                    return true;
                }
            });
            flowLayout2.setAdapter(tastesAdapter);
        }

        //加料
        if (productsBean.getMats() == null || productsBean.getMats().isEmpty()) {
            t5.setVisibility(View.GONE);
            flowLayout3.setVisibility(View.GONE);
        } else {
            flowLayout3.setAdapter(new TagAdapter<MatsBean>(productsBean.getMats()) {
                @Override
                public View getView(FlowLayout parent, int position, MatsBean o) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv,
                            flowLayout3, false);
                    tv.setGravity(Gravity.CENTER);
                    tv.setText(String.format("%s  ￥ %s", o.getMatName(), o.getIngredientPrice()));
                    return tv;
                }
            });

            flowLayout3.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    if (p != position) {
                        p = position;
                        matsBean = productsBean.getMats().get(position);
                        matsBeans.add(matsBean);
                    } else {
                        matsBeans.remove(matsBean);
                        p = -1;
                    }
                    return false;
                }
            });
        }
    }

    @OnClick(R.id.add)
    void add() {
        number--;
        if (number < 1) {
            number = 1;
        }
        numTV.setText(String.valueOf(number));
    }

    @OnClick(R.id.sub)
    void sub() {
        number++;
        if (number > 999) {
            number = 999;
        }
        numTV.setText(String.valueOf(number));
    }

    @OnClick(R.id.btn_home_addon_cancel)
    void Cancel() {
        callback.onCancelCallBack();
        cancel();
    }

    @OnClick(R.id.btn_home_addon_ok)
    void Ok() {
        double price = 0, origionPrice = 0;
        scaleBeans.clear();
        tastesBeans.clear();

        if (numTV.getText().length() > 0) {
            number = Integer.parseInt(numTV.getText().toString().isEmpty() ? "1" : numTV.getText().toString());
        }

        if (scaleBean != null) {
            price = scaleBean.getPrice();
            origionPrice = scaleBean.getOrigionPrice();
            scaleBeans.add(scaleBean);
            current.setScaleStr(scaleBean.getScaName());
        }

        if (tastesBean != null) {
            tastesBeans.add(tastesBean);
        }

        String matStr = "";
        double matP = 0;
        for (int i = 0; i < matsBeans.size(); i++) {
            matStr += matsBeans.get(i).getMatName() + " ";
            matP += matsBeans.get(i).getIngredientPrice();
        }

        current.setProId(productsBean.getProId());
        current.setProType(productsBean.getProType());
        current.setProName(productsBean.getProName());
        current.setProNameEn(productsBean.getProNameEn());
        current.setAddon("");//备注
        current.setTasteStr(tastesBean != null ? tastesBean.getTasteName() : "默认口味");
        current.setMatStr(matStr);
        current.setMateP(matP);
        current.setMats(matsBeans);
        current.setTastes(tastesBeans);

        OrderInfo.DataBean dataBean = new OrderInfo.DataBean();
        dataBean.setProType(productsBean.getProType());
        dataBean.setProId(productsBean.getProId());
        dataBean.setMakes(makesBeans);
        dataBean.setMats(matsBeans);
        dataBean.setTastes(tastesBeans);
        dataBean.setScales(scaleBeans);


        callback.onSingleCallBack(productsBean.getProId(), number, current, dataBean, origionPrice, price);

        current = null;
    }

    private String cNumber = "1";

    @OnClick({R.id.tv_no0, R.id.tv_no1, R.id.tv_no2, R.id.tv_no3, R.id.tv_no4, R.id.tv_no5, R.id.tv_no6, R.id.tv_no7, R.id.tv_no8, R.id.tv_no9, R.id.tv_no_delete, R.id.tv_no_ok})
    void onNoClick(View view) {
        switch (view.getId()) {
            case R.id.tv_no0:
                if (cNumber.isEmpty()) {
                    cNumber = "1";
                    return;
                }
                cNumber += "0";
                break;
            case R.id.tv_no1:
                cNumber += "1";
                break;
            case R.id.tv_no2:
                cNumber += "2";
                break;
            case R.id.tv_no3:
                cNumber += "3";
                break;
            case R.id.tv_no4:
                cNumber += "4";
                break;
            case R.id.tv_no5:
                cNumber += "5";
                break;
            case R.id.tv_no6:
                cNumber += "6";
                break;
            case R.id.tv_no7:
                cNumber += "7";
                break;
            case R.id.tv_no8:
                cNumber += "8";
                break;
            case R.id.tv_no9:
                cNumber += "9";
                break;
            case R.id.tv_no_delete:
                if (cNumber.length() > 1) {
                    cNumber = cNumber.substring(0, cNumber.length() - 1);
                } else {
                    cNumber = "";
                }
                break;
            case R.id.tv_no_ok:
                cNumber = "1";
                break;
        }
        if (cNumber.length() > 3) {
            cNumber = "999";
        }

        numTV.setText(cNumber);
    }

}
