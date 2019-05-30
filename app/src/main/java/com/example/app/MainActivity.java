package com.example.app;

import com.common.library.commons.common.CommonActivity;

public class MainActivity extends CommonActivity {

    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }


}
