package com.androidkits.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by xiaodong.jin on 2019/08/23.
 * 功能描述：
 */

public class DragRotatleView extends AppCompatImageView implements View.OnTouchListener {
    protected int lastX;
    protected int lastY;

    //初始的旋转角度
    private float oriRotation = 0;
    private static String TAG = "sxlwof";
    private int oriLeft, oriRight, oriTop, oriBottom;

    public DragRotatleView(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public DragRotatleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public DragRotatleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        if (action == MotionEvent.ACTION_DOWN) {
            oriLeft = v.getLeft();
            oriRight = v.getRight();
            oriTop = v.getTop();
            oriBottom = v.getBottom();
            lastY = (int) event.getRawY();
            lastX = (int) event.getRawX();

            oriRotation = v.getRotation();
            Log.d(TAG, "ACTION_DOWN: " + oriRotation);
        }

        delDrag(v, event, action);
        invalidate();
        return false;
    }

    /**
     * 处理拖动事件
     *
     * @param v
     * @param event
     * @param action
     */
    protected void delDrag(View v, MotionEvent event, int action) {
        switch (action) {
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }
    }

}