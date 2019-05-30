package com.androidkits.example.customanimal.animal;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.androidkits.example.R;
import com.androidkits.example.utils.GlideUtil;
import com.common.library.commons.common.CommonActivity;
import com.common.library.utils.SystemBarUtil;

public class InterpolatorActivity extends CommonActivity {


    private ImageView mImageView;

    @Override
    protected int getHeaderLayoutId() {
        return DEFAULT_HEADER;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_interpolator;
    }

    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        setTitle("Interpolator插值器");
        showBack();
        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);

    }

    @Override
    protected void initData() {
        super.initData();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce_interpolator);
        mImageView = findViewById(R.id.view);
        GlideUtil.load(this, R.drawable.loading, mImageView);
        mImageView.startAnimation(animation);
    }
}
