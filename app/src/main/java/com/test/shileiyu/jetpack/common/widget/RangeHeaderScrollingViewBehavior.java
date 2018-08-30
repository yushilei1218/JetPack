package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.test.shileiyu.jetpack.common.util.Util;

import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/8/30
 */

public class RangeHeaderScrollingViewBehavior extends HeaderScrollingViewBehavior {
    public static final String TAG = "MyBehavior";

    public RangeHeaderScrollingViewBehavior() {
    }

    public RangeHeaderScrollingViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    View findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (view instanceof RangeHeaderLayout) {
                return (RangeHeaderLayout) view;
            }
        }
        return null;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {

        boolean b = dependency instanceof RangeHeaderLayout;
        Log.d(TAG, "layoutDependsOn " + b);
        return b;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int bottom = dependency.getBottom();
        Log.d(TAG, "onDependentViewChanged " + " dependency bottom=" + bottom);
        ViewCompat.offsetTopAndBottom(child, bottom - child.getTop());
        return true;
    }
}

