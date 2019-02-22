package com.huojumu.main.activity.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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
import com.huojumu.model.Products;
import com.huojumu.model.Specification;
import com.huojumu.model.TastesBean;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @BindView(R.id.textView3)
    TextView t3;
    @BindView(R.id.textView5)
    TextView t4;
    @BindView(R.id.textView16)
    TextView t5;

    @BindView(R.id.tv_home_addon_number)
    EditText numTV;//数量
    @BindView(R.id.et_addon)
    EditText addOnET;//备注

    private int proId;//商品ID
    private int number = 1;//数量

    private Products.ProductsBean productsBean;//单品

    private List<TastesBean> tastesBeans = new ArrayList<>();//口味
    private List<MatsBean> matsBeans = new ArrayList<>();//加料
    private List<MakesBean> makesBeans = new ArrayList<>();//做法

    private SingleProCallback callback;//dialog回调
    private int position;
    private boolean isChange;

    private TastesBean tastesBean;
    private MatsBean matsBean;
    private MakesBean makesBean;

    public SingleProAddonDialog(@NonNull Context context, SingleProCallback callback,
                                Products.ProductsBean productsBean,
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

        final LayoutInflater mInflater = LayoutInflater.from(getContext());
        NetTool.getSpecification(SpUtil.getInt(Constant.PINPAI_ID), proId, new GsonResponseHandler<BaseBean<Specification>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<Specification> response) {
                tastesBeans.addAll(response.getData().getTastes());
                matsBeans.addAll(response.getData().getMats());
                makesBeans.addAll(response.getData().getMakes());
                if (!makesBeans.isEmpty()) {
                    flowLayout1.setAdapter(new TagAdapter<MakesBean>(makesBeans) {
                        @Override
                        public View getView(FlowLayout parent, int position, MakesBean o) {
                            TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv,
                                    flowLayout1, false);
                            tv.setText(o.getPracticeName());
                            return tv;
                        }
                    });
                    t3.setVisibility(View.VISIBLE);
                    flowLayout1.setVisibility(View.VISIBLE);
                }

                if (!tastesBeans.isEmpty()) {
                    flowLayout2.setAdapter(new TagAdapter<TastesBean>(tastesBeans) {
                        @Override
                        public View getView(FlowLayout parent, int position, TastesBean o) {
                            TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv,
                                    flowLayout1, false);
                            tv.setText(o.getTasteName());
                            return tv;
                        }

                        @Override
                        public void onSelected(int position, View view) {
                            if (position == 0) {
                                view.setSelected(true);
                            }
                        }
                    });
                    t4.setVisibility(View.VISIBLE);
                    flowLayout2.setVisibility(View.VISIBLE);
                }

                if (!matsBeans.isEmpty()) {
                    flowLayout3.setAdapter(new TagAdapter<MatsBean>(matsBeans) {
                        @Override
                        public View getView(FlowLayout parent, int position, MatsBean o) {
                            TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv,
                                    flowLayout1, false);
                            tv.setText(o.getMatName());
                            return tv;
                        }
                    });
                    t5.setVisibility(View.VISIBLE);
                    flowLayout3.setVisibility(View.VISIBLE);
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
        }


        flowLayout1.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
//                makesBeans.get(selectPosSet.iterator().next()).setSelected(true);
                makesBean = makesBeans.get(selectPosSet.iterator().next());
            }
        });

        flowLayout2.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
//                tastesBeans.get(selectPosSet.iterator().next()).setSelected(true);
                tastesBean = tastesBeans.get(selectPosSet.iterator().next());
            }
        });

        flowLayout3.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
//                matsBeans.get(selectPosSet.iterator().next()).setSelected(true);
                matsBean = matsBeans.get(selectPosSet.iterator().next());
            }
        });

    }

    @OnClick(R.id.btn_home_addon_cancel)
    void Cancel() {
        dismiss();
    }

    @OnClick(R.id.btn_home_addon_ok)
    void Ok() {
        number = Integer.parseInt(numTV.getText().toString());
        productsBean.setAddon(addOnET.getText().toString());//备注
        productsBean.setTasteStr(tastesBean!= null ? tastesBean.getTasteName() : "");
        OrderInfo.DataBean dataBean = new OrderInfo.DataBean();
        dataBean.setProType(productsBean.getProType());
        dataBean.setProId(productsBean.getProId());

        if (makesBean != null) {
            makesBeans.add(makesBean);
        }

        if (matsBean != null) {
            matsBeans.add(matsBean);
        }

        if (tastesBean != null) {
            tastesBeans.add(tastesBean);
        }

        dataBean.setMakes(makesBeans);
        dataBean.setMats(matsBeans);
        dataBean.setTastes(tastesBeans);

        proId = productsBean.getProId();
        callback.onSingleCallBack(proId, number, productsBean, dataBean, position);
        Log.e("number", "Ok: " + number);
        number = 1;
        cancel();
    }

}
