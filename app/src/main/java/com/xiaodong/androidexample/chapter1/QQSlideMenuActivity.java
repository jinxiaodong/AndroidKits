package com.xiaodong.androidexample.chapter1;

import android.os.Bundle;

import com.xiaodong.androidexample200.R;
import com.xiaodong.basetools.base.JBaseActivity;
import com.xiaodong.basetools.utils.SystemBarUtil;

public class QQSlideMenuActivity extends JBaseActivity {


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_qqslide_menu;
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);
        setTitle("仿QQ侧滑菜单");
        showOrHideBackButton(true);
        setEnableBackLayout(false);

    }
}
