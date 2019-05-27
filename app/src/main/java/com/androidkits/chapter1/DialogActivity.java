package com.androidkits.chapter1;

import android.os.Bundle;
import android.view.KeyEvent;

import com.androidkits.chapter1.widget.CustomDialog;
import com.common.androidexample200.R;
import com.common.library.commons.common.CommonActivity;
import com.common.library.utils.SystemBarUtil;

public class DialogActivity extends CommonActivity {

    private CustomDialog mCustomDialog;

    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);
        setTitle("提示Dialog");
        showBack();
        setEnableBackLayout(false);
    }

    @Override
    protected void initListener(Bundle onSavedInstance) {
        super.initListener(onSavedInstance);
    }

    @Override
    protected void initData( ) {
        super.initData();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showCustomDialog();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void showCustomDialog() {
        if (mCustomDialog == null) {
            mCustomDialog = new CustomDialog(this, "提示", "您确定退出应用吗？", "确定", "取消");
            mCustomDialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                @Override
                public void confirm(boolean positiveButton) {
                    if (positiveButton) {
                        finish();
                    }
                }
            });
        }
        mCustomDialog.show();
    }
}
