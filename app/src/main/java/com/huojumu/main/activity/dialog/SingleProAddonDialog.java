package com.huojumu.main.activity.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.huojumu.R;
import com.huojumu.base.BaseDialog;
import com.huojumu.main.dialogs.SingleProCallback;
import com.huojumu.model.BaseBean;
import com.huojumu.model.MakesBean;
import com.huojumu.model.MatsBean;
import com.huojumu.model.OrderInfo;
import com.huojumu.model.Production;
import com.huojumu.model.ScaleBean;
import com.huojumu.model.Specification;
import com.huojumu.model.TastesBean;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PrinterUtil;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
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
    @BindView(R.id.id_flowlayout4)
    TagFlowLayout flowLayout4;
    @BindView(R.id.textView3)
    TextView t3;
    @BindView(R.id.textView5)
    TextView t4;
    @BindView(R.id.textView16)
    TextView t5;
    @BindView(R.id.textView17)
    TextView t6;

    @BindView(R.id.tv_home_addon_number)
    EditText numTV;//数量
    @BindView(R.id.et_addon)
    EditText addOnET;//备注

    private int proId;//商品ID
    private int number = 1;//数量

    private Production productsBean;//单品

    private List<TastesBean> tastesBeans = new ArrayList<>();//口味
    private List<MatsBean> matsBeans = new ArrayList<>();//加料
    private List<MakesBean> makesBeans = new ArrayList<>();//做法
    private List<ScaleBean> scaleBeans = new ArrayList<>();//规格

    private SingleProCallback callback;//dialog回调
    private int position;
    private boolean isChange;

    private TastesBean tastesBean;
    private MatsBean matsBean;
    private MakesBean makesBean;
    private ScaleBean scaleBean;

    private int p = -1;

    public SingleProAddonDialog(@NonNull Context context, SingleProCallback callback,
                                Production productsBean,
                                int proId,
                                boolean isChange, int pos) {
        super(context);
        this.productsBean = productsBean;
        this.callback = callback;
        this.proId = proId;
        this.isChange = isChange;
        this.position = pos;
    }

    @Override
    public int setLayout() {
        return R.layout.dialog_for_home_detail;
    }

    @Override
    public void initView() {
        matsBeans.clear();
        final LayoutInflater mInflater = LayoutInflater.from(getContext());
        NetTool.getSpecification(SpUtil.getInt(Constant.PINPAI_ID), proId, new GsonResponseHandler<BaseBean<Specification>>() {
            @Override
            public void onSuccess(int statusCode,final BaseBean<Specification> response) {
                //规格
                if (response.getData().getScales() == null || response.getData().getScales().isEmpty()) {
                    t3.setVisibility(View.GONE);
                    flowLayout1.setVisibility(View.GONE);
                } else {
                    TagAdapter makesAdapter = new TagAdapter<ScaleBean>(response.getData().getScales()) {
                        @Override
                        public View getView(FlowLayout parent, int position, ScaleBean o) {
                            TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv,
                                    flowLayout1, false);
                            tv.setText(o.getScaName());
                            return tv;
                        }
                    };
                    makesAdapter.setSelectedList(0);
                    scaleBean = response.getData().getScales().get(0);
                    flowLayout1.setMaxSelectCount(1);

                    flowLayout1.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            scaleBean = response.getData().getScales().get(position);
                            return true;
                        }
                    });
                    flowLayout1.setAdapter(makesAdapter);
                }

                //口味
                if (response.getData().getTastes() == null || response.getData().getTastes().isEmpty()) {
                    t4.setVisibility(View.GONE);
                    flowLayout2.setVisibility(View.GONE);
                }else {
                    TagAdapter tastesAdapter = new TagAdapter<TastesBean>(response.getData().getTastes()) {
                        @Override
                        public View getView(FlowLayout parent, int position, TastesBean o) {
                            TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv,
                                    flowLayout1, false);
                            tv.setText(o.getTasteName());
                            return tv;
                        }

                        @Override
                        public void onSelected(int position, View view) {
                            super.onSelected(position, view);

                        }
                    };
                    tastesAdapter.setSelectedList(0);
                    tastesBean = response.getData().getTastes().get(0);
                    flowLayout2.setMaxSelectCount(1);
                    flowLayout2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            tastesBean = response.getData().getTastes().get(position);
                            return true;
                        }
                    });
                    flowLayout2.setAdapter(tastesAdapter);
                }

                //加料
                if (response.getData().getMats() == null || response.getData().getMats().isEmpty()) {
                    t5.setVisibility(View.GONE);
                    flowLayout3.setVisibility(View.GONE);
                } else {
                    flowLayout3.setAdapter(new TagAdapter<MatsBean>(response.getData().getMats()) {
                        @Override
                        public View getView(FlowLayout parent, int position, MatsBean o) {
                            TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv,
                                    flowLayout1, false);
                            tv.setGravity(Gravity.CENTER);
                            tv.setText(String.format("%s\n%s",o.getMatName(),o.getIngredientPrice()));
                            return tv;
                        }
                    });

                    flowLayout3.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            if (p != position) {
                                p = position;
                                matsBean = response.getData().getMats().get(position);
                                matsBeans.add(matsBean);
                            } else {
                                matsBeans.remove(matsBean);
                                p = -1;
                            }
                            return false;
                        }
                    });

                }

                //做法
                if (response.getData().getMakes() == null || response.getData().getMakes().isEmpty()) {
                    t6.setVisibility(View.GONE);
                    flowLayout4.setVisibility(View.GONE);
                } else {
                    TagAdapter makeAdapter = new TagAdapter<MakesBean>(response.getData().getMakes()) {
                        @Override
                        public View getView(FlowLayout parent, int position, MakesBean o) {
                            TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv,
                                    flowLayout1, false);
                            tv.setText(o.getPracticeName());
                            return tv;
                        }
                    };
                    flowLayout4.setAdapter(makeAdapter);
                    flowLayout4.setMaxSelectCount(1);
                    makeAdapter.setSelectedList(0);
                    makesBean = response.getData().getMakes().get(0);

                    flowLayout4.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            makesBean = response.getData().getMakes().get(position);
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });

        if (isChange) {
            number = productsBean.getNumber();
            numTV.setText(String.valueOf(number));
            addOnET.setText(productsBean.getAddon());
        } else {
            productsBean.getMats().clear();
            productsBean.getTastes().clear();
            productsBean.getMakes().clear();
        }

        numTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.btn_home_addon_cancel)
    void Cancel() {
        dismiss();
    }

    @OnClick(R.id.btn_home_addon_ok)
    void Ok() {
        if (numTV.getText().toString().isEmpty()) {
            number = 1;
        } else {
            number = Integer.parseInt(numTV.getText().toString().trim());
        }
        productsBean.setAddon(addOnET.getText().toString());//备注
        productsBean.setTasteStr(tastesBean != null ? tastesBean.getTasteName() : "");
        OrderInfo.DataBean dataBean = new OrderInfo.DataBean();
        dataBean.setProType(productsBean.getProType());
        dataBean.setProId(productsBean.getProId());

        if (makesBean != null) {
            makesBeans.clear();
            makesBeans.add(makesBean);
        }

        if (scaleBean != null) {
            scaleBeans.clear();
            scaleBeans.add(scaleBean);
            productsBean.setScaleStr(scaleBean.getScaName());
        }

        if (tastesBean != null) {
            tastesBeans.clear();
            tastesBeans.add(tastesBean);
        }

        dataBean.setMakes(makesBeans);
        dataBean.setMats(matsBeans);
        dataBean.setTastes(tastesBeans);
        dataBean.setScales(scaleBeans);

        productsBean.setMakes(makesBeans);
        productsBean.setMats(matsBeans);
        productsBean.setTastes(tastesBeans);
        proId = productsBean.getProId();
        callback.onSingleCallBack(proId, number, productsBean, dataBean, position);
        Log.e("number", "productsBean: {" + PrinterUtil.toJson(productsBean) + "\n}");
        number = 1;
        cancel();
    }

}
