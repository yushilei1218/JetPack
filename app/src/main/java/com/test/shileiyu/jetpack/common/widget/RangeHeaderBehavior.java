package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author shilei.yu
 * @date 2018/8/30
 */

public class RangeHeaderBehavior extends ViewOffsetBehavior<RangeHeaderLayout> {
    public static final String TAG = "MyBehavior";


    public RangeHeaderBehavior() {
    }

    public RangeHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, RangeHeaderLayout child, MotionEvent ev) {
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, RangeHeaderLayout child, MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RangeHeaderLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {

        boolean b = (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0
                && child.hasFixedRange()
                && coordinatorLayout.getHeight() - directTargetChild.getHeight() <= child.getHeight();
        Log.d(TAG, "onStartNestedScroll " + b);
        return b;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RangeHeaderLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        int top = child.getTop();
        Log.d(TAG, "onNestedPreScroll dy=" + dy + " child top=" + top);

        if (dy != 0) {
            if (dy < 0) {
                // We're scrolling down
                ViewCompat.offsetTopAndBottom(child, -dy);
            } else {
                // We're scrolling up
                ViewCompat.offsetTopAndBottom(child, +dy);
            }
            consumed[1] = dy;
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RangeHeaderLayout child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.d(TAG, "onNestedScroll  dyConsumed=" + dyConsumed + " dyUnconsumed=" + dyUnconsumed);

    }
}
