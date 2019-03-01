package com.huojumu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;

import com.huojumu.main.activity.MainActivity;

import static android.content.Intent.ACTION_BOOT_COMPLETED;
import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;

/**
 * @author : Jie
 * Date: 2018/6/1
 * Description:
 */
public class PosReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
//            case ACTION_USB_DEVICE_DETACHED:
//                sendStateBroadcast(CONN_STATE_DISCONNECT);
//                break;
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
