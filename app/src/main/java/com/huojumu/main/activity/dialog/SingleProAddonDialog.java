package com.huojumu.main.activity.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huojumu.R;
import com.huojumu.adapter.HomeAddonTasteAdapter;
import com.huojumu.base.BaseDialog;
import com.huojumu.main.dialogs.SingleProCallback;
import com.huojumu.model.OrderInfo;
import com.huojumu.model.Products;

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

    @BindView(R.id.recycler_home_addon_scale)
    Button scaleBtn;//杯型
    @BindView(R.id.recycler_home_addon_taste)
    RecyclerView tasteRecycler;//口味
    //    @BindView(R.id.recycler_home_addon_mats)
//    RecyclerView matsRecycler;//配料
    @BindView(R.id.tv_home_addon_number)
    TextView numTV;//数量
    @BindView(R.id.et_addon)
    EditText addOnET;//备注

    private int scaleId = -1;//规格ID
    private int number = 1;//数量
    private List<Products.TastesBean> tastesBeanList = new ArrayList<>();//口味
    private List<Products.ScalesBean> scalesBeans;//规格
    private Products.ProductsBean productsBean;//单品

    private SingleProCallback callback;//dialog回调


    private boolean isChange;

    public SingleProAddonDialog(@NonNull Context context, SingleProCallback callback,
                                List<Products.ScalesBean> scalesBeans,
                                List<Products.TastesBean> tastesBeanList,
                                Products.ProductsBean productsBean,
                                boolean isChange) {
        super(context);
        this.scalesBeans = scalesBeans;
        this.tastesBeanList.addAll(tastesBeanList);
        this.productsBean = productsBean;
        this.callback = callback;
        this.isChange = isChange;
    }

    @Override
    public int setLayout() {
        return R.layout.dialog_for_home_detail;
    }

    @Override
    public void initView() {
        //杯子规格
        String name = "冷饮小杯";
        if (scalesBeans != null)
            for (Products.ScalesBean scalesBean : scalesBeans) {
                if (scalesBean.getScaleId() == productsBean.getScaleId()) {
                    name = scalesBean.getScaName();
                    scaleId = scalesBean.getScaleId();
                }
            }
        scaleBtn.setText(name);
        scaleBtn.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));

        if (isChange) {
            number = productsBean.getNumber();
            numTV.setText(String.valueOf(number));
            addOnET.setText(productsBean.getAddon());
        } else {
            if (!productsBean.getTastes().isEmpty())
                for (int i = 0; i < tastesBeanList.size(); i++) {
                    if (tastesBeanList.get(i).getGroupId() != productsBean.getTastes().get(0).getTasteGroupId()) {
                        tastesBeanList.remove(i);
                    }
                }
        }

        //口味，可选
        tastesBeanList.get(0).setSelected(true);
        tasteRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        final HomeAddonTasteAdapter addonTasteAdapter = new HomeAddonTasteAdapter(tastesBeanList);
        tasteRecycler.setAdapter(addonTasteAdapter);
        addonTasteAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < tastesBeanList.size(); i++) {
                    tastesBeanList.get(i).setSelected(i == position);
                }
                addonTasteAdapter.notifyDataSetChanged();
            }
        });

        //配料
//        matsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 6));
//        HomeAddonMatsAdapter matsAdapter = new HomeAddonMatsAdapter(productsBean.getMats());
//        matsRecycler.setAdapter(matsAdapter);
//        matsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//            }
//        });
    }


    @OnClick(R.id.btn_home_addon_sub)
    void Sub() {
        number--;
        if (number < 1) {
            number = 1;
        }
        numTV.setText(String.valueOf(number));
    }

    @OnClick(R.id.btn_home_addon_plus)
    void Plus() {
        number++;
        if (number > 99) {
            number = 99;
        }
        numTV.setText(String.valueOf(number));
    }

    @OnClick(R.id.btn_home_addon_cancel)
    void Cancel() {
        dismiss();
    }

    @OnClick(R.id.btn_home_addon_ok)
    void Ok() {
        productsBean.setNumber(number);//数量
        productsBean.setAddon(addOnET.getText().toString());//备注
        OrderInfo.DataBean dataBean = new OrderInfo.DataBean();
        dataBean.setTaocan(new ArrayList<OrderInfo.DataBean.TaocanBean>());
        dataBean.setProType(productsBean.getProType());
        dataBean.setProId(productsBean.getProId());
        dataBean.setNum(number);
        dataBean.setMakes(new ArrayList<OrderInfo.DataBean.MakesBean>());
        dataBean.setMats(new ArrayList<OrderInfo.DataBean.MatsBean>());

        StringBuilder sb = new StringBuilder();
        OrderInfo.DataBean.TastesBean tastesBean = new OrderInfo.DataBean.TastesBean();
        for (Products.TastesBean t : tastesBeanList) {
            if (t.isSelected()) {
                sb.append(t.getTasteName()).append(" ");
                tastesBean.setTasteId(t.getTasteId());
            }
        }
        productsBean.setTasteStr(sb.toString());
        callback.onSingleCallBack(scaleId, number, productsBean, tastesBean, dataBean);
        for (Products.TastesBean t : tastesBeanList) {
            t.setSelected(false);
        }

        number = 1;
        cancel();
    }

}
