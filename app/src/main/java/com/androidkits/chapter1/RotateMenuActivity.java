package com.androidkits.chapter1;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.androidkits.chapter1.widget.SatelliteMenu;
import com.common.androidexample200.R;
import com.common.library.commons.common.CommonActivity;
import com.common.library.utils.SystemBarUtil;

import butterknife.InjectView;

/**
 * Created by xiaodong.jin on 2018/7/17.
 * description：可以收放的旋转菜单
 */
public class RotateMenuActivity extends CommonActivity {


    @InjectView(R.id.satellitemMenu)
    SatelliteMenu mSatellitemMenu;

    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_rotate_menu;
    }


    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);
        setTitle("收放的旋转菜单");
    }

    @Override
    protected void initListener(Bundle onSavedInstance) {
        super.initListener(onSavedInstance);
        mSatellitemMenu.setOnMenuItemClickListener(new SatelliteMenu.OnSatelliteMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Toast.makeText(RotateMenuActivity.this, pos + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }


}
