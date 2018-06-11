package com.xiaodong.basetools.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaodong.basetools.view.JButton;
import com.xiaodong.basetools.R;
import com.xiaodong.basetools.bean.BeanWraper;

import java.util.List;

/**
 * Created by xiaodong.jin on 2018/6/11.
 * description：
 */

public class ListAdapter extends RecyclerView.Adapter{


    private Context mContext;
    private List<BeanWraper> mData;
    private onButtonClick mOnButtonClick;

    public ListAdapter(Context mcontext, List<BeanWraper> data) {
        this.mContext = mcontext;
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListViewHolder holder1 = (ListViewHolder) holder;
        holder1.onBindViewHolder(position, mData);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        JButton btn;

        public ListViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.button);
        }

        private void onBindViewHolder(final int position, List<BeanWraper> mData) {
            BeanWraper beanWraper = mData.get(position);
            if (beanWraper == null) {
                return;
            }

            btn.setText(beanWraper.name);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnButtonClick.onBtnClick(v, position);
                }
            });
        }
    }


    public void setOnButtonClick(onButtonClick clickListener) {
        mOnButtonClick = clickListener;
    }

    public interface onButtonClick {
        void onBtnClick(View view, int position);
    }
}
