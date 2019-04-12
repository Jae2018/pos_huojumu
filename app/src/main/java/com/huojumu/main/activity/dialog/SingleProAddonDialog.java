package com.huojumu.main.activity.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.base.BaseDialog;
import com.huojumu.main.dialogs.SingleProCallback;
import com.huojumu.model.MakesBean;
import com.huojumu.model.MatsBean;
import com.huojumu.model.OrderInfo;
import com.huojumu.model.Production;
import com.huojumu.model.ScaleBean;
import com.huojumu.model.Specification;
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
    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.tv_home_addon_number)
    EditText numTV;//数量
    @BindView(R.id.et_addon)
    EditText addOnET;//备注

    @BindView(R.id.tv_product_name)
    TextView name;

    private Production productsBean,current;//单品

    private List<TastesBean> tastesBeans = new ArrayList<>();//口味
    private List<MatsBean> matsBeans = new ArrayList<>();//加料
    private List<MakesBean> makesBeans = new ArrayList<>();//做法
    private List<ScaleBean> scaleBeans = new ArrayList<>();//规格

    private SingleProCallback callback;//dialog回调
    private int position;

    private TastesBean tastesBean;
    private MatsBean matsBean;
    private MakesBean makesBean;
    private ScaleBean scaleBean;
    private double price = 0;
    private int p = -1;
    private int number = 1;

    private Specification specification;

    public SingleProAddonDialog(@NonNull Context context, SingleProCallback callback,
                                Production productsBean,
                                Specification specification, int pos) {
        super(context);
        this.productsBean = productsBean;
        this.callback = callback;
        this.specification = specification;
        this.position = pos;
    }

    @Override
    public int setLayout() {
        return R.layout.dialog_for_home_detail;
    }

    @Override
    public void initView() {
        current = new Production();
        name.setText(productsBean.getProName());

        if (productsBean.getImgs().size() > 0) {
            GlideApp.with(getContext()).load(productsBean.getImgs().get(0).getPath()).into(image);
        }
        matsBeans.clear();
        final LayoutInflater mInflater = LayoutInflater.from(getContext());
        if (specification != null) {
            //规格
            if (specification.getScales() == null || specification.getScales().isEmpty()) {
                t3.setVisibility(View.GONE);
                flowLayout1.setVisibility(View.GONE);
            } else {
                TagAdapter makesAdapter = new TagAdapter<ScaleBean>(specification.getScales()) {
                    @Override
                    public View getView(FlowLayout parent, int position, ScaleBean o) {
                        TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv,
                                flowLayout1, false);
                        tv.setText(o.getScaName());
                        return tv;
                    }
                };
                makesAdapter.setSelectedList(0);
                scaleBean = specification.getScales().get(0);
                flowLayout1.setMaxSelectCount(1);

                flowLayout1.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        scaleBean = specification.getScales().get(position);
                        return true;
                    }
                });
                flowLayout1.setAdapter(makesAdapter);
            }

            //口味
            if (specification.getTastes() == null || specification.getTastes().isEmpty()) {
                t4.setVisibility(View.GONE);
                flowLayout2.setVisibility(View.GONE);
            } else {
                TagAdapter tastesAdapter = new TagAdapter<TastesBean>(specification.getTastes()) {
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
                tastesBean = specification.getTastes().get(0);
                flowLayout2.setMaxSelectCount(1);
                flowLayout2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        tastesBean = specification.getTastes().get(position);
                        return true;
                    }
                });
                flowLayout2.setAdapter(tastesAdapter);
            }

            //加料
            if (specification.getMats() == null || specification.getMats().isEmpty()) {
                t5.setVisibility(View.GONE);
                flowLayout3.setVisibility(View.GONE);
            } else {
                flowLayout3.setAdapter(new TagAdapter<MatsBean>(specification.getMats()) {
                    @Override
                    public View getView(FlowLayout parent, int position, MatsBean o) {
                        TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv,
                                flowLayout1, false);
                        tv.setGravity(Gravity.CENTER);
                        tv.setText(String.format("%s\n￥ %s", o.getMatName(), o.getIngredientPrice()));
                        return tv;
                    }
                });

                flowLayout3.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        if (p != position) {
                            p = position;
                            matsBean = specification.getMats().get(position);
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
            if (specification.getMakes() == null || specification.getMakes().isEmpty()) {
                t6.setVisibility(View.GONE);
                flowLayout4.setVisibility(View.GONE);
            } else {
                TagAdapter makeAdapter = new TagAdapter<MakesBean>(specification.getMakes()) {
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
                makesBean = specification.getMakes().get(0);

                flowLayout4.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        makesBean = specification.getMakes().get(position);
                        return false;
                    }
                });
            }
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

    @OnClick(R.id.add)
    void add(){
        number--;
        if (number < 1) {
            number = 1;
        }
        numTV.setText(String.valueOf(number));
    }

    @OnClick(R.id.sub)
    void sub(){
        number++;
        if (number > 999) {
            number = 999;
        }
        numTV.setText(String.valueOf(number));
    }

    @OnClick(R.id.btn_home_addon_cancel)
    void Cancel() {
        dismiss();
    }

    @OnClick(R.id.btn_home_addon_ok)
    void Ok() {

        if (numTV.getText().length() > 0) {
            number = Integer.parseInt(numTV.getText().toString().trim());
        }

        current.setProId(productsBean.getProId());
        current.setProType(productsBean.getProType());
        current.setProName(productsBean.getProName());
        current.setAddon(addOnET.getText().toString());//备注
        current.setTasteStr(tastesBean != null ? tastesBean.getTasteName() : "默认口味");
        OrderInfo.DataBean dataBean = new OrderInfo.DataBean();
        dataBean.setProType(productsBean.getProType());
        dataBean.setProId(productsBean.getProId());

        if (makesBean != null) {
            makesBeans.clear();
            makesBeans.add(makesBean);
        }

        if (scaleBean != null) {
            price += scaleBean.getPrice();
            scaleBeans.clear();
            scaleBeans.add(scaleBean);
            current.setScaleStr(scaleBean.getScaName());
        }

        if (tastesBean != null) {
            tastesBeans.clear();
            tastesBeans.add(tastesBean);
        }

        String matStr = "";
        double matP = 0;
        for (int i = 0; i < matsBeans.size(); i++) {
            matStr += matsBeans.get(i).getMatName() + " ";
            matP += matsBeans.get(i).getIngredientPrice();
        }
        current.setMatStr(matStr);
        current.setMateP(matP);

        dataBean.setMakes(makesBeans);
        dataBean.setMats(matsBeans);
        dataBean.setTastes(tastesBeans);
        dataBean.setScales(scaleBeans);

        current.setMakes(makesBeans);
        current.setMats(matsBeans);
        current.setTastes(tastesBeans);

        callback.onSingleCallBack(productsBean.getProId(), number, current, dataBean, position, price);

        price = 0;
        cancel();
    }

}
