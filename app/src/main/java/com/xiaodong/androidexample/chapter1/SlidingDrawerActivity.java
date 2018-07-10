package com.xiaodong.androidexample.chapter1;

import android.os.Bundle;

import com.xiaodong.androidexample200.R;
import com.xiaodong.basetools.base.JBaseActivity;
import com.xiaodong.basetools.utils.SystemBarUtil;

public class SlidingDrawerActivity extends JBaseActivity {


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_sliding_drawer;
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);
        showOrHideBackButton(true);
        setTitle("抽屉式公告");
        setEnableBackLayout(false);
    }


}
