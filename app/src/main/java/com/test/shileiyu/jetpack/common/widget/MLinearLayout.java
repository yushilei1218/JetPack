package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.test.shileiyu.jetpack.R;

/**
 * @author shilei.yu
 * @date 2018/8/27
 */

public class MLinearLayout extends LinearLayout {
    private static final String TAG = "MLinearLayout";

    private ViewDragHelper mDragHelper;

    public MLinearLayout(Context context) {
        this(context, null);
    }

    public MLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child.getId() == R.id.test_tv_1;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//                return super.clampViewPositionHorizontal(children, left, dx);
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
//                return super.clampViewPositionVertical(children, top, dy);
                return top;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
//                super.onViewReleased();
                mDragHelper.settleCapturedViewAt(0, 0);
                ViewCompat.postInvalidateOnAnimation(MLinearLayout.this);
            }

            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {
                return super.getViewHorizontalDragRange(child);
            }

            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return Integer.MAX_VALUE >> 2;
                //return super.getViewVerticalDragRange(children);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "onLayout");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
