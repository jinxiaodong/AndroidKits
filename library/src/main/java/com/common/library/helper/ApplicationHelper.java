package com.common.library.helper;

import android.app.Application;

import java.lang.reflect.Method;

/**
 * Created by jiangdylan on 2017/10/22.
 */

public class ApplicationHelper {
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
