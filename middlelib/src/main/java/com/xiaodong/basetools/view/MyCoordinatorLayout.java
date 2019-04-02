package com.xiaodong.basetools.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xiaodong.basetools.utils.LogUtil;

import androidx.coordinatorlayout.widget.CoordinatorLayout;


/**
 * Created by jinxuefen on 2019/4/2.
 * 功能描述：
 */
public class MyCoordinatorLayout extends CoordinatorLayout {

    public interface OnScrollListener {
        void onScrollStart();
        void onScrollStop();
    }

    private OnScrollListener mOnScrollListener;
    public MyCoordinatorLayout(Context context) {
        super(context);
    }

    public MyCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float mDownPosX,mDownPosY;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if (mOnScrollListener != null){
            mOnScrollListener.onScrollStart();
            LogUtil.d("MyCoordinatorLayout","onStartNestedScroll.....1");

        }
        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int axes, int type) {
        if (mOnScrollListener != null){
            mOnScrollListener.onScrollStart();
            LogUtil.d("MyCoordinatorLayout","onStartNestedScroll.....2");
        }
        return super.onStartNestedScroll(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(View target) {
        if (mOnScrollListener != null){
            mOnScrollListener.onScrollStop();
        }
        super.onStopNestedScroll(target);
    }

    @Override
    public void onStopNestedScroll(View target, int type) {
        if (mOnScrollListener != null){
            mOnScrollListener.onScrollStop();
        }
        super.onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (mOnScrollListener != null){
            mOnScrollListener.onScrollStart();
            LogUtil.d("MyCoordinatorLayout","onNestedScroll.....1");
        }
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (mOnScrollListener != null){
            mOnScrollListener.onScrollStart();
            LogUtil.d("MyCoordinatorLayout","onNestedScroll"+dxConsumed+"=="+dyConsumed);
        }
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed, int type) {
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (mOnScrollListener != null){
            mOnScrollListener.onScrollStart();
            LogUtil.d("MyCoordinatorLayout","onNestedFling.....1");
        }
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();

        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = x;
                mDownPosY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(x - mDownPosX);
                final float deltaY = Math.abs(y - mDownPosY);
                if (deltaX > deltaY) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }

}
