package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * @author shilei.yu
 * @date 2018/9/5
 */

public class VerticalDragLayout extends ViewGroup {

    private boolean isBeingDragged = false;
    private int mTouchSlop;

    private float mLastMotionY;
    private float mLastMotionX;

    public VerticalDragLayout(Context context) {
        super(context);
    }

    public VerticalDragLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int size = MeasureSpec.getSize(widthMeasureSpec);
        int size1 = MeasureSpec.getSize(heightMeasureSpec);
        int wm = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        int hm = MeasureSpec.makeMeasureSpec(size1, MeasureSpec.EXACTLY);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            measureChild(child, wm, hm);
        }
        setMeasuredDimension(size, size1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View childTop = getChildAt(0);
        View childBottom = getChildAt(1);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int topChildBottom = paddingTop + childTop.getMeasuredHeight();
        childTop.layout(paddingLeft, paddingTop, paddingLeft + childTop.getMeasuredWidth(), topChildBottom);
        childBottom.layout(paddingLeft, topChildBottom, paddingLeft + childBottom.getMeasuredWidth(), topChildBottom + childBottom.getMeasuredHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isBeingDragged) {
                    int yDiff = (int) (y - mLastMotionY);
                    scrollBy(0, -yDiff);
                }
                break;
            case MotionEvent.ACTION_UP:
                isBeingDragged = false;
                break;
            default:
                break;
        }
        mLastMotionY = y;
        mLastMotionX = x;
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isBeingDragged) {
            return true;
        }
        float y = ev.getY();
        float x = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float yDiff = mLastMotionY - y;
                float xDiff = mLastMotionX - x;
                float absYDiff = Math.abs(yDiff);
                if (absYDiff > mTouchSlop && absYDiff * 0.5f > Math.abs(xDiff)) {
                    isBeingDragged = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                isBeingDragged = false;
                break;
            default:
                break;
        }

        mLastMotionY = y;
        mLastMotionX = x;

        return false;
    }

}
