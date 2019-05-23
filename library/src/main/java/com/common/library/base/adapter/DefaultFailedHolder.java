package com.common.library.base.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.common.library.R;

import java.util.List;

/**
 * Created by xiaodong.jin on 2019/05/23.
 * 功能描述：
 */

public class DefaultFailedHolder extends BaseViewHold {

    public DefaultFailedHolder(final Context mContext, View itemView) {
        super(itemView);
        Button bt= (Button) itemView.findViewById(R.id.bt_refresh);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((CommonActivity)mContext).reRequestData();
            }
        });

    }

    @Override
    public void onBindViewHolder(int position, List mData) {

    }
}
