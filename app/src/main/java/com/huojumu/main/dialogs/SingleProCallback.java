package com.huojumu.main.dialogs;

import com.huojumu.model.OrderInfo;
import com.huojumu.model.Products;


/**
 * @author : Jie
 * Date: 2018/10/17
 * Description:
 */
public interface SingleProCallback {

    void onSingleCallBack(int scaleId, int value, Products.ProductsBean productsBean, OrderInfo.DataBean.TastesBean tastesBean, OrderInfo.DataBean dataBean);

}
