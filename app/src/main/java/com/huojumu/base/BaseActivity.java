package com.huojumu.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.huojumu.MyApplication;
import com.huojumu.utils.CustomerEngine;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * @author : Jie
 * Date: 2018/5/25
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected CustomerEngine engine;
    protected LoadingDialog ld, ld2;
    public static ArrayList<String> per = new ArrayList<>();
    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH
    };
    private static final int REQUEST_CODE = 0x004;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(setLayout());
        ButterKnife.bind(this);

        engine = CustomerEngine.getInstance(getApplicationContext());
        initView();
        initData();

        checkPermission();
        requestPermission();
    }

    private void checkPermission() {
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permission)) {
                per.add(permission);
            }
        }
    }

    public void requestPermission() {
        if (per.size() > 0) {
            String[] p = new String[per.size()];
            ActivityCompat.requestPermissions(this, per.toArray(p), REQUEST_CODE);
        }
    }

    // 设置布局
    protected abstract int setLayout();

    // 初始化组件
    protected abstract void initView();

    // 初始化数据
    protected abstract void initData();

    @Override
    public void onBackPressed() {

    }

    public boolean grantAutomaticPermission(UsbDevice usbDevice) {
        try {
            Log.e(">>>", "grantAutomaticPermission: " );
            Context context = MyApplication.getContext();
            PackageManager pkgManager = context.getPackageManager();
            ApplicationInfo appInfo = pkgManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

            Class serviceManagerClass = Class.forName("android.os.ServiceManager");
            Method getServiceMethod = serviceManagerClass.getDeclaredMethod("getService", String.class);
            getServiceMethod.setAccessible(true);
            android.os.IBinder binder = (android.os.IBinder) getServiceMethod.invoke(null, Context.USB_SERVICE);

            Class iUsbManagerClass = Class.forName("android.hardware.usb.IUsbManager");
            Class stubClass = Class.forName("android.hardware.usb.IUsbManager$Stub");
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", android.os.IBinder.class);
            asInterfaceMethod.setAccessible(true);
            Object iUsbManager = asInterfaceMethod.invoke(null, binder);


            System.out.println("UID : " + appInfo.uid + " " + appInfo.processName + " " + appInfo.permission);
            final Method grantDevicePermissionMethod = iUsbManagerClass.getDeclaredMethod("grantDevicePermission", UsbDevice.class, int.class);
            grantDevicePermissionMethod.setAccessible(true);
            grantDevicePermissionMethod.invoke(iUsbManager, usbDevice, appInfo.uid);


            System.out.println("Method OK : " + binder + "  " + iUsbManager);
            return true;
        } catch (Exception e) {
            System.err.println("Error trying to assing automatic usb permission : ");
            e.printStackTrace();
            return false;
        }
    }

}
