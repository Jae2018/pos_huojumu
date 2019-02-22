package com.huojumu.main.activity.dialog;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.adapter.HomeSelectedAdapter;
import com.huojumu.model.Products;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/16
 * Description: 双屏异显
 */
public class DifferentDisplay extends Presentation {

    private SurfaceView sv;
    private RecyclerView rv;
    private TextView priceTV, cutTV;
    private List<Products.ProductsBean> list;
    private HomeSelectedAdapter selectedAdapter;
    //结账二维码
    private ImageView aliIV,wxIV;

    public DifferentDisplay(Context outerContext, Display display) {
        super(outerContext, display);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_for_differ_screen);
//        sv = findViewById(R.id.sv_differ_video);
        rv = findViewById(R.id.recycler_for_differ);
        priceTV = findViewById(R.id.tv_differ_money);
        cutTV = findViewById(R.id.tv_differ_cut);
        aliIV = findViewById(R.id.iv_pay_image);
//        wxIV = findViewById(R.id.iv_pay_wx);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        selectedAdapter = new HomeSelectedAdapter(list);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.line_v_for_recycler));
        rv.addItemDecoration(itemDecoration);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        rv.setAdapter(selectedAdapter);
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public SurfaceView getSv() {
        return sv;
    }

    public void refresh(List<Products.ProductsBean> list) {
        selectedAdapter.setNewData(list);
    }

    public void clear(){
        selectedAdapter.setNewData(null);
        aliIV.setImageBitmap(null);
    }

    public void setPrice(double total, double cut) {
        priceTV.setText(String.valueOf(total));
        cutTV.setText(String.format("优惠：%.2s元", String.valueOf(cut)));
    }

    public ImageView getAliIV() {
        return aliIV;
    }

    public ImageView getWxIV() {
        return null;
    }

}
