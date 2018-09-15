package com.test.shileiyu.jetpack.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.test.shileiyu.jetpack.BaseApp;
import com.test.shileiyu.jetpack.common.util.Util;

public class PageLinearLayout extends ViewGroup {
    private DragCallback callback;

    private int mTouchSlop;

    private float mLastY;

    private float mStartDragY;

    private boolean isBeingDragged = false;

    private int mPage = 1;

    private int mScrollPageDistance = Util.dp2px(BaseApp.sApplication, 100);

    public void setCallback(DragCallback callback) {
        this.callback = callback;
    }

    public PageLinearLayout(Context context) {
        this(context, null);
    }

    public PageLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float y = ev.getY();
        if (callback != null) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mLastY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float deltaY = y - mLastY;
                    if (isBeingDragged) {
                        offsetChildTopAndBottom((int) deltaY);
                        mLastY = y;
                        return true;
                    }
                    if (Math.abs(deltaY) > mTouchSlop) {
                        if (deltaY > 0) {
                            //down
                            if (callback.isIntercept(mPage, 1)) {
                                mStartDragY = y;
                                isBeingDragged = true;
                            }
                        } else {
                            //up
                            if (callback.isIntercept(mPage, -1)) {
                                mStartDragY = y;
                                isBeingDragged = true;
                            }
                        }
                    }
                    mLastY = y;
                    if (isBeingDragged) {
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    isBeingDragged = false;
                    int dragDistance = (int) (y - mStartDragY);
                    if (Math.abs(dragDistance) >= mScrollPageDistance) {
                        if (mPage == 1 && dragDistance < 0) {
                            //滚到Page2
                            translate2SpecPage(2);
                        } else if (mPage == 2 && dragDistance > 0) {
                            //滚到Page1
                            translate2SpecPage(1);
                        } else {
                            translate2SpecPage(mPage);
                        }
                    } else {
                        //回滚到当前Page
                        translate2SpecPage(mPage);
                    }
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void translate2SpecPage(int page) {
        mPage = page;
        View topChild = getChildAt(0);
        int total;
        if (page == 1) {
            int top = topChild.getTop();
            total = -top;
        } else {
            int top = topChild.getTop();
            int end = -topChild.getHeight();
            //top 从top - 负的
            total = end - top;
        }
        ValueAnimator va = ValueAnimator.ofFloat(0f, 1f);
        va.addUpdateListener(new UISeltter(total));
        va.setInterpolator(new DecelerateInterpolator());
        va.setDuration(300);
        va.start();
    }

    private class UISeltter implements ValueAnimator.AnimatorUpdateListener {
        private int total;
        private int pass;

        public UISeltter(int total) {
            this.total = total;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            Float fl = (Float) animation.getAnimatedValue();
            int now = (int) (fl * total);
            int deltaY = now - pass;
            pass = now;
            offsetChildTopAndBottom(deltaY);
        }
    }

    private void offsetChildTopAndBottom(int offset) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.offsetTopAndBottom(offset);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View top = getChildAt(0);
        View bottom = getChildAt(1);
        top.layout(l, t, r, b);
        bottom.layout(l, b, r, 2 * (b - t));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public interface DragCallback {
        /**
         * @param page 1 2
         * @param axis down 1 up -1
         * @return
         */
        boolean isIntercept(int page, int axis);
    }
}
