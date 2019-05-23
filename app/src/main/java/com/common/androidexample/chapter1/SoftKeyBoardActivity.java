package com.common.androidexample.chapter1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.common.androidexample.utils.StatusUtil;
import com.common.androidexample.utils.keyboardPanelUtils.KeyboardUtil;
import com.common.androidexample200.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SoftKeyBoardActivity extends AppCompatActivity implements KeyboardUtil.OnKeyboardShowingListener {


    private EditText edit;
    private LinearLayout ll_root;
    private ViewTreeObserver.OnGlobalLayoutListener keyboardListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_key_board);
        initWidget();
    }



    protected void initWidget() {
        StatusUtil.setStatusBarLightMode(this, Color.WHITE);
        edit = findViewById(R.id.edit);
        ll_root = findViewById(R.id.ll_root);

        keyboardListener = KeyboardUtil.attach(this, this);
    }

    @Override
    public void onKeyboardShowing(boolean isShowing) {
            Toast.makeText(SoftKeyBoardActivity.this, isShowing?"软键盘显示":"软键盘隐藏", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtil.detach(this,keyboardListener);
    }
}
