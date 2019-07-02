package com.huojumu.listeners;

/**
 * @author : Jie
 * Date: 2018/10/11
 * Description: dialog interface
 */
public interface DialogInterface {
    /**
     * @param type   类型
     * @param earn   实收金额
     * @param cost   消费金额
     * @param charge 找零
     * @param name   姓名
     */
    void OnDialogOkClick(int type, double earn, double cost, double charge, String name);

    void OnUsbCallBack(String name);
}
