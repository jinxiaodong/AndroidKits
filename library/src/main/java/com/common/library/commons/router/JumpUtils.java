package com.common.library.commons.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by xiaodong.jin on 2019/05/27.
 * description：统一跳转工具
 */
public class JumpUtils {


    public static void startActivity(Context context, String path) {
        startActivity(context, path, null);
    }

    public static void startActivity(Context context, String path, Bundle bundle) {
        Postcard build = ARouter.getInstance().build(path);
        if (bundle != null) {
            build.with(bundle);
        }
        build.navigation();
    }


    public static void startActivityForResult(Activity context, int requestCode, String path, Bundle bundle) {
        Postcard build = ARouter.getInstance()
                .build(path);
        if (bundle != null) {
            build.with(bundle);
        }
        build.navigation(context, requestCode);
    }
}
