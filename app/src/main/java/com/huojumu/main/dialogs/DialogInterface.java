package com.huojumu.main.dialogs;

/**
 * @author : Jie
 * Date: 2018/10/11
 * Description: dialog interface
 */
public interface DialogInterface {
    void OnDialogOkClick(double value, String name);

    void OnTasteClick(int type, int id, int number, String name);

    void OnDialogCancelClick(int value);
}
