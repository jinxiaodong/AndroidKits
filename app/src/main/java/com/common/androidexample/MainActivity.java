package com.common.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.common.androidexample.customanimal.CustomAnimalActivity;
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

public class MainActivity extends CommonActivity {


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
        mList.add(FormatDataUtils.getBeanWraper("自定义控件三部曲"));
        mList.add(FormatDataUtils.getBeanWraper("首页复杂布局"));

    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);
        /*禁用页面滑动退出*/
        setEnableBackLayout(false);

        mListAdapter = new ListAdapter(this, mList);
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
                    case "chapter1":
                        intent = new Intent(MainActivity.this, Chapter1Activity.class);
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
                    case "自定义控件三部曲":
                        intent = new Intent(MainActivity.this, CustomAnimalActivity.class);
                        break;
                    case "首页复杂布局":
                        intent = new Intent(MainActivity.this, HomeLayoutActivity.class);
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
        mRecycleview.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleview.setAdapter(mListAdapter);
    }
}
