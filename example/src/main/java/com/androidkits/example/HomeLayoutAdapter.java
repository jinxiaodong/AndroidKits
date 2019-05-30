package com.androidkits.example;


import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by jinxuefen on 2019/4/2.
 * 功能描述：
 */

public class HomeLayoutAdapter extends BaseQuickAdapter<HomeBean, HomeLayoutViewHolder> {


    public HomeLayoutAdapter(@Nullable List<HomeBean> data) {
        super(R.layout.item_home, data);
    }

    @Override
    protected void convert(HomeLayoutViewHolder helper, HomeBean item) {
        helper.setText(R.id.text, item.index);
    }
}
