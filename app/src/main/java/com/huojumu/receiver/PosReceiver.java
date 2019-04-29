package com.huojumu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
//import android.hardware.usb.UsbDevice;
//import android.hardware.usb.UsbManager;
//import android.util.Log;
//
//import com.blankj.utilcode.util.ToastUtils;
//import com.huojumu.MyApplication;
import com.huojumu.main.activity.MainActivity;
//import com.huojumu.main.activity.home.HomeActivity;
//import com.huojumu.utils.Constant;
//import com.huojumu.utils.DeviceConnFactoryManager;
//import com.huojumu.utils.PrinterUtil;
//import com.huojumu.utils.SpUtil;
//import com.huojumu.utils.ThreadPool;
//import com.huojumu.utils.UsbUtil;

import static android.content.Intent.ACTION_BOOT_COMPLETED;


/**
 * @author : Jie
 * Date: 2018/6/1
 * Description:
 */
public class PosReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case ACTION_BOOT_COMPLETED:
                    Intent i = new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    break;
                default:
                    break;
            }
        }
    }
}
