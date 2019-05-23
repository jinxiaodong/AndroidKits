package com.common.androidexample.chapter1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.common.androidexample200.R;
import com.common.library.base.adapter.BaseRecyclerAdapter;
import com.common.library.base.adapter.BaseViewHold;
import com.common.library.bean.BeanWraper;

import java.util.List;

/**
 * Created by xiaodong.jin on 2018/6/12.
 * description：
 */

public class MenuAdapter extends BaseRecyclerAdapter<BeanWraper> {


    private onItemClickListener mOnItemClickListener;

    public MenuAdapter(Context context, List<BeanWraper> list) {
        super(context, list);
    }

    @Override
    public BaseViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WeiXinMenuViewHolder(mInflater.inflate(R.layout.weixi_menu_item, parent, false));
    }

    private class WeiXinMenuViewHolder extends BaseViewHold<BeanWraper> {


        Button btnItem;

        public WeiXinMenuViewHolder(View view) {
            super(view);

            btnItem = view.findViewById(R.id.btn_item);
        }

        @Override
        public void onBindViewHolder(final int position, List<BeanWraper> mData) {
            if (mData == null || mData.get(position) == null) {
                return;
            }
            BeanWraper beanWraper = mData.get(position);

            btnItem.setText(beanWraper.name);
            switch (position) {
                case 0:
                    btnItem.setCompoundDrawablesRelativeWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.icon_group),null,null,null);
                    break;
                case 1:
                    btnItem.setCompoundDrawablesRelativeWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.icon_add),null,null,null);

                    break;
                case 2:
                    btnItem.setCompoundDrawablesRelativeWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.icon_scan),null,null,null);

                    break;
                case 3:
                    btnItem.setCompoundDrawablesRelativeWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.icon_card),null,null,null);

                    break;
                case 4:
                    btnItem.setCompoundDrawablesRelativeWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.icon_help),null,null,null);

                    break;
            }
            btnItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, position);
                    }
                }
            });
        }

    }

    public void setOnItemClickListener(onItemClickListener clickListener) {
        mOnItemClickListener = clickListener;
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }
}
