package com.huojumu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.huojumu.MyApplication;
import com.huojumu.main.activity.MainActivity;
import com.huojumu.utils.Constant;
import com.huojumu.utils.SpUtil;

import static android.content.Intent.ACTION_BOOT_COMPLETED;
import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;

/**
 * @author : Jie
 * Date: 2018/6/1
 * Description:
 */
public class PosReceiver extends BroadcastReceiver {

    int CONN_STATE_DISCONNECT = 0x90;
    String ACTION_CONN_STATE = "action_connect_state";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null)
            switch (action) {
//            case ACTION_USB_DEVICE_DETACHED:
//                sendStateBroadcast(CONN_STATE_DISCONNECT);
//                break;
                case ACTION_BOOT_COMPLETED:
                    Intent i = new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    break;
                case ACTION_USB_DEVICE_DETACHED:
                    sendStateBroadcast(CONN_STATE_DISCONNECT);
                    break;
                default:
                    break;
            }
    }

    private void sendStateBroadcast(int state) {
        Intent intent = new Intent(ACTION_CONN_STATE);
        intent.putExtra(Constant.STATE, state);
        intent.putExtra(Constant.DEVICE_ID, SpUtil.getInt("UsbId"));
        MyApplication.getContext().sendBroadcast(intent);
    }
}
