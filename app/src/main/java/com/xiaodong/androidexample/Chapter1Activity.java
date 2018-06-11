package com.xiaodong.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiaodong.androidexample.chapter1.WeiXinMenuActivity;
import com.xiaodong.androidexample.utils.FormatDataUtils;
import com.xiaodong.androidexample200.R;
import com.xiaodong.basetools.base.JBaseActivity;
import com.xiaodong.basetools.base.ListAdapter;
import com.xiaodong.basetools.bean.BeanWraper;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class Chapter1Activity extends JBaseActivity {



    @InjectView(R.id.recycleview)
    RecyclerView mRecycleview;

    private ListAdapter mListAdapter;
    private List<BeanWraper> mList = new ArrayList<>();

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_chapter1;
    }

    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);
        setTitle("Android实例200");
        mList.add(FormatDataUtils.getBeanWraper("微信右上角弹出菜单"));

    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);


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
                    case "微信右上角弹出菜单" :
                         intent = new Intent(Chapter1Activity.this, WeiXinMenuActivity.class);
                        break;
                }
                if(intent == null) {
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
