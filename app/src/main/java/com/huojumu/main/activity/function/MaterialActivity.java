package com.huojumu.main.activity.function;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.adapter.MaterialAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.Material;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 库存
 */
public class MaterialActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.emptyTv)
    TextView emptyTv;
    @BindView(R.id.recycler_material)
    RecyclerView recycler_material;

    private MaterialAdapter adapter;
    String titleStr;

    @Override
    protected int setLayout() {
        return R.layout.activity_material_inspect;
    }

    @Override
    protected void initView() {
        titleStr = getIntent().getStringExtra("title");
        title.setText(titleStr);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler_material.setLayoutManager(manager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider_v));
        recycler_material.addItemDecoration(decoration);
        adapter = new MaterialAdapter(null);
        recycler_material.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        NetTool.getMaterial(SpUtil.getInt(Constant.STORE_ID), SpUtil.getInt(Constant.ENT_ID), SpUtil.getInt(Constant.PINPAI_ID), new GsonResponseHandler<BaseBean<Material>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<Material> response) {
                if (response.getData().getRows() == null || response.getData().getRows().isEmpty()) {
                    emptyTv.setText("暂无数据");
                    emptyTv.setVisibility(View.VISIBLE);
                } else {
                    emptyTv.setVisibility(View.GONE);
                    adapter.setNewData(response.getData().getRows());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }

    @OnClick(R.id.back)
    void back() {
//        startActivity(new Intent(MaterialActivity.this, HomeActivity.class));
        finish();
    }

}
