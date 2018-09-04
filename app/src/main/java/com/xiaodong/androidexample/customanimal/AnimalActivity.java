package com.xiaodong.androidexample.customanimal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiaodong.androidexample.customanimal.animal.InterpolatorActivity;
import com.xiaodong.androidexample.utils.FormatDataUtils;
import com.xiaodong.androidexample200.R;
import com.xiaodong.basetools.base.JBaseActivity;
import com.xiaodong.basetools.base.ListAdapter;
import com.xiaodong.basetools.bean.BeanWraper;
import com.xiaodong.basetools.utils.SystemBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class AnimalActivity extends JBaseActivity {

    @InjectView(R.id.recycleview)
    RecyclerView mRecycleview;

    private ListAdapter mListAdapter;
    private List<BeanWraper> mList = new ArrayList<>();

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_animal;
    }

    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);

        mList.add(FormatDataUtils.getBeanWraper("Interpolator插值器"));

    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        setTitle("自定义控件三部曲之动画");
        showOrHideBackButton(true);
        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);

        mListAdapter = new ListAdapter(this, mList);

        mRecycleview.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleview.setAdapter(mListAdapter);
    }

    @Override
    protected void initListener(Bundle onSavedInstance) {
        super.initListener(onSavedInstance);
        mListAdapter.setOnButtonClick(new ListAdapter.onButtonClick() {
            @Override
            public void onBtnClick(View view, int position) {
                BeanWraper beanWraper = mList.get(position);
                Intent intent = null;
                switch (beanWraper.name) {
                    case "Interpolator插值器":
                        intent = new Intent(AnimalActivity.this, InterpolatorActivity.class);
                        break;

                }
                if (intent == null) {
                    return;
                }
                startActivity(intent);
            }
        });
    }


    @Override
    protected void initData(Bundle onSavedInstance) {
        super.initData(onSavedInstance);
    }
}
