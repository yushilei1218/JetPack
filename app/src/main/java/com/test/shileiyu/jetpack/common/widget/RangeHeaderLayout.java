package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.test.shileiyu.jetpack.R;

/**
 * @author shilei.yu
 * @date 2018/8/30
 */
public class RangeHeaderLayout extends LinearLayout implements CoordinatorLayout.AttachedBehavior {
    private int mFixedHeight;

    public RangeHeaderLayout(Context context) {
        this(context, null);
    }

    public RangeHeaderLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RangeHeaderLayout);
            mFixedHeight = ta.getDimensionPixelSize(R.styleable.RangeHeaderLayout_fixedHeight, 0);
            ta.recycle();
        }
    }

    public boolean hasFixedRange() {
        return mFixedHeight > 0;
    }

    @NonNull
    @Override
    public CoordinatorLayout.Behavior getBehavior() {
        return new RangeHeaderBehavior();
    }
}
