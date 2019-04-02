package com.xiaodong.basetools.base.adapter;

import android.view.View;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lenovo on 2016/7/21.
 */
public abstract class BaseViewHold<T> extends RecyclerView.ViewHolder{
    public BaseViewHold(View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(int position,List<T> mData);
}
