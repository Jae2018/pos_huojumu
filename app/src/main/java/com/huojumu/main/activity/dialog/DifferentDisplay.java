package com.huojumu.main.activity.dialog;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.adapter.HomeSelectedAdapter;
import com.huojumu.model.Production;

import java.util.List;
import java.util.Locale;

//import android.view.SurfaceView;

/**
 * @author : Jie
 * Date: 2018/10/16
 * Description: 双屏异显
 */
public class DifferentDisplay extends Presentation {

//    private SurfaceView sv;
    private RecyclerView rv;
    private TextView priceTV, cutTV;
    private HomeSelectedAdapter selectedAdapter;
    //结账二维码
    private ImageView aliIV;
    private ImageView adsLinear;

    public DifferentDisplay(Context outerContext, Display display) {
        super(outerContext, display);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_for_differ_screen);
        rv = findViewById(R.id.recycler_for_differ);
        priceTV = findViewById(R.id.tv_differ_money);
        cutTV = findViewById(R.id.tv_differ_cut);
        aliIV = findViewById(R.id.iv_pay_image);
        adsLinear = findViewById(R.id.iv_ads_image);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        selectedAdapter = new HomeSelectedAdapter(null);
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

//    public SurfaceView getSv() {
//        return sv;
//    }

    public void refresh(List<Production> list) {
        selectedAdapter.setNewData(list);
    }

    public void clear(){
        selectedAdapter.setNewData(null);
        aliIV.setImageBitmap(null);
    }

    public void setPrice(double total, double cut) {
        priceTV.setText(String.valueOf(total));
        cutTV.setText(String.format(Locale.CHINA,"优惠：%.2f元", cut));
    }

    public ImageView getAliIV() {
        return aliIV;
    }

    public ImageView getAdsLinear() {
        return adsLinear;
    }
}
