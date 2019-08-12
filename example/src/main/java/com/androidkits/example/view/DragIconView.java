package com.androidkits.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.common.library.utils.DeviceUtil;

import androidx.annotation.Nullable;

/**
 * Created by xiaodong.jin on 2019/07/30.
 * description：
 */
public class DragIconView extends View {

    private Paint mPaint;

    public DragIconView(Context context) {
        this(context, null);
    }

    public DragIconView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resultWidth;
        int resultHeight;

        int specWMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWSize = MeasureSpec.getSize(widthMeasureSpec);
        resultWidth = myMeasure(specWMode, specWSize, DeviceUtil.dp2px(20));

        int specHMode = MeasureSpec.getMode(heightMeasureSpec);
        int specHSize = MeasureSpec.getSize(heightMeasureSpec);

        resultHeight = myMeasure(specHMode, specHSize, DeviceUtil.dp2px(30));
        setMeasuredDimension(resultWidth, resultHeight);
    }

    /**
     * @param specMode 测量模式
     * @param specSize 测量大小
     * @param result   在非精确测量模式中用来约束的大小
     * @return
     */
    private int myMeasure(int specMode, int specSize, int result) {
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(specSize, result);
        } else {

        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), mPaint);
        mPaint.setColor(Color.parseColor("#FF00A0E9"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DeviceUtil.dp2px(2));
        canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), mPaint);
    }
}
