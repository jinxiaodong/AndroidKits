package com.xiaodong.androidexample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiaodong.androidexample.utils.FormatDataUtils;
import com.xiaodong.androidexample200.R;
import com.xiaodong.basetools.base.JBaseActivity;
import com.xiaodong.basetools.base.ListAdapter;
import com.xiaodong.basetools.bean.BeanWraper;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MainActivity extends JBaseActivity {


    @InjectView(R.id.recycleview)
    RecyclerView mRecycleview;

    private ListAdapter mListAdapter;
    private List<BeanWraper> mList =new ArrayList<>();

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);
        setTitle("Android实例200");
        mList.add(FormatDataUtils.getBeanWraper("chapter1"));
        mList.add(FormatDataUtils.getBeanWraper("chapter2"));
        mList.add(FormatDataUtils.getBeanWraper("chapter3"));
        mList.add(FormatDataUtils.getBeanWraper("chapter4"));
        mList.add(FormatDataUtils.getBeanWraper("chapter5"));
        mList.add(FormatDataUtils.getBeanWraper("chapter6"));

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
                switch (beanWraper.name) {
                    case "chapter1":

                        break;
                    case "chapter2":

                        break;
                    case "chapter3":

                        break;
                    case "chapter4":

                        break;
                    case "chapter5":

                        break;
                    case "chapter6":

                        break;
                    case "chapter7":

                        break;
                }
            }
        });

    }


    @Override
    protected void initData(Bundle onSavedInstance) {
        super.initData(onSavedInstance);
    }

}
