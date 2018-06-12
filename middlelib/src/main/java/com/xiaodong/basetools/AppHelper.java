package com.xiaodong.basetools;

import android.app.Application;

import java.lang.reflect.Method;

/**
 * Created by jiangdylan on 2017/10/22.
 */

public class AppHelper {
    public static Application getApplication() {
        try {
            Class<?> clazz = Class.forName("android.app.ActivityThread");
            Method method = clazz.getMethod("currentApplication");
            return (Application) method.invoke(null, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
