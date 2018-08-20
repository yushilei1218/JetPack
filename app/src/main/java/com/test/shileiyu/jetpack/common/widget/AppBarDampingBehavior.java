package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author shilei.yu
 * @date 2018/8/20
 */

public class AppBarDampingBehavior extends AppBarLayout.Behavior {
    public AppBarDampingBehavior() {
    }

    public AppBarDampingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    String TAG = "AppBarDampingBehavior";

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout,
                                  AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        boolean isNeedDamping = false;
        int temp = dy;
        if (dy < 0) {
            //向下滑动 并且Header露出的高度小于自身，则需要进行阻尼滑动
            int appBarHeight = child.getHeight();
            int offset = getTopAndBottomOffset();
            if (offset < 0 && appBarHeight > -offset) {
                isNeedDamping = true;
                dy = (int) (dy * 0.7f);
            }

            Log.d(TAG, "offset " + offset + " appBar " + appBarHeight);
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        Log.d(TAG, "consumed1 " + consumed[1]);
        if (isNeedDamping) {
            consumed[1] = (int) (dy*0.7f);
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }
}
