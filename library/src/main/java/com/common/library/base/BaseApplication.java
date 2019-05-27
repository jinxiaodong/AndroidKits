package com.common.library.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.common.library.commons.device.DeviceInfo;
import com.common.library.utils.LogUtil;
import com.common.library.utils.LifecycleUtils;

import androidx.multidex.MultiDexApplication;

/**
 * Created by xiaodong.jin on 2018/11/15.
 * description：
 */

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication instance;
    protected static Context context;
    public static String SCHEME = "";

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        appInit();
        LifecycleUtils.init(this);
    }

    /**
     * 设备信息初始化
     */
    private void appInit() {
        //屏幕信息\
        Resources resources = context.getResources();
        if (resources != null) {
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                LogUtil.i("Application", "============appInit, orientation:" + "横屏状态");
                DeviceInfo.WIDTHPIXELS = displayMetrics.heightPixels;
                DeviceInfo.HEIGHTPIXELS = displayMetrics.widthPixels;
            } else {
                LogUtil.i("Application", "============appInit, orientation:" + "竖屏状态");
                DeviceInfo.WIDTHPIXELS = displayMetrics.widthPixels;
                DeviceInfo.HEIGHTPIXELS = displayMetrics.heightPixels;
            }
            DeviceInfo.DENSITYDPI = displayMetrics.densityDpi;
            DeviceInfo.DENSITY = displayMetrics.density;
        }

    }
}
