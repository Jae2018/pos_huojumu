package com.huojumu.main.dialogs;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.base.BaseDialog;
import com.huojumu.utils.SpUtil;

import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;

/**
 * Created by Administrator
 */
public class UsbDeviceList extends BaseDialog {


    /**
     * Member fields
     */
    @BindView(R.id.lvUsbDevices)
    ListView lvUsbDevice;
    private ArrayAdapter<String> mUsbDeviceArrayAdapter;
    private DialogInterface anInterface;
    private BaseActivity activity;

    public UsbDeviceList(@NonNull BaseActivity activity, DialogInterface anInterface) {
        super(activity);
        this.activity = activity;
        this.anInterface = anInterface;
    }

    @Override
    public int setLayout() {
        return R.layout.dialog_usb_list;
    }

    @Override
    public void initView() {
        mUsbDeviceArrayAdapter = new ArrayAdapter<>(getContext(),
                R.layout.usb_device_name_item);
        lvUsbDevice.setOnItemClickListener(mDeviceClickListener);
        lvUsbDevice.setAdapter(mUsbDeviceArrayAdapter);
        getUsbDeviceList();
    }

    private boolean checkUsbDevicePidVid(UsbDevice dev) {
        int pid = dev.getProductId();
        int vid = dev.getVendorId();
        return ((vid == 34918 && pid == 256) || (vid == 1137 && pid == 85)
                || (vid == 26728 && pid == 256) || (vid == 26728 && pid == 512)
                || (vid == 6790 && pid == 30084) || (vid == 26728 && pid == 768)
                || (vid == 26728 && pid == 1024) || (vid == 26728 && pid == 1280)
                || (vid == 26728 && pid == 1536));
    }

    private void getUsbDeviceList() {
        UsbManager manager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        // Get the list of attached devices
        HashMap<String, UsbDevice> devices = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = devices.values().iterator();
        int count = devices.size();

        if (count > 0) {
            while (deviceIterator.hasNext()) {
                UsbDevice device = deviceIterator.next();
                String devicename = device.getDeviceName();
//                String pName = device.getProductName();

                if (checkUsbDevicePidVid(device)) {
                    mUsbDeviceArrayAdapter.add(devicename);
                }
            }
        } else {
            String noDevices = "无设备连接";
            mUsbDeviceArrayAdapter.add(noDevices);
        }
    }

    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            anInterface.OnUsbCallBack(info);
            cancel();
        }
    };
}
