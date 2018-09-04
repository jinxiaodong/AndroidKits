package com.xiaodong.androidexample.customanimal.animal;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.xiaodong.androidexample.utils.GlideUtil;
import com.xiaodong.androidexample200.R;
import com.xiaodong.basetools.base.JBaseActivity;
import com.xiaodong.basetools.utils.SystemBarUtil;

public class InterpolatorActivity extends JBaseActivity {


    private ImageView mImageView;

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
        showOrHideBackButton(true);
        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);

    }

    @Override
    protected void initData(Bundle onSavedInstance) {
        super.initData(onSavedInstance);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce_interpolator);
        mImageView = findViewById(R.id.view);
        GlideUtil.load(this, R.drawable.loading, mImageView);
        mImageView.startAnimation(animation);
    }
}
