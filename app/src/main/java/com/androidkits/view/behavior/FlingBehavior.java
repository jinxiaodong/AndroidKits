package com.androidkits.view.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

public final class FlingBehavior extends AppBarLayout.Behavior {

    private static final String TAG = FlingBehavior.class.getName();
    private static final int TOP_CHILD_FLING_THRESHOLD = 1;
    private static final float OPTIMAL_FLING_VELOCITY = 3500;
    private static final float MIN_FLING_VELOCITY = 20;

    boolean shouldFling = false;
    float flingVelocityY = 0;

    public FlingBehavior() {
    }

    public FlingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target,
                                  int velocityX, int velocityY, int[] consumed) {

        super.onNestedPreScroll(coordinatorLayout, child, target, velocityX, velocityY, consumed);

        if (velocityY > MIN_FLING_VELOCITY) {
            shouldFling = true;
            flingVelocityY = velocityY;
        } else {
            shouldFling = false;
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target) {
        super.onStopNestedScroll(coordinatorLayout, abl, target);
        if (shouldFling) {
            Log.d(TAG, "onNestedPreScroll: running nested fling, velocityY is " + flingVelocityY);
            onNestedFling(coordinatorLayout, abl, target, 0, flingVelocityY, true);
        }
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target,
                                 float velocityX, float velocityY, boolean consumed) {

        if (target instanceof RecyclerView && velocityY < 0) {
            Log.d(TAG, "onNestedFling: target is recyclerView");
            final RecyclerView recyclerView = (RecyclerView) target;
            final View firstChild = recyclerView.getChildAt(0);
            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD;
        }

        // prevent fling flickering when going up
        if (target instanceof NestedScrollView && velocityY > 0) {
            consumed = true;
        }

        if (Math.abs(velocityY) < OPTIMAL_FLING_VELOCITY) {
            velocityY = OPTIMAL_FLING_VELOCITY * (velocityY < 0 ? -1 : 1);
        }
        Log.d(TAG, "onNestedFling: velocityY - " + velocityY + ", consumed - " + consumed);

        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}