package com.common.androidexample.chapter1;

import android.os.Bundle;

import com.common.androidexample200.R;
import com.common.library.commons.common.CommonActivity;
import com.common.library.utils.SystemBarUtil;

public class QQSlideMenuActivity extends CommonActivity {


    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_qqslide_menu;
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);
        setTitle("仿QQ侧滑菜单");
        showBack();
        setEnableBackLayout(false);

    }
}
