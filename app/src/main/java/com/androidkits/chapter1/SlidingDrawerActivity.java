package com.androidkits.chapter1;

import android.os.Bundle;

import com.common.androidexample200.R;
import com.common.library.commons.common.CommonActivity;
import com.common.library.utils.SystemBarUtil;

public class SlidingDrawerActivity extends CommonActivity {


    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_sliding_drawer;
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);
        showBack();
        setTitle("抽屉式公告");
        setEnableBackLayout(false);
    }


}
