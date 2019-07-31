//package com.huojumu.receiver;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import com.huojumu.main.activity.MainActivity;
//
//public class ReInstallReceiver extends BroadcastReceiver {
//
//    private String TAG = ReInstallReceiver.class.getSimpleName();
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        Log.e(TAG, "ReInstallReceiver: " + intent.getAction());
//        String action = intent.getAction();
//        if (action != null)
//            switch (action) {
//                case Intent.ACTION_PACKAGE_ADDED:
//                    Log.e(TAG, "onReceive: ACTION_PACKAGE_ADDED");
//                    break;
//                case Intent.ACTION_PACKAGE_REMOVED:
//                    Log.e(TAG, "onReceive: ACTION_PACKAGE_REMOVED");
//                    break;
//                case Intent.ACTION_PACKAGE_REPLACED:
//                    Log.e(TAG, "onReceive: ACTION_PACKAGE_REPLACED");
//                    Intent intent2 = new Intent(context, MainActivity.class);
//                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent2);
//                    break;
//            }
//    }
//}
