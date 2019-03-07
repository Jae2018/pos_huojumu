package com.huojumu.utils;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;

import java.util.HashMap;
import java.util.Iterator;

public class UsbUtil {

    private static boolean checkUsbDevicePidVid(UsbDevice dev) {
        int pid = dev.getProductId();
        int vid = dev.getVendorId();
        return ((vid == 34918 && pid == 256) || (vid == 1137 && pid == 85)
                || (vid == 26728 && pid == 256) || (vid == 26728 && pid == 512)
                || (vid == 6790 && pid == 30084) || (vid == 26728 && pid == 768)
                || (vid == 26728 && pid == 1024) || (vid == 26728 && pid == 1280)
                || (vid == 26728 && pid == 1536));
    }

    public static String getUsbDeviceList(Context context) {
        String devicename = "";
        UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        // Get the list of attached devices
        HashMap<String, UsbDevice> devices = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = devices.values().iterator();
        int count = devices.size();

        if (count > 0) {
            while (deviceIterator.hasNext()) {
                UsbDevice device = deviceIterator.next();

                if (device != null && checkUsbDevicePidVid(device)) {
                    devicename = device.getDeviceName();
                }
            }
        }

        return devicename;
    }

}
