package com.androidkits.example.chapter1.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkits.example.R;
import com.androidkits.example.utils.keyboardPanelUtils.KeyboardUtil;

/**
 * Created by xiaodong.jin on 2019/05/10.
 * description：
 */
public class CustomerEditText extends RelativeLayout {

    private TextView tvTitle;
    private EditText etName;
    private LinearLayout llEtContent;
    private Context context;


    public CustomerEditText(Context context) {
        this(context, null);
    }

    public CustomerEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
        initListener();
    }


    private void initView(Context context) {
        View inflate = inflate(context, R.layout.layout_customer_edittext, this);
        tvTitle = inflate.findViewById(R.id.tv_title);
        etName = inflate.findViewById(R.id.et_name);
        llEtContent = inflate.findViewById(R.id.ll_et_content);
    }

    private void initListener() {

        etName.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etName.setHint("");
                    tvTitle.setVisibility(VISIBLE);
                    tvTitle.setTextColor(Color.parseColor("#00A0E9"));
                } else {
                    if (TextUtils.isEmpty(etName.getText().toString())) {
                        tvTitle.setVisibility(GONE);
                        etName.setHint(tvTitle.getText().toString());
                    } else {
                        tvTitle.setVisibility(VISIBLE);
                        tvTitle.setTextColor(Color.parseColor("#999999"));
                    }
                }
            }
        });
        llEtContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.requestFocus();
                KeyboardUtil.showKeyboard(etName);
            }
        });

    }


    /**
     * EditText getText()方法
     *
     * @return
     */
    public Editable getText() {
        if (etName == null) {
            etName = new ClearEditText(context);
        }
        return etName.getText();
    }

    public void setHint(String hint) {
        if (etName != null) {
            etName.setHint(hint);
        }
    }

    public void setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }
}
