package com.xiaodong.androidexample.customanimal;

import android.content.Intent;
import android.view.View;

import com.xiaodong.androidexample200.R;
import com.xiaodong.basetools.base.JBaseActivity;
import com.xiaodong.basetools.view.JButton;

import butterknife.InjectView;
import butterknife.OnClick;

public class CustomAnimalActivity extends JBaseActivity {


    @InjectView(R.id.button_animal)
    JButton mButtonAnimal;
    @InjectView(R.id.button_canvas)
    JButton mButtonCanvas;
    @InjectView(R.id.button_view)
    JButton mButtonView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_custom_animal;
    }

    @OnClick({R.id.button_animal, R.id.button_canvas, R.id.button_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_animal:
                Intent intent = new Intent(CustomAnimalActivity.this,AnimalActivity.class);
                startActivity(intent);
                break;
            case R.id.button_canvas:
                break;
            case R.id.button_view:
                break;
        }
    }
}
