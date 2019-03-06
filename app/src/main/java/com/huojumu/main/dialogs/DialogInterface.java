package com.huojumu.main.dialogs;

/**
 * @author : Jie
 * Date: 2018/10/11
 * Description: dialog interface
 */
public interface DialogInterface {
    void OnDialogOkClick(int type, double earn, double cost, double charge, String name);

    void OnUsbCallBack(String name);
}
