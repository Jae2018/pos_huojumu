package com.huojumu.main.dialogs;

import com.huojumu.model.OrderInfo;
import com.huojumu.model.Products;


/**
 * @author : Jie
 * Date: 2018/10/17
 * Description:
 */
public interface SingleProCallback {

    void onSingleCallBack(int scaleId, int number, Products.ProductsBean productsBean, OrderInfo.DataBean dataBean, int position);

}
