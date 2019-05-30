package com.common.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;

import com.common.library.helper.ApplicationHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaodong.jin on 2018/3/15.
 * description：
 */

public class DeviceUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dp){
        final float scale = ApplicationHelper.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float px) {
        final float scale = ApplicationHelper.getApplication().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    /**
     * 获得屏幕宽度 px
     * @param context
     * @return
     */
    public static int getWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获得屏幕高度 px
     * @param context
     * @return
     */
    public static int getHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


    /**
     * 获取字符串
     * @param id 字符串id
     * @return
     */
    public static String getResString(int id){
        return ApplicationHelper.getApplication().getResources().getString(id);
    }

    /**
     * 时间格式转换
     * @param data
     * @return
     */
    public static String longTimeTOString(long data) {
        return longTimeTOString(data, null);
    }


    public static String longTimeTOString(long data, String format) {
        if (null == format) {
            format = "MM月dd  HH:mm";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);//yyyy年
        Date curDate = new Date(data);//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }


    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

}
