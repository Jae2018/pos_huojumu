package com.huojumu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.huojumu.main.activity.MainActivity;

import static android.content.Intent.ACTION_BOOT_COMPLETED;


/**
 * @author : Jie
 * Date: 2018/6/1
 * Description:
 */
public class BootReceiver extends BroadcastReceiver {

    private String TAG = BootReceiver.class.getSimpleName();

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        Log.e(TAG, "onReceive: " + action);
        if (action != null && action.equals(ACTION_BOOT_COMPLETED)) {
            Log.e(TAG, "onReceive: ACTION_BOOT_COMPLETED");
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
