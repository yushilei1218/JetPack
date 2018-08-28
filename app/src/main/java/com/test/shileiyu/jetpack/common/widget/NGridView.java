package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author shilei.yu
 * @date 2018/8/28
 */

public class NGridView extends GridView {
    public NGridView(Context context) {
        super(context);
    }

    public NGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }
}
