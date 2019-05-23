package com.common.androidexample.customanimal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.common.androidexample.customanimal.animal.InterpolatorActivity;
import com.common.androidexample.utils.FormatDataUtils;
import com.common.androidexample200.R;
import com.common.library.base.adapter.ListAdapter;
import com.common.library.bean.BeanWraper;
import com.common.library.commons.common.CommonActivity;
import com.common.library.utils.SystemBarUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.InjectView;

public class AnimalActivity extends CommonActivity {

    @InjectView(R.id.recycleview)
    RecyclerView mRecycleview;

    private ListAdapter mListAdapter;
    private List<BeanWraper> mList = new ArrayList<>();

    @Override
    protected int getHeaderLayoutId() {
        return DEFAULT_HEADER;
    }

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
        showBack();
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
    protected void initData() {
        super.initData();
    }
}
