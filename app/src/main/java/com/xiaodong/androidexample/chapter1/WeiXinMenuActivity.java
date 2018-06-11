package com.xiaodong.androidexample.chapter1;

import android.os.Bundle;

import com.xiaodong.androidexample200.R;
import com.xiaodong.basetools.base.JBaseActivity;

public class WeiXinMenuActivity extends JBaseActivity {


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_wei_xin_menu;
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        setTitle("微信右上角弹出菜单");
        showOrHideBackButton(true);
    }
}
