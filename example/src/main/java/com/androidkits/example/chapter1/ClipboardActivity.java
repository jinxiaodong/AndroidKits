package com.androidkits.example.chapter1;

import android.os.Bundle;

import com.androidkits.example.R;
import com.common.library.commons.common.CommonActivity;
import com.common.library.utils.SystemBarUtil;


public class ClipboardActivity extends CommonActivity {

    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_clipboard;
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);
        setTitle("剪切板");

    }
}
