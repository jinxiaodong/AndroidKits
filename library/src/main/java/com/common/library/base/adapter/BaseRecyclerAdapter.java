package com.common.library.base.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.library.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xiaodong.jin on 2019/05/23.
 * 功能描述：
 */

public class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHold> {

    private Handler handler;

    protected List<T> mData = new ArrayList<T>();
    protected Context mContext;
    protected LayoutInflater mInflater;

    private HashMap<Integer, Integer> mDefaultCount = new HashMap<>();
    //无内容
    public static final int NOTHING = R.layout.lib_default_page_no_content;
    //加载失败
    public static final int FAILED = R.layout.lib_default_page_failed;

    private int prePosition = 0;
    private final MyDataObserver myDataObserver = new MyDataObserver();

    public BaseRecyclerAdapter(Context context, List<T> list) {
        this.mContext = context;
        if (list != null) {
            this.mData.addAll(list);
        }
        mInflater = LayoutInflater.from(mContext);
        registerAdapterDataObserver(myDataObserver);
    }

    @Override
    public BaseViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NOTHING) {
            return new DefaultNothingHolder(mInflater.inflate(NOTHING, parent, false));
        } else if (viewType == FAILED) {
            return new DefaultFailedHolder(mContext, mInflater.inflate(FAILED, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDefaultCount.size() != 0) {
            if (mDefaultCount.containsKey(NOTHING) && mDefaultCount.get(NOTHING) == position) {
                return NOTHING;
            }
            if (mDefaultCount.containsKey(FAILED) && mDefaultCount.get(FAILED) == position) {
                return FAILED;
            }

        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(BaseViewHold holder, int position) {
        holder.onBindViewHolder(position, mData);
    }

    public View inflate(int resLayout, ViewGroup parent) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(mContext);
        }
        return mInflater.inflate(resLayout, parent, false);
    }

    @Override
    public int getItemCount() {
        return (mData != null ? mData.size() : 0) + mDefaultCount.size();
    }


    public List<T> getData() {
        return mData;
    }

    //显示加载失败
    public void showFailed() {
        showPage(FAILED);
    }

    //显示无内容
    public void showNothing() {
        showPage(NOTHING);
    }

    private void showPage(int resourceId) {
        prePosition = mData != null ? mData.size() : 0;
        mDefaultCount.put(resourceId, prePosition);
        notifyDataSetChanged();
    }

    private class MyDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            if (mData.size() != prePosition) {
                mDefaultCount.clear();
            }
        }
    }
}
