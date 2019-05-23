package com.common.androidexample;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by xiaodong.jin on 2019/4/2.
 * description：
 */
public class HomeBean implements MultiItemEntity {

    public String index;

    @Override
    public int getItemType() {
        return 0;
    }
}
