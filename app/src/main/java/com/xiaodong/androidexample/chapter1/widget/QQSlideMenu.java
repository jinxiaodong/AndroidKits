package com.xiaodong.androidexample.chapter1.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;

/**
 * Created by xiaodong.jin on 2018/6/12.
 * description：
 */

public class QQSlideMenu extends ViewGroup {
    @SuppressLint("InlinedApi")
    private static final int[] THEME_ATTRS = {
            android.R.attr.colorPrimaryDark
    };

    @IntDef({STATE_IDLE, STATE_DRAGGING, STATE_SETTLING})
    @Retention(RetentionPolicy.SOURCE)
    private @interface State {
    }

    public static final int STATE_IDLE = ViewDragHelper.STATE_IDLE;
    public static final int STATE_DRAGGING = ViewDragHelper.STATE_DRAGGING;
    public static final int STATE_SETTLING = ViewDragHelper.STATE_SETTLING;

    interface DrawerListener {
        void onDrawerOpened(View contentView, View drawerView);

        void onDrawerClosed(View contentView, View drawerView);

        void onDrawerStateChanged(View contentView, View drawerView, @State int newState);

        void onDrawerSlideOffset(View contentView, View drawerView, float slideOffset);
    }

    private static final int MIN_DRAWER_MARGIN = 64;//dp
    private static final int MIN_FLYING_VELOCITY = 400;// dp per second
    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;
    private int mMinDrawerMargin;
    private float mSlideOffset;
    private ViewDragHelper mViewDragHelper;
    private int mScrimColor = DEFAULT_SCRIM_COLOR;
    private Paint mScrimPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mInitialMotionX;
    private float mInitialMotionY;
    private float mDrawerCoefficient = 0.25f; // 0f ~ 1f
    private int mDrawState = STATE_IDLE;
    private ArrayList<DrawerListener> mListeners;
    //status bar
    private Drawable mStatusBarBackground;
    private WindowInsets mLastInsets;
    private boolean mDrawStatusBarBackground;

    public QQSlideMenu(Context context) {
        this(context, null);
    }

    public QQSlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQSlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final float density = getResources().getDisplayMetrics().density;
        mMinDrawerMargin = (int) (MIN_DRAWER_MARGIN * density + 0.5f);
        mViewDragHelper = ViewDragHelper.create(this, .5f, new DragCallback());
        mViewDragHelper.setMinVelocity(MIN_FLYING_VELOCITY * density);
        if (ViewCompat.getFitsSystemWindows(this)) {
            if (Build.VERSION.SDK_INT >= 21) {
                setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
                    @TargetApi(21)
                    @Override
                    public WindowInsets onApplyWindowInsets(View view, WindowInsets insets) {
                        final QQSlideMenu drawerLayout = (QQSlideMenu) view;
                        drawerLayout.setChildInsets(insets, mDrawStatusBarBackground);
                        return insets.consumeSystemWindowInsets();
                    }
                });
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                final TypedArray a = context.obtainStyledAttributes(THEME_ATTRS);
                try {
                    mStatusBarBackground = a.getDrawable(0);
                } finally {
                    a.recycle();
                }
            } else {
                mStatusBarBackground = null;
            }
        }
    }

    private void setChildInsets(WindowInsets insets, boolean draw) {
        mLastInsets = insets;
        mDrawStatusBarBackground = draw;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            if (isInEditMode()) {
                if (widthMode == MeasureSpec.UNSPECIFIED) {
                    widthSize = 300;
                }
                if (heightMode == MeasureSpec.UNSPECIFIED) {
                    heightSize = 300;
                }
            } else {
                throw new IllegalArgumentException(
                        "DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
        }
        setMeasuredDimension(widthSize, heightSize);
        final boolean applyInsets = mLastInsets != null && ViewCompat.getFitsSystemWindows(this);
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            if (applyInsets) {
                if (ViewCompat.getFitsSystemWindows(child)) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        WindowInsets wi = mLastInsets;
                        wi = wi.replaceSystemWindowInsets(wi.getSystemWindowInsetLeft(),
                                wi.getSystemWindowInsetTop(), 0,
                                wi.getSystemWindowInsetBottom());
                        child.dispatchApplyWindowInsets(wi);
                    }
                }
            }
            if (isContentView(child)) {
                // Content views get measured at exactly the layout's size.
                final int contentWidthSpec = MeasureSpec.makeMeasureSpec(
                        widthSize - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY);
                final int contentHeightSpec = MeasureSpec.makeMeasureSpec(
                        heightSize - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY);
                child.measure(contentWidthSpec, contentHeightSpec);
            } else if (isDrawerView(child)) {
                final int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec,
                        mMinDrawerMargin + lp.leftMargin + lp.rightMargin,
                        lp.width);
                final int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec,
                        lp.topMargin + lp.bottomMargin,
                        lp.height);
                child.measure(drawerWidthSpec, drawerHeightSpec);
            } else {
                throw new IllegalStateException("must only be one content and one drawer");
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final float mSlideOffset = this.mSlideOffset;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            if (isContentView(child)) {
                int l1 = (int) (lp.leftMargin + Math.min(getDrawerView().getMeasuredWidth() * mSlideOffset, getDrawerView().getMeasuredWidth()));
                child.layout(l1, lp.topMargin,
                        l1 + child.getMeasuredWidth(),
                        lp.topMargin + child.getMeasuredHeight());
            } else { // Drawer, if it wasn't onMeasure would have thrown an exception.
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
                float initLeft = -mDrawerCoefficient * childWidth;
                float oldSlideOffset = (initLeft - child.getLeft()) / initLeft;
                int newChildLeft = (int) (initLeft - mSlideOffset * initLeft);
                child.layout(newChildLeft, lp.topMargin, newChildLeft + childWidth,
                        lp.topMargin + childHeight);
                final int newVisibility = mSlideOffset > 0 ? VISIBLE : INVISIBLE;
                if (child.getVisibility() != newVisibility) {
                    child.setVisibility(newVisibility);
                }
                //dispatch newest mSlideOffset
                if (oldSlideOffset != mSlideOffset) {
                    dispatchDrawerSlideOffset(mSlideOffset);
                }
            }
            Log.e("onLayout", getDrawerView().getRight() + ", " + getContentView().getLeft());
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mDrawStatusBarBackground && mStatusBarBackground != null) {
            final int inset;
            if (Build.VERSION.SDK_INT >= 21) {
                inset = mLastInsets != null ? mLastInsets.getSystemWindowInsetTop() : 0;
            } else {
                inset = 0;
            }
            if (inset > 0) {
                mStatusBarBackground.setBounds(getContentView().getLeft(), 0, getWidth(), inset);
                mStatusBarBackground.draw(canvas);
            }
        }
        if (mSlideOffset > 0) {
            final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
            final int imag = (int) (baseAlpha * mSlideOffset);
            final int color = imag << 24 | (mScrimColor & 0xffffff);
            mScrimPaint.setColor(color);
            canvas.drawRect(getContentView().getLeft(), 0, getWidth(), getHeight(), mScrimPaint);
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Log.e("drawChild", getDrawerView().getRight() + ", " + getContentView().getLeft());
        boolean isDrawContent = isContentView(child);
        View contentView = getContentView();
        final float mSlideOffset = this.mSlideOffset;
        int restoreCount = canvas.save();
        int clipLeft = 0;
        int clipRight = getWidth();
        if (!isDrawContent && clipRight > contentView.getLeft()) {
            clipRight = contentView.getLeft();
        }
        if (isDrawContent && clipLeft < contentView.getLeft()) {
            clipLeft = contentView.getLeft();
        }
        canvas.clipRect(clipLeft, 0, clipRight, getHeight());
        boolean result = super.drawChild(canvas, child, drawingTime);
        canvas.restoreToCount(restoreCount);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean interceptForDrag = mViewDragHelper.shouldInterceptTouchEvent(ev);
        boolean interceptForTap = false;
        final int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            final float x = ev.getX();
            final float y = ev.getY();
            mInitialMotionX = x;
            mInitialMotionY = y;
            if (mSlideOffset > 0) {
                final View child = mViewDragHelper.findTopChildUnder((int) x, (int) y);
                if (child != null && isContentView(child)) {
                    interceptForTap = true;
                }
            }
        }
        return interceptForDrag || interceptForTap;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        final int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                final float x = event.getX();
                final float y = event.getY();
                mInitialMotionX = x;
                mInitialMotionY = y;
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                final float x = event.getX();
                final float y = event.getY();
                final View touchedView = mViewDragHelper.findTopChildUnder((int) x, (int) y);
                if (touchedView != null && isContentView(touchedView)) {
                    final float dx = x - mInitialMotionX;
                    final float dy = y - mInitialMotionY;
                    final int slop = mViewDragHelper.getTouchSlop();
                    if (dx * dx + dy * dy < slop * slop) {
                        closeDrawer();
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        if (disallowIntercept) {
            closeDrawer();
        }
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public void requestLayout() {
        if (!isInLayout()) {
            super.requestLayout();
        }
    }

    public void openDrawer() {
        openOrCloseDrawer(true);
    }

    public void closeDrawer() {
        openOrCloseDrawer(false);
    }

    private void openOrCloseDrawer(boolean open) {
        int finalLeft = open ? getDrawerView().getMeasuredWidth() : 0;
        if (finalLeft != getContentView().getLeft()) {
            mViewDragHelper.smoothSlideViewTo(getContentView(), finalLeft, getContentView().getTop());
            invalidate();
        }
    }

    public View getContentView() {
        checkChildCountValid();
        return getChildAt(1);
    }

    public View getDrawerView() {
        checkChildCountValid();
        return getChildAt(0);
    }

    public void addDrawerListener(DrawerListener drawerListener) {
        if (drawerListener == null) {
            return;
        }
        if (mListeners == null) {
            mListeners = new ArrayList<>();
        }
        mListeners.add(drawerListener);
    }

    public void setDrawerCoefficient(@FloatRange(from = 0f, to = 1f) float coefficient) {
        mDrawerCoefficient = coefficient;
        invalidate();
    }

    public void setStatusBarBackgroundResource(@DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mStatusBarBackground = getResources().getDrawable(resId, getContext().getTheme());
        } else {
            mStatusBarBackground = getResources().getDrawable(resId);
        }
        invalidate();
    }

    public void setStatusBarBackground(Drawable drawable) {
        mStatusBarBackground = drawable;
        invalidate();
    }

    public void setStatusBarBackgroundColor(int color) {
        mStatusBarBackground = new ColorDrawable(color);
        invalidate();
    }

    public void setDrawStatusBarBackground(boolean draw) {
        mDrawStatusBarBackground = draw;
        requestLayout();
    }

    public boolean getDrawStatusBarBackground() {
        return mDrawStatusBarBackground;
    }

    public void setScrimColor(int color) {
        mScrimColor = color;
        invalidate();
    }

    public int getScrimColor() {
        return mScrimColor;
    }

    private void checkChildCountValid() {
        if (getChildCount() != 2) {
            throw new IllegalArgumentException("child count must be 2");
        }
    }

    private boolean isDrawerView(View child) {
        return indexOfChild(child) == 0;
    }

    private boolean isContentView(View child) {
        return indexOfChild(child) == 1;
    }

    private void dispatchDrawerOpenOrClose(boolean opened) {
        if (mListeners == null) {
            return;
        }
        for (DrawerListener listener : mListeners) {
            if (opened) {
                listener.onDrawerOpened(getContentView(), getDrawerView());
            } else {
                listener.onDrawerClosed(getContentView(), getDrawerView());
            }
        }
    }

    private void dispatchDrawerState(@State int newState) {
        if (mListeners == null) {
            return;
        }
        for (DrawerListener listener : mListeners) {
            listener.onDrawerStateChanged(getContentView(), getDrawerView(), newState);
        }
    }

    private void dispatchDrawerSlideOffset(@FloatRange(from = 0f, to = 1f) float slideOffset) {
        if (mListeners == null) {
            return;
        }
        for (DrawerListener listener : mListeners) {
            listener.onDrawerSlideOffset(getContentView(), getDrawerView(), slideOffset);
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.MarginLayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MarginLayoutParams ? (MarginLayoutParams) p : new MarginLayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MarginLayoutParams && super.checkLayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        final SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (ss.slideOffset >= 0f && ss.slideOffset <= 1f) {
            mSlideOffset = ss.slideOffset;
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        SavedState ss = new SavedState(parcelable);
        ss.slideOffset = mSlideOffset;
        return ss;
    }

    protected static class SavedState extends AbsSavedState {
        float slideOffset = 0f;

        public SavedState(Parcel in, ClassLoader loader) {
            super(in, loader);
            slideOffset = in.readFloat();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeFloat(slideOffset);
        }

        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, null);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        return isContentView(getChildAt(i)) ? 1 : 0;
    }

    private class DragCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return isDrawerView(child) || isContentView(child);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int finalLeft;
            int dragRange = getViewHorizontalDragRange(releasedChild);
            if (isDrawerView(releasedChild)) {
                finalLeft = xvel > 0 || (mSlideOffset > 0.5f && xvel == 0) ? 0 : (int) (-dragRange * mDrawerCoefficient);
            } else {
                finalLeft = xvel > 0 || (mSlideOffset > 0.5f && xvel == 0) ? dragRange : 0;
            }
            mViewDragHelper.settleCapturedViewAt(finalLeft, releasedChild.getTop());
            invalidate();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (getContentView().getLeft() != getDrawerView().getRight()) {
                ViewCompat.offsetLeftAndRight(changedView, -dx);
            }
            float offset;
            float dragRange = getViewHorizontalDragRange(changedView);
            float initLeft = -dragRange * mDrawerCoefficient;
            if (isContentView(changedView)) {
                offset = left / dragRange;
            } else {
                offset = (initLeft - left) / initLeft;
            }
            if (offset != mSlideOffset) {
                mSlideOffset = offset;
                requestLayout();
            }
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == STATE_IDLE) {
                if (mSlideOffset == 0) {
                    dispatchDrawerOpenOrClose(false);
                } else if (mSlideOffset == 1) {
                    dispatchDrawerOpenOrClose(true);
                }
            }
            if (state != mDrawState) {
                mDrawState = state;
                dispatchDrawerState(state);
            }
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return getDrawerView().getWidth();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int dragRange = getViewHorizontalDragRange(child);
            int result;
            if (isDrawerView(child)) {
                result = Math.round(Math.max(-dragRange * mDrawerCoefficient, Math.min(child.getLeft() + dx * mDrawerCoefficient, 0f)));
            } else {
                result = Math.min(Math.max(left, 0), dragRange);
            }
            return result;
        }
    }
}
