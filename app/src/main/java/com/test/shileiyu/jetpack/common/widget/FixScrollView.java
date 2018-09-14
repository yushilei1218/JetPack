package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

import com.test.shileiyu.jetpack.BaseApp;

/**
 * @author shilei.yu
 * @date 2018/9/14
 */

public class FixScrollView extends ScrollView {
    private float mLY;
    private int mTouchSlop = ViewConfiguration.get(BaseApp.sApplication).getScaledTouchSlop();

    public FixScrollView(Context context) {
        super(context);
    }

    public FixScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float y = ev.getY();
        int scrollY = getScrollY();
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            float deltaY = y - mLY;
            if (Math.abs(deltaY) > mTouchSlop) {
                if (deltaY > 0) {
                    //down
                    if (scrollY == 0) {
                        return false;
                    }
                } else {
                    //up
                    if (scrollY == getChildAt(0).getHeight() - getHeight()) {
                        return false;
                    }
                }
            }
        }
        mLY = y;
        return super.onInterceptTouchEvent(ev);
    }
}
