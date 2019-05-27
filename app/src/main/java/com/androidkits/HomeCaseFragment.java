package com.androidkits;

import android.os.Bundle;

import com.common.androidexample200.R;
import com.common.library.commons.common.CommonFragment;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jinxuefen on 2019/4/2.
 * 功能描述：
 */

public class HomeCaseFragment extends CommonFragment {


    private RecyclerView recycleview;
    private ArrayList<HomeBean> mList;
    private int mIndex;


    public static HomeCaseFragment newInstance(int index) {
        HomeCaseFragment fragment = new HomeCaseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getHeaderLayoutId() {
        return CUSTOMER_HEADER;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home_case;
    }


    @Override
    protected void initValue(Bundle savedInstanceState) {
        mIndex = getArguments().getInt("index");
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new LinearLayoutManager(mContext));
        mList = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            HomeBean homeBean = new HomeBean();
            homeBean.index = "我是第" + mIndex + "个Fragment中的第" + (i + 1) + "item";
            mList.add(homeBean);
        }
        recycleview.setAdapter(new HomeLayoutAdapter(mList));
    }

    @Override
    protected void initListener(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
