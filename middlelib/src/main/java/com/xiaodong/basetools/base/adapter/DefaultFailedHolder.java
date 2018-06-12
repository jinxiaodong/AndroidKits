package com.xiaodong.basetools.base.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.xiaodong.basetools.R;
import com.xiaodong.basetools.base.JBaseActivity;

import java.util.List;

/**
 * Created by shijia on 2016/9/23.
 */
public class DefaultFailedHolder extends BaseViewHold {

    public DefaultFailedHolder(final Context mContext, View itemView) {
        super(itemView);
        Button bt= (Button) itemView.findViewById(R.id.bt_refresh);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((JBaseActivity)mContext).reRequestData();
            }
        });

    }

    @Override
    public void onBindViewHolder(int position, List mData) {

    }
}
