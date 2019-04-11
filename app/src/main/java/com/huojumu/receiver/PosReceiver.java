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
//import static android.hardware.usb.UsbManager.ACTION_USB_ACCESSORY_ATTACHED;
//import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_ATTACHED;
//import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;
//import static com.huojumu.utils.Constant.ACTION_USB_PERMISSION;
//import static com.huojumu.utils.DeviceConnFactoryManager.ACTION_QUERY_PRINTER_STATE;
//import static com.huojumu.utils.DeviceConnFactoryManager.CONN_STATE_FAILED;

/**
 * @author : Jie
 * Date: 2018/6/1
 * Description:
 */
public class PosReceiver extends BroadcastReceiver {

    String TAG = "PosReceiver";
//    int CONN_STATE_DISCONNECT = 0x90;
//    int CONN_STATE_CONNECT = 0x100;
    String ACTION_CONN_STATE = "action_connect_state";

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

//        switch (action) {
//            case ACTION_BOOT_COMPLETED:
//                Intent i = new Intent(context, MainActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//                break;
//            case ACTION_USB_PERMISSION:
//                synchronized (this) {
//                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                        if (device != null) {
//                            usbConn(device);
//                        }
//                    } else {
//                        Log.e(TAG, "permission denied for device " + device);
//                    }
//                }
//                break;
//            //Usb连接断开、蓝牙连接断开广播
//            case ACTION_USB_DEVICE_DETACHED:
//                Log.e(TAG, "onReceive: ACTION_USB_DEVICE_DETACHED");
//                break;
//            //Usb连接断开、蓝牙连接广播
//            case ACTION_USB_DEVICE_ATTACHED:
//                Log.e(TAG, "onReceive: ACTION_USB_DEVICE_ATTACHED");
//                getUsb(UsbUtil.getUsbDeviceList(context));
//                ThreadPool.getInstantiation().addTask(new Runnable() {
//                    @Override
//                    public void run() {
//                        PrinterUtil.connectPrinter(context);
//                    }
//                });
//                break;
//            case DeviceConnFactoryManager.ACTION_CONN_STATE:
//                int state = intent.getIntExtra(DeviceConnFactoryManager.STATE, -1);
//                int deviceId = intent.getIntExtra(DeviceConnFactoryManager.DEVICE_ID, -1);
//                switch (state) {
//                    case DeviceConnFactoryManager.CONN_STATE_DISCONNECT:
//                        if (id == deviceId) {
//                            ToastUtils.showLong("标签打印机已断开连接");
//                        }
//                        break;
//                    case DeviceConnFactoryManager.CONN_STATE_CONNECTING:
//
//                        break;
//                    case DeviceConnFactoryManager.CONN_STATE_CONNECTED:
//                        ToastUtils.showLong("标签打印机已连接");
////                            if(DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].connMethod== DeviceConnFactoryManager.CONN_METHOD.WIFI){
////                                wificonn=true;
////                                if(keepConn==null) {
////                                    keepConn = new KeepConn();
////                                    keepConn.start();
////                                }
////                            }
//                        break;
//                    case CONN_STATE_FAILED:
////                            Utils.toast(MainActivity.this, getString(R.string.str_conn_fail));
//                        ToastUtils.showLong("标签打印机连接失败");
//                        getUsb(UsbUtil.getUsbDeviceList(context));
//                        //wificonn=false;
//                        break;
//                    default:
//                        break;
//                }
//                break;
//            case ACTION_QUERY_PRINTER_STATE:
//                if (printcount >= 0) {
//                    if (printcount != 0) {
//                        printLabel();
//                    }
//                }
//                break;
//            default:
//                break;
//        }
        }
    }

//    private void sendStateBroadcast(int state) {
//        Intent intent = new Intent(ACTION_CONN_STATE);
//        intent.putExtra(Constant.STATE, state);
//        intent.putExtra(Constant.DEVICE_ID, SpUtil.getInt("UsbId"));
//        MyApplication.getContext().sendBroadcast(intent);
//    }
//
//    private void sendStateBroadcast2(int state) {
//        Intent intent = new Intent(ACTION_CONN_STATE);
//        intent.putExtra(Constant.STATE, state);
//        intent.putExtra(Constant.DEVICE_ID, SpUtil.getInt("UsbId"));
//        MyApplication.getContext().sendBroadcast(intent);
//    }
}
