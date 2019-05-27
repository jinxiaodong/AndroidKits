package com.androidkits.chapter1;

import android.os.Bundle;

import com.androidkits.chapter1.widget.CustomerEditText;
import com.common.androidexample200.R;

import androidx.appcompat.app.AppCompatActivity;

public class EditTextActivity extends AppCompatActivity {

    private CustomerEditText mCustomeredittext;
    private CustomerEditText mCustomeredittext2;
    private CustomerEditText mCustomeredittext3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        mCustomeredittext = findViewById(R.id.customeredittext);
        mCustomeredittext2 = findViewById(R.id.customeredittext2);
        mCustomeredittext3 = findViewById(R.id.customeredittext3);
        mCustomeredittext.setHint("测试1");
        mCustomeredittext.setTitle("测试1");
        mCustomeredittext2.setHint("测试2");
        mCustomeredittext2.setTitle("测试2");
        mCustomeredittext3.setHint("测试3");
        mCustomeredittext3.setTitle("测试3");
    }
}
