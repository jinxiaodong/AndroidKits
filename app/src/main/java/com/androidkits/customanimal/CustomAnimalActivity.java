package com.androidkits.customanimal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.common.androidexample200.R;
import com.common.library.commons.common.CommonActivity;
import com.common.library.view.widgets.JButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;

public class CustomAnimalActivity extends CommonActivity {



    @BindView(R.id.button_animal)
    JButton mButtonAnimal;
    @BindView(R.id.button_canvas)
    JButton mButtonCanvas;
    @BindView(R.id.button_view)
    JButton mButtonView;

    @Override
    protected int getHeaderLayoutId() {
        return DEFAULT_HEADER;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_custom_animal;
    }

    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        setTitle("自定义动画");
        showBack();
    }

    @OnClick({R.id.button_animal, R.id.button_canvas, R.id.button_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_animal:
                Intent intent = new Intent(CustomAnimalActivity.this, AnimalActivity.class);
                startActivity(intent);
                break;
            case R.id.button_canvas:
                break;
            case R.id.button_view:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
