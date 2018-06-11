package com.project.xiaodong.fflibrary.constants;

import com.project.xiaodong.fflibrary.ApplicationHelper;
import com.project.xiaodong.fflibrary.BuildConfig;

/**
 * Created by xiaodong.jin on 2018/2/6.
 * description：常量
 */

public class BaseConstants {

    /*Debug 版本标志*/
    public static boolean isTest = BuildConfig.DEBUG;
    /*应用包名*/
    public final static String PACKAGE_NAME = ApplicationHelper.getApplication().getPackageName();
    /*应用版本code*/
    public static int APP_VERSION_CODE = BuildConfig.VERSION_CODE;
    /** version name */
    public static String APP_VERSION_NAME = BuildConfig.VERSION_NAME;
}
