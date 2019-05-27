package com.common.library.commons.router.interceptor;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.common.library.utils.LogUtil;

/**
 * Created by xiaodong.jin on 2019/05/27.
 * description：Arouterd:登录拦截器
 */
@Interceptor(priority = 8, name = "logininterceptor")
public class LoginInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Bundle extras = postcard.getExtras();
        if (extras != null && extras.getBoolean("needLogin", false)) {
            //需要登录


        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        LogUtil.i("LoginInterceptor", "登录拦截器初始化");
    }
}
