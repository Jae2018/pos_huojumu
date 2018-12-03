package com.huojumu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;

import com.huojumu.main.activity.MainActivity;

/**
 * @author : Jie
 * Date: 2018/6/1
 * Description:
 */
public class PosReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else if (UsbManager.ACTION_USB_ACCESSORY_ATTACHED.equals(intent.getAction())) {

        } else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(intent.getAction())) {

        }
    }
}
