package com.xiaodong.androidexample.utils;

import com.xiaodong.basetools.bean.BeanWraper;

/**
 * Created by xiaodong.jin on 2018/6/11.
 * description：获取对应格式的假数据
 */

public class FormatDataUtils {

    public static BeanWraper getBeanWraper(String   name) {
        BeanWraper beanWraper = new BeanWraper();
        beanWraper.name = name;
        beanWraper.type = 1;
        return beanWraper;
    }

    public static BeanWraper getBeanWraper(Object name,int type) {
        BeanWraper beanWraper = new BeanWraper();
        beanWraper.data = name;
        beanWraper.type = type;
        return beanWraper;
    }
}
