package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @author shilei.yu
 * @date 2018/9/6
 */

public class DragCoordinatorLayout extends CoordinatorLayout {

    private int mTouchSlop;
    private float mLastTouchX;
    private float mLastTouchY;
    private boolean mIsDragging = false;

    private OnDragListener mDragListener;

    private PointF mStartDragAxis = new PointF();

    public void setDragListener(OnDragListener dragListener) {
        mDragListener = dragListener;
    }

    public DragCoordinatorLayout(Context context) {
        super(context, null);
    }

    public DragCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();
    }

    public boolean dispatchTouchEventSuper(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mDragListener == null) {
            return dispatchTouchEventSuper(ev);
        }
        float cx = ev.getX();
        float cy = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mIsDragging) {
                    mIsDragging = false;
                    mDragListener.onRelease(cx - mStartDragAxis.x, cy - mStartDragAxis.y);
                    return true;
                } else {
                    return dispatchTouchEventSuper(ev);
                }

            case MotionEvent.ACTION_DOWN:
                mLastTouchX = cx;
                mLastTouchY = cy;
                dispatchTouchEventSuper(ev);
                return true;

            case MotionEvent.ACTION_MOVE:
                float deltaX = cx - mLastTouchX;
                float deltaY = cy - mLastTouchY;
                boolean moveDown = deltaY > 0;
                if (!mIsDragging && moveDown
                        && deltaY > Math.abs(deltaX) && deltaY > mTouchSlop
                        && mDragListener.isCanDragDown()
                        ) {
                    mIsDragging = true;
                    mStartDragAxis.set(mLastTouchX, mLastTouchY);
                }

                if (mIsDragging) {
                    //
                    mDragListener.onDrag(cx - mStartDragAxis.x, cy - mStartDragAxis.y);
                    return true;
                } else {
                    return dispatchTouchEventSuper(ev);
                }

            default:
                return dispatchTouchEventSuper(ev);

        }
    }


    public interface OnDragListener {
        boolean isCanDragDown();

        void onDrag(float distanceX, float distanceY);

        void onRelease(float distanceX, float distanceY);
    }
}
