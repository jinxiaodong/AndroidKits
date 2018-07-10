package com.xiaodong.androidexample;

import android.app.Application;

/**
 * Created by xiaodong.jin on 2018/6/13.
 * description：
 */

public class JApplication extends Application {

    private static JApplication mInstance;

    public static Application getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
