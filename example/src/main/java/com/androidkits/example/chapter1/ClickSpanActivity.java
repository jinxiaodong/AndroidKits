package com.androidkits.example.chapter1;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.androidkits.example.R;
import com.common.library.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ClickSpanActivity extends AppCompatActivity {

    private TextView tv;

    private List<String> name = new ArrayList<>();
    private String result;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_span);
        tv = findViewById(R.id.tv);
        name.add("张三");
        name.add("张三");
        name.add("李四");
        name.add("王五");
        name.add("赵六");
        name.add("赵六");
        name.add("王二麻子");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < name.size(); i++) {
            stringBuilder.append(name.get(i));
            if (i == name.size() - 2) {
                stringBuilder.append("或");
            } else {
                stringBuilder.append("、");
            }

        }
        result = stringBuilder.toString();
        SpannableString spannable = new SpannableString(result);
        for (int i = 0; i < name.size(); i++) {
            int finalI = i;
            spannable.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    ToastUtils.show(name.get(finalI));
                }
            }, caculateStart(i)-name.get(i).length(), caculateStart(i), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spannable);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

    }


    private int caculateStart(int index) {
        if (index <0) {
            return 0;
        }
        return result.indexOf(name.get(index), caculateStart(index-1))+name.get(index).length();
    }
}
