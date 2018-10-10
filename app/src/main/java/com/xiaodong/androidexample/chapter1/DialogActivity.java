package com.xiaodong.androidexample.chapter1;

import android.os.Bundle;
import android.view.KeyEvent;

import com.xiaodong.androidexample.chapter1.widget.CustomDialog;
import com.xiaodong.androidexample200.R;
import com.xiaodong.basetools.base.JBaseActivity;
import com.xiaodong.basetools.utils.SystemBarUtil;

public class DialogActivity extends JBaseActivity {

    private CustomDialog mCustomDialog;

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
        showOrHideBackButton(true);
        setEnableBackLayout(false);
    }

    @Override
    protected void initListener(Bundle onSavedInstance) {
        super.initListener(onSavedInstance);
    }

    @Override
    protected void initData(Bundle onSavedInstance) {
        super.initData(onSavedInstance);
    }

    @Override
    public void close() {
        onKeyDown(KeyEvent.KEYCODE_BACK,null);
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
