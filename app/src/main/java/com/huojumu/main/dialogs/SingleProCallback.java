package com.huojumu.main.dialogs;

import com.huojumu.model.OrderInfo;
import com.huojumu.model.Production;


/**
 * @author : Jie
 * Date: 2018/10/17
 * Description:
 */
public interface SingleProCallback {

    void onSingleCallBack(int scaleId, int number, Production productsBean, OrderInfo.DataBean dataBean, double origionPrice, double price);

    void onCancelCallBack();
}
