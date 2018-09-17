package com.test.shileiyu.jetpack.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author shilei.yu
 * @date 2018/9/17
 */

public class FixViewPager extends ViewPager {
    public FixViewPager(@NonNull Context context) {
        super(context);
    }

    public FixViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 0) {
            View child = getChildAt(0);

            setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), child.getMeasuredHeight());
        }
    }
}
